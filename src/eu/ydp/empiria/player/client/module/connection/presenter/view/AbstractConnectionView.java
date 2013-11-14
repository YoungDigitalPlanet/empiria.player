package eu.ydp.empiria.player.client.module.connection.presenter.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveCancelHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchHandler;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.TouchEventChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public abstract class AbstractConnectionView extends Composite implements ConnectionView, TouchHandler {
	private final Set<ConnectionMoveHandler> handlers = new HashSet<ConnectionMoveHandler>();
	private final Set<ConnectionMoveEndHandler> endMoveHandlers = new HashSet<ConnectionMoveEndHandler>();
	private final Set<ConnectionMoveStartHandler> startMoveHandlers = new HashSet<ConnectionMoveStartHandler>();
	private final Set<ConnectionMoveCancelHandler> moveCancelHandlers = new HashSet<ConnectionMoveCancelHandler>();
	@Inject
	private TouchEventChecker touchEventChecker;
	@Inject
	protected EventsBus eventsBus;
	@Inject
	private PositionHelper positionHelper;

	private final static boolean NOT_MOBILE_BROWSER = !UserAgentChecker.isMobileUserAgent();

	@Inject
	protected TouchRecognitionFactory touchRecognitionFactory;

	private boolean drawFollowTouch;

	@Override
	public void setDrawFollowTouch(boolean followTouch) {
		this.drawFollowTouch = followTouch;
	}

	public boolean isDrawFollowTouch() {
		return drawFollowTouch;
	}

	@Override
	public void addElementToMainView(IsWidget widget) {
		getView().add(widget);
	}

	@Override
	public void addConnectionMoveHandler(ConnectionMoveHandler handler) {
		handlers.add(handler);
	}

	@Override
	public void addConnectionMoveEndHandler(ConnectionMoveEndHandler handler) {
		endMoveHandlers.add(handler);
	}

	@Override
	public void addConnectionMoveStartHandler(ConnectionMoveStartHandler handler) {
		startMoveHandlers.add(handler);
	}

	@Override
	public void addConnectionMoveCancelHandler(ConnectionMoveCancelHandler handler) {
		moveCancelHandlers.add(handler);
	}

	protected void callOnMoveHandlers(ConnectionMoveEvent event) {
		for (ConnectionMoveHandler handler : handlers) {
			handler.onConnectionMove(event);
		}
	}

	protected void callOnMoveEndHandlers(ConnectionMoveEndEvent event) {
		for (ConnectionMoveEndHandler handler : endMoveHandlers) {
			handler.onConnectionMoveEnd(event);
		}
	}

	protected void callOnMoveStartHandlers(ConnectionMoveStartEvent event) {
		for (ConnectionMoveStartHandler handler : startMoveHandlers) {
			handler.onConnectionStart(event);
		}
	}

	protected void callOnMoveCancelHandlers() {
		for (ConnectionMoveCancelHandler handler : moveCancelHandlers) {
			handler.onConnectionMoveCancel();
		}
	}

	public void onTouchMove(NativeEvent event) {
		if (getView() != null) {
			if (NOT_MOBILE_BROWSER) {
				event.preventDefault();
			}
			callOnMoveHandlers(new ConnectionMoveEvent(getPositionX(event), getPositionY(event), event));
		}
	}

	public void addFirstColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addFirstColumnItem(item);
		}
	}

	public void addSecondColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addSecondColumnItem(item);
		}
	}

	protected int getPositionX(NativeEvent event) {
		return positionHelper.getXPositionRelativeToTarget(event, getView().getElement());
	}

	protected int getPositionY(NativeEvent event) {
		return positionHelper.getYPositionRelativeToTarget(event, getView().getElement());
	}

	@Override
	public void onTouchEvent(TouchEvent event) {
		NativeEvent nativeEvent = event.getNativeEvent();

		JsArray<Touch> touches = nativeEvent.getTouches();
		boolean isMouseClick = (touches == null); // method is also called when
													// MouseEvents occur
		boolean isOneFingerTouch = touchEventChecker.isOnlyOneFinger(touches);

		switch (event.getType()) {
		case TOUCH_START:
			if (isMouseClick || isOneFingerTouch) {
				onTouchStart(nativeEvent);
			} else {
				onTouchCancel(nativeEvent);
			}
			break;
		case TOUCH_END:
			onTouchEnd(nativeEvent);
			break;
		case TOUCH_MOVE:
			if (isMouseClick || isOneFingerTouch) {
				onTouchMove(nativeEvent);
			} else {
				onTouchCancel(nativeEvent);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public int getHeight() {
		return getOffsetHeight();
	}

	@Override
	public int getWidth() {
		return getOffsetWidth();
	}

	protected abstract FlowPanel getView();

	@Override
	public abstract void addFirstColumnItem(ConnectionItem item);

	@Override
	public abstract void addSecondColumnItem(ConnectionItem item);

	public abstract void onTouchStart(NativeEvent event);

	public abstract void onTouchEnd(NativeEvent event);

	public abstract void onTouchCancel(NativeEvent event);
}
