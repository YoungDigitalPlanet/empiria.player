package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
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
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionModuleViewImpl extends AbstractEventHandlers<PairConnectEventHandler, PairConnectEventTypes, PairConnectEvent> implements MultiplePairModuleView<SimpleAssociableChoiceBean>, ConnectionMoveHandler, ConnectionMoveEndHandler, ConnectionMoveStartHandler, TouchHandler {

	@Inject
	private ConnectionView view;
	@Inject
	private ConnectionModuleFactory connectionFactory;
	@Inject
	private PositionHelper positionHelper;
	@Inject
	private StyleSocket styleSocket;
	@Inject
	private TouchRecognitionFactory touchRecognitionFactory;
	@Inject
	private StyleNameConstants styleNames;
	@Inject
	XMLParser xmlParser;

	private final Map<String, ConnectionItem> items = new HashMap<String, ConnectionItem>();
	protected final Set<ConnectionItem> leftColumnItems = new HashSet<ConnectionItem>();
	protected final Set<ConnectionItem> rightColumnItems = new HashSet<ConnectionItem>();
	protected ConnectionSurface currentSurfce = null;
	protected final Map<KeyValue<String, String>, ConnectionSurface> connectedSurfaces = new HashMap<KeyValue<String, String>, ConnectionSurface>();
	protected final Map<ConnectionItem, KeyValue<Integer, Integer>> startPositions = new HashMap<ConnectionItem, KeyValue<Integer, Integer>>();
	private ConnectionModuleViewStyles connectionModuleViewStyles;
	private ConnectionItem selectedItem;
	private MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final KeyValue<Double, Double> lastPoint = new KeyValue<Double, Double>(0d, 0d);
	private ModuleSocket moduleSocket;

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
	}

	@Override
	public void connect(String sourceIdentifier, String targetIdentifier, MultiplePairModuleConnectType type) {
		if (items.containsKey(sourceIdentifier) && items.containsKey(targetIdentifier)) {
			ConnectionItem source = items.get(sourceIdentifier);
			ConnectionItem target = items.get(targetIdentifier);
			startDrawLine(source, type);
			drawLine(target, target.getRelativeX(), target.getRelativeY());
			connectItems(source, target);
		} else {
			fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, targetIdentifier);
		}
	}

	@Override
	public void disconnect(String sourceIdentifier, String targetIdentifier) {
		if (items.containsKey(sourceIdentifier) && items.containsKey(targetIdentifier)) {
			KeyValue<String, String> keyValue = new KeyValue<String, String>(sourceIdentifier, targetIdentifier);
			ConnectionSurface connectionSurface = connectedSurfaces.get(keyValue);
			if (connectionSurface != null) {
				connectionSurface.clear();
				connectionSurface.removeFromParent();
				connectedSurfaces.remove(keyValue);
			}
			items.get(sourceIdentifier).reset();
			items.get(targetIdentifier).reset();
			fireConnectEvent(PairConnectEventTypes.DISCONNECTED, sourceIdentifier, targetIdentifier);
		} else {
			fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, targetIdentifier);
		}

	}

	@Override
	public void addPairConnectEventHandler(PairConnectEventHandler handler) {
		for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
			addHandler(handler, PairConnectEvent.getType(type));
		}
	}

	@Override
	public void bindView() {
		connectionModuleViewStyles = new ConnectionModuleViewStyles(styleSocket, styleNames, xmlParser);
		// zdarzenia z item
		view.addConnectionMoveHandler(this);
		view.addConnectionMoveEndHandler(this);
		view.addConnectionMoveStartHandler(this);
		// zdarzenia od usera
		HasTouchHandlers touchRecognition = touchRecognitionFactory.getTouchRecognition(view.asWidget());
		touchRecognition.addTouchHandler(this, TouchEvent.getType(TouchTypes.TOUCH_START));
		touchRecognition.addTouchHandler(this, TouchEvent.getType(TouchTypes.TOUCH_END));
		// elementy do wyswietlenia / polaczenia
		for (PairChoiceBean choice : modelInterface.getSourceChoicesSet()) {
			ConnectionItem item = connectionFactory.getConnectionItem(choice, moduleSocket.getInlineBodyGeneratorSocket());
			leftColumnItems.add(item);
			items.put(choice.getIdentifier(), item);
			view.addFirstColumnItem(item);
		}
		for (PairChoiceBean choice : modelInterface.getTargetChoicesSet()) {
			ConnectionItem item = connectionFactory.getConnectionItem(choice, moduleSocket.getInlineBodyGeneratorSocket());
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
			startDrawLine(event.getItem(), MultiplePairModuleConnectType.NORMAL);
			selectedItem = event.getItem();
		}

	}

	@Override
	public void onConnectionMove(ConnectionMoveEvent event) {
		if (!locked) {
			int approximation = 5;
			if (UserAgentChecker.isStackAndroidBrowser()) {
				approximation = 15;
			}

			if (Math.abs(lastPoint.getKey() - event.getX()) > approximation || Math.abs(lastPoint.getValue() - event.getY()) > approximation) {
				lastPoint.setKey(event.getX());
				lastPoint.setValue(event.getY());
				drawLine(selectedItem, event.getX(), event.getY());
			}
		}
	}

	protected Set<ConnectionItem> getConnectionItems(ConnectionItem selectedItem) {
		return rightColumnItems.contains(selectedItem) ? leftColumnItems : rightColumnItems;
	}

	@Override
	public void onConnectionMoveEnd(ConnectionMoveEndEvent event) {
		if (!locked) {
			for (ConnectionItem item : getConnectionItems(selectedItem)) {
				if (item.getOffsetLeft() <= event.getX() && event.getX() <= item.getOffsetLeft() + item.getWidth() && item.getOffsetTop() <= event.getY() && event.getY() <= item.getOffsetTop() + item.getHeight()) {
					drawLine(selectedItem, event.getX(), event.getY());
					connectItems(selectedItem, item);
					return;
				}
			}
		}
		clearSurface(selectedItem);
	}

	protected void drawLine(ConnectionItem item, double positionX, double positionY) {
		if (startPositions.containsKey(item)) {
			KeyValue<Integer, Integer> startPoint = startPositions.get(item);
			currentSurfce.drawLine(startPoint.getKey(), startPoint.getValue(), positionX, positionY);
		}

	}

	protected void clearSurface(ConnectionItem item) {
		if (startPositions.containsKey(item)) {
			item.reset();
			startPositions.remove(item);
			currentSurfce.clear();
		}
	}

	protected void startDrawLine(ConnectionItem item, MultiplePairModuleConnectType type) {
		KeyValue<Integer, Integer> startPoint = new KeyValue<Integer, Integer>(item.getRelativeX(), item.getRelativeY());
		startPositions.put(item, startPoint);
		currentSurfce = connectionFactory.getConnectionSurfce(view.getOffsetWidth(), view.getOffsetHeight());
		currentSurfce.applyStyles(connectionModuleViewStyles.getStyles(type));
		view.addElementToMainView(currentSurfce.asWidget());
		currentSurfce.drawLine(item.getRelativeX(), item.getRelativeY(), item.getRelativeX(), item.getRelativeY());
	}

	protected void connectItems(ConnectionItem sourceItem, ConnectionItem targetItem) {
		startPositions.remove(sourceItem);
		sourceItem.reset();
		sourceItem.setConnected(true);
		targetItem.setConnected(true);
		ConnectionSurface duplicate = connectedSurfaces.put(new KeyValue<String, String>(sourceItem.getBean().getIdentifier(), targetItem.getBean().getIdentifier()), currentSurfce);
		if (duplicate == null) {
			fireConnectEvent(PairConnectEventTypes.CONNECTED, sourceItem.getBean().getIdentifier(), targetItem.getBean().getIdentifier());
		} else {
			duplicate.removeFromParent();
		}
	}

	protected void fireConnectEvent(PairConnectEventTypes type, String source, String target) {
		PairConnectEvent event = new PairConnectEvent(type, source, target);
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

	private void findConnection(NativeEvent event) {
		int xPos = positionHelper.getPositionX(event, view.getElement());
		int yPos = positionHelper.getPositionY(event, view.getElement());
		for (Map.Entry<KeyValue<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(xPos, yPos, 10)) {
				disconnect(entry.getKey().getKey(), entry.getKey().getValue());
				try {
					event.preventDefault();
				} catch (Exception exc) {
				}
				break;
			}
		}
	}

	@Override
	public void onTouchEvent(TouchEvent event) {
		if (event.getType() == TouchTypes.TOUCH_START) {
			findConnection(event.getNativeEvent());
		} else if (event.getType() == TouchTypes.TOUCH_END) {
			clearSurface(selectedItem);
		}
	}
}
