package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.NORMAL;
import static eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column.LEFT;
import static eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column.RIGHT;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
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
import eu.ydp.empiria.player.client.util.events.AbstractEventHandlers;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionModuleViewImpl extends AbstractEventHandlers<PairConnectEventHandler, PairConnectEventTypes, PairConnectEvent> implements
		MultiplePairModuleView<SimpleAssociableChoiceBean>, ConnectionMoveHandler, ConnectionMoveEndHandler, ConnectionMoveStartHandler, ResizeHandler {

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

	protected final Map<String, ConnectionItem> items = new HashMap<String, ConnectionItem>();
	protected final Set<ConnectionItem> leftColumnItems = new HashSet<ConnectionItem>();
	protected final Set<ConnectionItem> rightColumnItems = new HashSet<ConnectionItem>();
	protected ConnectionSurface currentSurface = null;
	protected final Map<KeyValue<String, String>, ConnectionSurface> connectedSurfaces = new HashMap<KeyValue<String, String>, ConnectionSurface>();
	protected final Map<String, ConnectionSurface> surfaces = new HashMap<String, ConnectionSurface>();
	protected final Map<ConnectionItem, KeyValue<Integer, Integer>> startPositions = new HashMap<ConnectionItem, KeyValue<Integer, Integer>>();
	private ConnectionModuleViewStyles connectionModuleViewStyles;
	private final KeyValue<ConnectionItem, ConnectionItem> connectionItemPair = new KeyValue<ConnectionItem, ConnectionItem>();
	private MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final KeyValue<Double, Double> lastPoint = new KeyValue<Double, Double>(0d, 0d);
	private ModuleSocket moduleSocket;
	private final int approximation = UserAgentChecker.isStackAndroidBrowser() ? 15 : 5;

	private boolean locked;

	@Override
	public void setBean(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface) {
		this.modelInterface = modelInterface;

	}

	@Override
	public void reset() {
		for (ConnectionSurface surfce : connectedSurfaces.values()) {
			surfce.removeFromParent();
		}
		connectedSurfaces.clear();
		for (ConnectionItem item : items.values()) {
			item.reset();
		}

	}

	protected void connect(ConnectionItem source, ConnectionItem target, MultiplePairModuleConnectType type, boolean userAction) {
		startDrawLine(source, type);
		drawLine(source, target.getRelativeX(), target.getRelativeY());
		connectItems(source, target, type, userAction);
	}

	@Override
	public void connect(String sourceIdentifier, String targetIdentifier, MultiplePairModuleConnectType type) {
		if (items.containsKey(sourceIdentifier) && items.containsKey(targetIdentifier)) {
			ConnectionItem source = items.get(sourceIdentifier);
			ConnectionItem target = items.get(targetIdentifier);
			connect(source, target, type, false);
		} else {
			fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, targetIdentifier, false);
		}
	}

	@Override
	public void disconnect(String sourceIdentifier, String targetIdentifier) {
		disconnect(sourceIdentifier, targetIdentifier, false);
	}

	public void disconnect(String sourceIdentifier, String targetIdentifier, boolean userAction) {
		if (items.containsKey(sourceIdentifier) && items.containsKey(targetIdentifier)) {
			KeyValue<String, String> keyValue = new KeyValue<String, String>(sourceIdentifier, targetIdentifier);
			ConnectionSurface connectionSurface = connectedSurfaces.get(keyValue);
			if (connectionSurface != null) {
				connectionSurface.clear();
				connectionSurface.removeFromParent();
				connectedSurfaces.remove(keyValue);
			}

			resetIfNotConnected(items.get(sourceIdentifier));
			resetIfNotConnected(items.get(targetIdentifier));
			fireConnectEvent(PairConnectEventTypes.DISCONNECTED, sourceIdentifier, targetIdentifier, userAction);
		} else {
			fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, targetIdentifier, userAction);
		}
	}

	@Override
	public void addPairConnectEventHandler(PairConnectEventHandler handler) {
		for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
			addHandler(handler, PairConnectEvent.getType(type));
		}
	}

	@Override
	public void onResize(ResizeEvent event) {
		fireConnectEvent(PairConnectEventTypes.REPAINT_VIEW, "", "", true);
	}

	@Override
	public void bindView() {
		connectionModuleViewStyles = new ConnectionModuleViewStyles(styleSocket, styleNames, xmlParser);
		// zdarzenia z item
		view.addConnectionMoveHandler(this);
		view.addConnectionMoveEndHandler(this);
		view.addConnectionMoveStartHandler(this);
		Window.addResizeHandler(this);
		// zdarzenia od usera
		// elementy do wyswietlenia / polaczenia
		for (PairChoiceBean choice : modelInterface.getSourceChoicesSet()) {
			ConnectionItem item = connectionFactory.getConnectionItem(choice, moduleSocket.getInlineBodyGeneratorSocket(), LEFT);
			leftColumnItems.add(item);
			items.put(choice.getIdentifier(), item);
			view.addFirstColumnItem(item);
		}
		for (PairChoiceBean choice : modelInterface.getTargetChoicesSet()) {
			ConnectionItem item = connectionFactory.getConnectionItem(choice, moduleSocket.getInlineBodyGeneratorSocket(), RIGHT);
			rightColumnItems.add(item);
			items.put(choice.getIdentifier(), item);
			view.addSecondColumnItem(item);
		}

	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onConnectionStart(ConnectionMoveStartEvent event) {
		if (!locked) {
			ConnectionItem item = findConnectionItem(event.getNativeEvent());
			if (item == null) {
				findConnection(event.getNativeEvent());
			} else {
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
				startDrawLine(item, NORMAL);
				item.setConnected(true, NORMAL);
				connectionItemPair.setKey(item);
			}
		}
	}

	@Override
	public void onConnectionMove(ConnectionMoveEvent event) {
		if (!locked && startPositions.containsKey(connectionItemPair.getKey())) {
			event.preventDefault();
			if (Math.abs(lastPoint.getKey() - event.getX()) > approximation || Math.abs(lastPoint.getValue() - event.getY()) > approximation) {
				lastPoint.setKey(event.getX());
				lastPoint.setValue(event.getY());
				drawLine(connectionItemPair.getKey(), event.getX(), event.getY());
			}
		}
	}

	protected Set<ConnectionItem> getConnectionItems(ConnectionItem selectedItem) {
		return rightColumnItems.contains(selectedItem) ? leftColumnItems : rightColumnItems;
	}

	protected void resetConnectionByTouch() {
		connectionItemPair.setKey(null);
		connectionItemPair.setValue(null);
	}

	@Override
	public void onConnectionMoveEnd(ConnectionMoveEndEvent event) {
		if (!locked) {
			for (ConnectionItem item : getConnectionItems(connectionItemPair.getKey())) {
				if (item.getOffsetLeft() <= event.getX() && event.getX() <= item.getOffsetLeft() + item.getWidth() && item.getOffsetTop() <= event.getY()
						&& event.getY() <= item.getOffsetTop() + item.getHeight()) {
					drawLine(connectionItemPair.getKey(), item.getRelativeX(), item.getRelativeY());
					connectItems(connectionItemPair.getKey(), item, NORMAL, true);
					resetConnectionByTouch();
					return;
				}
			}
			// connection by click
			if (connectionItemPair.getValue() == null || connectionItemPair.getValue().equals(connectionItemPair.getKey())) {
				connectionItemPair.setValue(connectionItemPair.getKey());
			} else {
				connect(connectionItemPair.getKey(), connectionItemPair.getValue(), NORMAL, true);
				resetConnectionByTouch();
			}
		}
		clearSurface(connectionItemPair.getKey());
	}

	protected void drawLine(ConnectionItem item, double positionX, double positionY) {
		if (startPositions.containsKey(item)) {
			KeyValue<Integer, Integer> startPoint = startPositions.get(item);
			currentSurface.drawLine(startPoint.getKey(), startPoint.getValue(), positionX, positionY);
		}

	}

	protected boolean hasConnections(ConnectionItem item) {
		boolean hasConnection = false;
		String identifier = item.getBean().getIdentifier();
		for (KeyValue<String, String> connection : connectedSurfaces.keySet()) {
			if (identifier.equals(connection.getKey()) || identifier.equals(connection.getValue())) {
				hasConnection = true;
				break;
			}
		}
		return hasConnection;
	}

	protected void resetIfNotConnected(ConnectionItem item) {
		if (!hasConnections(item)) {
			item.reset();
		}
	}

	protected void clearSurface(ConnectionItem item) {
		if (startPositions.containsKey(item)) {
			startPositions.remove(item);
			currentSurface.clear();
		}
	}

	protected ConnectionSurface getSurface(ConnectionItem item) {
		ConnectionSurface surface;
		if ((surface = surfaces.get(item.getBean().getIdentifier())) == null) {// NOPMD
			surface = connectionFactory.getConnectionSurface(view.getOffsetWidth(), view.getOffsetHeight());
			surfaces.put(item.getBean().getIdentifier(), surface);
		}
		return surface;
	}

	protected void startDrawLine(ConnectionItem item, MultiplePairModuleConnectType type) {
		KeyValue<Integer, Integer> startPoint = new KeyValue<Integer, Integer>(item.getRelativeX(), item.getRelativeY());
		startPositions.put(item, startPoint);
		currentSurface = getSurface(item);
		currentSurface.applyStyles(connectionModuleViewStyles.getStyles(type));
		view.addElementToMainView(currentSurface.asWidget());
		currentSurface.drawLine(item.getRelativeX(), item.getRelativeY(), item.getRelativeX(), item.getRelativeY());
	}

	protected KeyValue<String, String> getConnectionPair(ConnectionItem sourceItem, ConnectionItem targetItem) {
		return new KeyValue<String, String>(sourceItem.getBean().getIdentifier(), targetItem.getBean().getIdentifier());
	}

	protected void connectItems(ConnectionItem sourceItem, ConnectionItem targetItem, MultiplePairModuleConnectType type, boolean userAction) {
		startPositions.remove(sourceItem);
		sourceItem.reset();
		sourceItem.setConnected(true, type);
		targetItem.setConnected(true, type);
		ConnectionSurface duplicate = connectedSurfaces.put(getConnectionPair(sourceItem, targetItem), currentSurface);
		surfaces.remove(sourceItem.getBean().getIdentifier());
		if (duplicate == null) {
			fireConnectEvent(PairConnectEventTypes.CONNECTED, sourceItem.getBean().getIdentifier(), targetItem.getBean().getIdentifier(), userAction);
		} else {
			duplicate.removeFromParent();
		}
	}

	protected void fireConnectEvent(PairConnectEventTypes type, String source, String target, boolean userAction) {
		PairConnectEvent event = new PairConnectEvent(type, source, target, userAction);
		fireEvent(event);

	}

	@Override
	protected void dispatchEvent(PairConnectEventHandler handler, PairConnectEvent event) {
		handler.onConnectionEvent(event);
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;

	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		this.moduleSocket = socket;
	}

	protected void findConnection(NativeEvent event) {
		int xPos = positionHelper.getPositionX(event, view.getElement());
		int yPos = positionHelper.getPositionY(event, view.getElement());
		for (Map.Entry<KeyValue<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(xPos, yPos, 10)) {
				disconnect(entry.getKey().getKey(), entry.getKey().getValue(), true);
				event.preventDefault();
				break;
			}
		}
	}

	protected ConnectionItem findConnectionItem(NativeEvent event) {
		int xPos = positionHelper.getPositionX(event, view.getElement());
		int yPos = positionHelper.getPositionY(event, view.getElement());
		ConnectionItem item = null;
		for (Map.Entry<String, ConnectionItem> itemEntry : items.entrySet()) {
			if (itemEntry.getValue().isOnPosition(xPos, yPos)) {
				item = itemEntry.getValue();
				event.preventDefault(); // disable text selection
				break;
			}
		}
		return item;
	}

}
