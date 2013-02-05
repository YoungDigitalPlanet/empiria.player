package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.NORMAL;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionSurfacesManagerFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionModuleViewImpl implements MultiplePairModuleView<SimpleAssociableChoiceBean>, ConnectionMoveHandler, ConnectionMoveEndHandler,
		ConnectionMoveStartHandler {

	@Inject
	private ConnectionView view;
	@Inject
	private ConnectionModuleFactory connectionFactory;
	@Inject
	private PositionHelper positionHelper;
	@Inject
	private StyleSocket styleSocket;
	@Inject
	private StyleNameConstants styleNames;
	@Inject
	private XMLParser xmlParser;
	@Inject
	private EventsBus eventsBus;
	@Inject
	protected ConnectionEventHandler connectionEventHandler;
	@Inject
	private ConnectionItemsFactory connectionItemsFactory;
	@Inject
	private ConnectionSurfacesManagerFactory connectionSurfacesManagerFactory;
	@Inject
	private ConnectionViewResizeHandler resizeHandler;

	private ConnectionColumnsBuilder connectionColumnsBuilder;
	private ConnectionModuleViewStyles connectionModuleViewStyles;
	private final KeyValue<ConnectionItem, ConnectionItem> connectionItemPair = new KeyValue<ConnectionItem, ConnectionItem>();
	private MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final KeyValue<Double, Double> lastPoint = new KeyValue<Double, Double>(0d, 0d);
	private ModuleSocket moduleSocket;
	private final int approximation = UserAgentChecker.isStackAndroidBrowser() ? 15 : 5;
	private boolean locked;

	protected final Map<ConnectionItem, Point> startPositions = new HashMap<ConnectionItem, Point>();
	protected ConnectionSurfacesManager connectionSurfacesManager;
	protected ConnectionItems connectionItems;
	protected ConnectionsBetweenItems connectionsBetweenItems;
	protected ConnectionStyleChacker connectionStyleChacker;
	protected ConnectionSurface currentSurface = null;

	@Override
	public void setBean(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface) {
		this.modelInterface = modelInterface;
	}

	@Override
	public void reset() {
		connectionSurfacesManager.resetAll();
		connectionItems.resetAllItems();
	}

	protected void connect(ConnectionItem source, ConnectionItem target, MultiplePairModuleConnectType type, boolean userAction) {
		startDrawLine(source, type);
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
			KeyValue<String, String> keyValue = new KeyValue<String, String>(sourceIdentifier, targetIdentifier);
			connectionSurfacesManager.clearConnectionSurface(keyValue);
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
		checkStylesAndShowError();

	}

	private void checkStylesAndShowError() {
		connectionStyleChacker.areStylesCorrectThrowsExceptionWhenNot();
	}

	private void initColumns() {
		connectionColumnsBuilder.initLeftColumn();
		connectionColumnsBuilder.initRightColumn();

	}

	private void prepareObjects() {
		connectionSurfacesManager = connectionSurfacesManagerFactory.getConnectionSurfacesManager(view);
		connectionModuleViewStyles = new ConnectionModuleViewStyles(styleSocket, styleNames, xmlParser);
		connectionItems = connectionItemsFactory.getConnectionItems(moduleSocket.getInlineBodyGeneratorSocket());
		connectionStyleChacker = connectionFactory.getConnectionStyleChacker(styleSocket);
		connectionsBetweenItems = connectionFactory.getConnectionsBetweenItems(view, connectionItems);
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
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onConnectionStart(ConnectionMoveStartEvent event) {
		if (!locked) {
			ConnectionItem item = connectionsBetweenItems.findConnectionItem(event.getNativeEvent());
			if (item == null) {
				tryDisconnectConnection(event.getNativeEvent());
			} else {
				reserveTouchEvent();
				startDrawLine(item, NORMAL);
				item.setConnected(true, NORMAL);
				connectionItemPair.setKey(item);
			}
		}
	}

	private void reserveTouchEvent() {
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
	}

	@Override
	public void onConnectionMove(ConnectionMoveEvent event) {
		if (!locked && startPositions.containsKey(connectionItemPair.getKey())) {
			event.preventDefault();
			if (wasMoved(event)) {
				updatePointPosition(event);
				drawLine(connectionItemPair.getKey(), event.getX(), event.getY());
			}
		}
	}

	private void updatePointPosition(ConnectionMoveEvent event) {
		lastPoint.setKey(event.getX());
		lastPoint.setValue(event.getY());
	}

	private boolean wasMoved(ConnectionMoveEvent event) {
		return Math.abs(lastPoint.getKey() - event.getX()) > approximation || Math.abs(lastPoint.getValue() - event.getY()) > approximation;
	}

	protected void resetConnectionMadeByTouch() {
		connectionItemPair.setKey(null);
		connectionItemPair.setValue(null);
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

	@Override
	public void onConnectionMoveEnd(ConnectionMoveEndEvent event) {
		ConnectionItem connectionStartItem = connectionItemPair.getKey();
		if (!locked && !searchAndConnectItemsPair(event, connectionStartItem)) {
			if (!tryConnectByClick(connectionStartItem)) {
				connectionItemPair.setValue(connectionStartItem);
			}
		}
		clearSurface(connectionStartItem);
	}

	private boolean tryConnectByClick(ConnectionItem connectionStartItem) {
		boolean mayConnect = connectionItemPair.getValue() != null && !connectionItemPair.getValue().equals(connectionStartItem);
		if (mayConnect) {
			connect(connectionStartItem, connectionItemPair.getValue(), NORMAL, true);
			resetConnectionMadeByTouch();
		}
		return mayConnect;
	}

	private boolean isTouchEndOnItem(ConnectionMoveEndEvent event, ConnectionItem item) {
		return item.getOffsetLeft() <= event.getX()
				&& event.getX() <= item.getOffsetLeft() + item.getWidth()
				&& item.getOffsetTop() <= event.getY()
				&& event.getY() <= item.getOffsetTop() + item.getHeight();
	}

	protected void drawLine(ConnectionItem item, double positionX, double positionY) {
		if (startPositions.containsKey(item)) {
			Point startPoint = startPositions.get(item);
			currentSurface.drawLine(startPoint.getX(), startPoint.getY(), positionX, positionY);
		}
	}

	protected void resetIfNotConnected(String identifier) {
		if (!connectionSurfacesManager.hasConnections(identifier)) {
			connectionItems.getConnectionItem(identifier).reset();
		}
	}

	protected void clearSurface(ConnectionItem item) {
		if (startPositions.containsKey(item)) {
			startPositions.remove(item);
			currentSurface.clear();
		}
	}

	protected void startDrawLine(ConnectionItem item, MultiplePairModuleConnectType type) {
		Point startPoint = new Point(item.getRelativeX(), item.getRelativeY());
		startPositions.put(item, startPoint);
		currentSurface = connectionSurfacesManager.getOrCreateSurface(getIdentifier(item));
		currentSurface.applyStyles(connectionModuleViewStyles.getStyles(type));
		view.addElementToMainView(currentSurface.asWidget());
		currentSurface.drawLine(item.getRelativeX(), item.getRelativeY(), item.getRelativeX(), item.getRelativeY());
	}

	private String getIdentifier(ConnectionItem item) {
		return item.getBean().getIdentifier();
	}

	protected KeyValue<String, String> getConnectionPair(ConnectionItem sourceItem, ConnectionItem targetItem) {
		return new KeyValue<String, String>(getIdentifier(sourceItem), getIdentifier(targetItem));
	}

	protected void connectItems(ConnectionItem sourceItem, ConnectionItem targetItem, MultiplePairModuleConnectType type, boolean userAction) {
		startPositions.remove(sourceItem);
		sourceItem.reset();
		sourceItem.setConnected(true, type);
		targetItem.setConnected(true, type);
		KeyValue<String, String> connectionPair = getConnectionPair(sourceItem, targetItem);
		if (connectionSurfacesManager.containsSurface(connectionPair)) {
			connectionSurfacesManager.removeSurfaceFromParent(connectionPair);
			resetIfNotConnected(connectionPair.getKey());
			resetIfNotConnected(connectionPair.getValue());
		} else {
			connectionSurfacesManager.putSurface(connectionPair, currentSurface);
			connectionEventHandler.fireConnectEvent(PairConnectEventTypes.CONNECTED, getIdentifier(sourceItem), getIdentifier(targetItem), userAction);
		}
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

	protected void tryDisconnectConnection(NativeEvent event) {
		Point clickPoint = getClicktPoint(event);
		KeyValue<String, String> pointOnPath = connectionSurfacesManager.findPointOnPath(clickPoint);
		disconnectAndPreventDefaultBehaviorIfPointNotNull(event, pointOnPath);
	}

	private void disconnectAndPreventDefaultBehaviorIfPointNotNull(NativeEvent event, KeyValue<String, String> pointOnPath) {
		if (pointOnPath != null) {
			connectionSurfacesManager.removeSurfaceFromParent(pointOnPath);
			disconnect(pointOnPath.getKey(), pointOnPath.getValue(), true);
			event.preventDefault();
		}
	}

}
