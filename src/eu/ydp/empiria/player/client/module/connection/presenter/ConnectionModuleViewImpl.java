package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceStyleProvider;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.translation.SurfacePositionFinder;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.HasConnectionMoveHandlers;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionModuleViewImpl implements MultiplePairModuleView<SimpleAssociableChoiceBean>, HasConnectionMoveHandlers {

	@Inject
	private UserAgentCheckerWrapper userAgent;
	@Inject
	private ConnectionView view;
	@Inject
	private ConnectionModuleFactory connectionFactory;
	@Inject
	private PositionHelper positionHelper;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private ConnectionEventHandler connectionEventHandler;
	@Inject
	private ConnectionItemsFactory connectionItemsFactory;
	@Inject
	private ConnectionViewResizeHandler resizeHandler;
	@Inject
	private SurfacePositionFinder surfacePositionFinder;

	@Inject
	private ConnectionStyleChecker connectionStyleChecker;

	@Inject
	private ConnectionSurfaceStyleProvider surfaceStyleProvider;

	@Inject
	private ConnectionModuleViewStyles connectionModuleViewStyles;

	@Inject
	private ConnectionsBetweenItemsFinder connectionsFinder;

	@Inject
	@PageScoped
	private ConnectionSurfacesManager connectionSurfacesManager;

	private ConnectionColumnsBuilder connectionColumnsBuilder;
	private final ConnectionPairEntry<ConnectionItem, ConnectionItem> connectionItemPair = new ConnectionPairEntry<ConnectionItem, ConnectionItem>();
	private MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final ConnectionPairEntry<Double, Double> lastPoint = new ConnectionPairEntry<Double, Double>(0d, 0d);
	private ModuleSocket moduleSocket;
	private boolean locked;

	private final Map<ConnectionItem, Point> startPositions = new HashMap<ConnectionItem, Point>();
	protected ConnectionItems connectionItems;

	protected ConnectionSurface currentSurface = null;

	private final Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces = Maps.newHashMap();
	private final Map<String, ConnectionSurface> surfaces = Maps.newHashMap();

	@PostConstruct
	public void postConstruct() {
		view.setDrawFollowTouch(!userAgent.isStackAndroidBrowser());
	}

	@Override
	public void setBean(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface) {
		this.modelInterface = modelInterface;
	}

	@Override
	public void reset() {
		connectionSurfacesManager.resetAll(connectedSurfaces);
		connectionItems.resetAllItems();
	}

	protected void connect(ConnectionItem source, ConnectionItem target, MultiplePairModuleConnectType type, boolean userAction) {
		currentSurface = getSurfaceForLineDrawing(source, type);
		startDrawLine(source, currentSurface);
		drawLine(source, target.getRelativeX(), target.getRelativeY());
		connectItems(source, target, type, userAction);
	}

	@Override
	public void connect(String sourceIdentifier, String targetIdentifier, MultiplePairModuleConnectType type) {
		if (connectionItems.isIdentifiersCorrect(sourceIdentifier, targetIdentifier)) {
			findConnectionItemsAndConnect(sourceIdentifier, targetIdentifier, type);
		} else {
			fireEventWrongConnection(sourceIdentifier, targetIdentifier, false);
		}
	}

	private void findConnectionItemsAndConnect(String sourceIdentifier, String targetIdentifier, MultiplePairModuleConnectType type) {
		ConnectionItem source = connectionItems.getConnectionItem(sourceIdentifier);
		ConnectionItem target = connectionItems.getConnectionItem(targetIdentifier);
		connect(source, target, type, false);
	}

	@Override
	public void disconnect(String sourceIdentifier, String targetIdentifier) {
		disconnect(sourceIdentifier, targetIdentifier, false);
	}

	public void disconnect(String sourceIdentifier, String targetIdentifier, boolean userAction) {
		if (connectionItems.isIdentifiersCorrect(sourceIdentifier, targetIdentifier)) {
			ConnectionPairEntry<String, String> keyValue = new ConnectionPairEntry<String, String>(sourceIdentifier, targetIdentifier);
			connectionSurfacesManager.clearConnectionSurface(connectedSurfaces, keyValue);
			resetIfNotConnected(sourceIdentifier);
			resetIfNotConnected(targetIdentifier);
			connectionEventHandler.fireConnectEvent(PairConnectEventTypes.DISCONNECTED, sourceIdentifier, targetIdentifier, userAction);
		} else {
			fireEventWrongConnection(sourceIdentifier, targetIdentifier, userAction);
		}
	}

	private void fireEventWrongConnection(String sourceIdentifier, String targetIdentifier, boolean userAction) {
		connectionEventHandler.fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, targetIdentifier, userAction);
	}

	@Override
	public void addPairConnectEventHandler(PairConnectEventHandler handler) {
		connectionEventHandler.addPairConnectEventHandler(handler);
	}

	@Override
	public void bindView() {
		prepareObjects();
		addConnectionHandlers();
		addResizeHandler();
		initColumns();
		addCheckStyleHandler();
	}

	private void addCheckStyleHandler() {
		view.asWidget().addAttachHandler(new ConnectionStyleCheckerHandler(view, connectionStyleChecker));
	}

	private void initColumns() {
		connectionColumnsBuilder.initLeftColumn();
		connectionColumnsBuilder.initRightColumn();

	}

	private void prepareObjects() {
		connectionItems = connectionItemsFactory.getConnectionItems(moduleSocket.getInlineBodyGeneratorSocket());
		connectionColumnsBuilder = connectionFactory.getConnectionColumnsBuilder(modelInterface, connectionItems, view);
	}

	private void addResizeHandler() {
		resizeHandler.setConnectionModuleViewImpl(connectionEventHandler);
		Window.addResizeHandler(resizeHandler);
	}

	private void addConnectionHandlers() {
		view.addConnectionMoveHandler(this);
		view.addConnectionMoveEndHandler(this);
		view.addConnectionMoveStartHandler(this);
		view.addConnectionMoveCancelHandler(this);
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onConnectionStart(ConnectionMoveStartEvent event) {
		if (!locked) {
			handleConnectionStart(event, this, connectionItems);
		}
	}

	private void handleConnectionStart(ConnectionMoveStartEvent event, IsWidget widget, ConnectionItems connectionItems) {
		ConnectionItem item = connectionsFinder.findConnectionItemForEventOnWidget(event.getNativeEvent(), this, connectionItems);

		if (item == null) {
			NativeEvent event1 = event.getNativeEvent();
			Point clickPoint = getClicktPoint(event1);
			ConnectionPairEntry<String, String> pointOnPath = connectionSurfacesManager.findPointOnPath(connectedSurfaces, clickPoint);

			if (pointOnPath != null) {
				connectionSurfacesManager.removeSurfaceFromParent(connectedSurfaces, pointOnPath);
				disconnect(pointOnPath.getSource(), pointOnPath.getTarget(), true);
				event1.preventDefault();
			}
		} else {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
			event.getNativeEvent().preventDefault();
			currentSurface = getSurfaceForLineDrawing(item, NORMAL);
			startDrawLine(item, currentSurface);
			item.setConnected(true, NORMAL);

			// resetOtherLastStartElement
			ConnectionItem source = connectionItemPair.getSource();
			if (source != null && !item.equals(source)) {
				resetIfNotConnected(getIdentifier(source));
			}

			connectionItemPair.setSource(item);
		}
	}

	@Override
	public void onConnectionMove(ConnectionMoveEvent event) {
		if (!locked && startPositions.containsKey(connectionItemPair.getSource())) {
			event.preventDefault();
			if (wasMoved(event)) {
				updatePointPosition(event);
				drawLine(connectionItemPair.getSource(), (int) event.getX(), (int) event.getY());
			}
		}
	}

	@Override
	public void onConnectionMoveEnd(ConnectionMoveEndEvent event) {
		ConnectionItem connectionStartItem = connectionItemPair.getSource();

		if (connectionStartItem != null && !locked && !searchAndConnectItemsPair(event, connectionStartItem)) {
			if (!tryConnectByClick(connectionStartItem)) {
				connectionItemPair.setTarget(connectionStartItem);
			}
		}
		clearSurface(connectionStartItem);
	}

	private void updatePointPosition(ConnectionMoveEvent event) {
		lastPoint.setSource(event.getX());
		lastPoint.setTarget(event.getY());
	}

	private boolean wasMoved(ConnectionMoveEvent event) {
		return Math.abs(lastPoint.getSource() - event.getX()) > approximation() || Math.abs(lastPoint.getTarget() - event.getY()) > approximation();
	}

	private int approximation() {
		return userAgent.isStackAndroidBrowser() ? 15 : 5;
	}

	protected void resetConnectionMadeByTouch() {
		connectionItemPair.setSource(null);
		connectionItemPair.setTarget(null);
	}

	private boolean searchAndConnectItemsPair(ConnectionMoveEndEvent event, ConnectionItem connectionStartItem) {
		boolean found = false;
		for (ConnectionItem item : connectionItems.getConnectionItems(connectionStartItem)) {
			if (isTouchEndOnItem(event, item)) {
				drawLine(connectionStartItem, item.getRelativeX(), item.getRelativeY());
				connectItems(connectionStartItem, item, NORMAL, true);
				resetConnectionMadeByTouch();
				found = true;
			}
		}
		return found;
	}

	private boolean tryConnectByClick(ConnectionItem connectionStartItem) {
		boolean mayConnect = connectionItemPair.getTarget() != null && !connectionItemPair.getTarget().equals(connectionStartItem);
		if (mayConnect) {
			connect(connectionStartItem, connectionItemPair.getTarget(), NORMAL, true);
			resetConnectionMadeByTouch();
		}
		return mayConnect;
	}

	private boolean isTouchEndOnItem(ConnectionMoveEndEvent event, ConnectionItem item) {
		return item.getOffsetLeft() <= event.getX() && event.getX() <= item.getOffsetLeft() + item.getWidth() && item.getOffsetTop() <= event.getY()
				&& event.getY() <= item.getOffsetTop() + item.getHeight();
	}

	protected void resetIfNotConnected(String identifier) {
		if (!connectionSurfacesManager.hasConnections(connectedSurfaces, identifier)) {
			connectionItems.getConnectionItem(identifier).reset();
		}
	}

	protected void clearSurface(ConnectionItem item) {
		if (startPositions.containsKey(item)) {
			startPositions.remove(item);
			currentSurface.clear();
			currentSurface.removeFromParent();
			currentSurface = null;
		}
	}

	private void startDrawLine(ConnectionItem item, ConnectionSurface connectionSurface) {
		Point startPoint = new Point(item.getRelativeX(), item.getRelativeY());
		startPositions.put(item, startPoint);

		connectionSurface.drawLine(startPoint, startPoint);
	}

	private ConnectionSurface getSurfaceForLineDrawing(ConnectionItem item, MultiplePairModuleConnectType type) {
		ConnectionSurface cs = connectionSurfacesManager.getOrCreateSurface(surfaces, getIdentifier(item), view);
		cs.applyStyles(connectionModuleViewStyles.getStyles(type));

		int offsetLeft = surfacePositionFinder.findOffsetLeft(connectionItems);
		int offsetTop = surfacePositionFinder.findTopOffset(connectionItems);

		cs.setOffsetLeft(offsetLeft);
		cs.setOffsetTop(offsetTop);

		view.addElementToMainView(cs);

		return cs;
	}

	private void drawLine(ConnectionItem item, int positionX, int positionY) {
		if (startPositions.containsKey(item)) {
			Point startPoint = startPositions.get(item);
			Point endPoint = new Point(positionX, positionY);
			currentSurface.drawLine(startPoint, endPoint);
		}
	}

	private String getIdentifier(ConnectionItem item) {
		return item.getBean().getIdentifier();
	}

	private ConnectionPairEntry<String, String> getConnectionPair(ConnectionItem sourceItem, ConnectionItem targetItem) {
		return new ConnectionPairEntry<String, String>(getIdentifier(sourceItem), getIdentifier(targetItem));
	}

	private void connectItems(ConnectionItem sourceItem, ConnectionItem targetItem, MultiplePairModuleConnectType type, boolean userAction) {
		ConnectionPairEntry<String, String> connectionPair = getConnectionPair(sourceItem, targetItem);
		if (connectionSurfacesManager.containsSurface(connectedSurfaces, connectionPair)) {
			resetIfNotConnected(getIdentifier(sourceItem));
		} else {
			startPositions.remove(sourceItem);
			sourceItem.reset();
			sourceItem.setConnected(true, type);
			targetItem.setConnected(true, type);
			connectionSurfacesManager.putSurface(surfaces, connectedSurfaces, connectionPair, currentSurface);
			connectionEventHandler.fireConnectEvent(PairConnectEventTypes.CONNECTED, getIdentifier(sourceItem), getIdentifier(targetItem), userAction);

			prepareAndAddStyleToSurface(sourceItem, targetItem, type);
		}
	}

	private void prepareAndAddStyleToSurface(ConnectionItem enditem, ConnectionItem startItem, MultiplePairModuleConnectType type) {
		boolean startIsLeft = modelInterface.isLeftItem(startItem.getBean());
		int leftIndex;
		int rightIndex;

		if (startIsLeft) {
			leftIndex = modelInterface.getLeftItemIndex(startItem.getBean());
			rightIndex = modelInterface.getRightItemIndex(enditem.getBean());
		} else {
			leftIndex = modelInterface.getLeftItemIndex(enditem.getBean());
			rightIndex = modelInterface.getRightItemIndex(startItem.getBean());
		}

		List<String> stylesToAdd = surfaceStyleProvider.getStylesForSurface(type, leftIndex, rightIndex);
		addStylesToSurface(stylesToAdd);
	}

	protected void addStylesToSurface(List<String> styles) {
		for (String style : styles) {
			getCurrentSurface().asWidget().addStyleName(style);
		}
	}

	public ConnectionSurface getCurrentSurface() {
		return currentSurface;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		this.moduleSocket = socket;
	}

	private Point getClicktPoint(NativeEvent event) {
		return positionHelper.getPoint(event, view.getElement());
	}

	@Override
	public boolean isAttached() {
		return asWidget().isAttached();
	}

	@Override
	public void onConnectionMoveCancel() {

		ConnectionItem connectionStartItem = connectionItemPair.getSource();
		if (connectionStartItem != null) {
			resetIfNotConnected(getIdentifier(connectionStartItem));
			resetConnectionMadeByTouch();
			clearSurface(connectionStartItem);
		}
	}
}
