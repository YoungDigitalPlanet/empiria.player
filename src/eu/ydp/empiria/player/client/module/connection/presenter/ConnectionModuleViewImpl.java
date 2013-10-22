package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
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
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;

public class ConnectionModuleViewImpl implements MultiplePairModuleView<SimpleAssociableChoiceBean> {

	@Inject
	private UserAgentCheckerWrapper userAgent;

	@Inject
	private ConnectionView view;
	@Inject
	private ConnectionModuleFactory connectionFactory;
	@Inject
	private ConnectionEventHandler connectionEventHandler;
	@Inject
	private ConnectionItemsFactory connectionItemsFactory;
	@Inject
	private ConnectionViewResizeHandler resizeHandler;

	@Inject
	private ConnectionStyleChecker connectionStyleChecker;

	@Inject
	private ConnectionSurfaceStyleProvider surfaceStyleProvider;

	@Inject
	private ConnectionSurfacesManager connectionSurfacesManager;

	@Inject
	private ConnectionModuleViewStyles connectionModuleViewStyles;

	@Inject
	private SurfacePositionFinder surfacePositionFinder;

	@Inject
	private ConnectionModuleViewImplHandlers handlers;
	
	private ConnectionColumnsBuilder connectionColumnsBuilder;
	private final ConnectionPairEntry<ConnectionItem, ConnectionItem> connectionItemPair = new ConnectionPairEntry<ConnectionItem, ConnectionItem>();
	private MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final ConnectionPairEntry<Double, Double> lastPoint = new ConnectionPairEntry<Double, Double>(0d, 0d);
	private ModuleSocket moduleSocket;
	boolean locked;

	private final Map<ConnectionItem, Point> startPositions = new HashMap<ConnectionItem, Point>();

	private ConnectionItems connectionItems;

	private ConnectionSurface currentSurface;

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

	public void connect(ConnectionItem source, ConnectionItem target, MultiplePairModuleConnectType type, boolean userAction) {
		currentSurface = getSurfaceForLineDrawing(source, type);
		startDrawLine(source);
		drawLineFromSource(target.getRelativeX(), target.getRelativeY());
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

	void disconnect(String sourceIdentifier, String targetIdentifier, boolean userAction) {
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
		handlers.setView(this);
		
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
		view.addConnectionMoveHandler(handlers);
		view.addConnectionMoveEndHandler(handlers);
		view.addConnectionMoveStartHandler(handlers);
		view.addConnectionMoveCancelHandler(handlers);
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	public void resetTouchConnections() {
		connectionItemPair.setSource(null);
		connectionItemPair.setTarget(null);
	}

	public void clearSurface(ConnectionItem item) {
		if (startPositions.containsKey(item)) {
			startPositions.remove(item);
			currentSurface.clear();
			currentSurface.removeFromParent();
			currentSurface = null;
		}
	}

	String getIdentifier(ConnectionItem item) {
		return item.getBean().getIdentifier();
	}

	private ConnectionPairEntry<String, String> getConnectionPair(ConnectionItem sourceItem, ConnectionItem targetItem) {
		return new ConnectionPairEntry<String, String>(getIdentifier(sourceItem), getIdentifier(targetItem));
	}

	public void connectItems(ConnectionItem sourceItem, ConnectionItem targetItem, MultiplePairModuleConnectType type, boolean userAction) {
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

	private void addStylesToSurface(List<String> styles) {
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

	@Override
	public boolean isAttached() {
		return asWidget().isAttached();
	}

	public boolean isLocked() {
		return locked;
	}

	public ConnectionItems getConnectionItems() {
		return connectionItems;
	}

	public Element getConnectionViewElement() {
		return view.getElement();
	}

	public void setCurrentSurface(ConnectionSurface currentSurface) {
		this.currentSurface = currentSurface;
	}

	public Map<ConnectionPairEntry<String, String>, ConnectionSurface> getConnectedSurfaces() {
		return connectedSurfaces;
	}

	public Map<String, ConnectionSurface> getSurfaces() {
		return surfaces;
	}

	public Map<ConnectionItem, Point> getStartPositions() {
		return startPositions;
	}

	public ConnectionPairEntry<ConnectionItem, ConnectionItem> getConnectionItemPair() {
		return connectionItemPair;
	}

	public ConnectionPairEntry<Double, Double> getLastPoint() {
		return lastPoint;
	}

	public void resetIfNotConnected(String identifier) {
		if (connectionSurfacesManager.hasConnections(connectedSurfaces, identifier)) {
			connectionItems.getConnectionItem(identifier).reset();
		}
	}

	public ConnectionSurface getSurfaceForLineDrawing(ConnectionItem item, MultiplePairModuleConnectType type) {
		ConnectionSurface cs = connectionSurfacesManager.getOrCreateSurface(surfaces, item.getBean().getIdentifier(), view);
		this.currentSurface = cs;
		cs.applyStyles(connectionModuleViewStyles.getStyles(type));

		int offsetLeft = surfacePositionFinder.findOffsetLeft(connectionItems);
		int offsetTop = surfacePositionFinder.findTopOffset(connectionItems);

		cs.setOffsetLeft(offsetLeft);
		cs.setOffsetTop(offsetTop);

		view.addElementToMainView(cs);

		return cs;
	}

	public void startDrawLine(ConnectionItem item) {
		Point startPoint = new Point(item.getRelativeX(), item.getRelativeY());
		startPositions.put(item, startPoint);

		currentSurface.drawLine(startPoint, startPoint);
	}

	public void drawLineFromSource(int positionX, int positionY) {
		ConnectionItem item = connectionItemPair.getSource();
		if (startPositions.containsKey(item)) {
			Point startPoint = startPositions.get(item);
			Point endPoint = new Point(positionX, positionY);
			currentSurface.drawLine(startPoint, endPoint);
		}
	}



}
