package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieDragHandlersManager {

	private final IPlayerContainersAccessor accessor;
	private final EventsBus eventsBus;
	private StickieDragController stickieDragController;

	private List<HandlerRegistration> upMoveHandlersRegistrations = new ArrayList<HandlerRegistration>();

	@Inject
	public StickieDragHandlersManager(@Assisted StickieDragController stickieDragController, IPlayerContainersAccessor accessor, EventsBus eventsBus) {
		this.stickieDragController = stickieDragController;
		this.accessor = accessor;
		this.eventsBus = eventsBus;
	}

	public void mouseDown(MouseDownEvent event) {
		event.preventDefault();
		Point<Integer> point = new Point<Integer>(event.getScreenX(), event.getScreenY());
		onDragStart(point);
		registerMouseMoveHandler();
		registerMouseUpHandler();
	}

	public void touchStart(TouchStartEvent event) {
		Point<Integer> touchPoint = getTouchPoint(event.getTouches());
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));

		onDragStart(touchPoint);
		addTouchMoveHandler();
		addTouchEndHandler();
	}

	private void registerMouseUpHandler() {
		Type<MouseUpHandler> type = MouseUpEvent.getType();
		registerMouseHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				onDragEnd();
			}

		}, type);
	}

	private void removeMoveAndUpHandlersIfExists() {
		for (HandlerRegistration handlerRegistration : upMoveHandlersRegistrations) {
			handlerRegistration.removeHandler();
		}
		upMoveHandlersRegistrations.clear();
	}

	private void registerMouseMoveHandler() {
		registerMouseHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				Point<Integer> mouseMovePoint = new Point<Integer>(event.getScreenX(), event.getScreenY());
				onDragMove(event, mouseMovePoint);
			}
		}, MouseMoveEvent.getType());
	}

	private void addTouchEndHandler() {
		registerTouchHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				onDragEnd();
			}

		}, TouchEndEvent.getType());
	}

	private void addTouchMoveHandler() {
		registerTouchHandler(new TouchMoveHandler() {

			@Override
			public void onTouchMove(TouchMoveEvent event) {
				Point<Integer> touchMovePoint = getTouchPoint(event.getTouches());
				onDragMove(event, touchMovePoint);
			}
		}, TouchMoveEvent.getType());
	}

	private void onDragStart(Point<Integer> point) {
		stickieDragController.dragStart(point);
		removeMoveAndUpHandlersIfExists();
	}

	private void onDragMove(DomEvent<?> event, Point<Integer> movePosition) {
		stickieDragController.dragMove(movePosition);
		event.preventDefault();
	}

	private void onDragEnd() {
		stickieDragController.dragEnd();
		removeMoveAndUpHandlersIfExists();
	}

	private Point<Integer> getTouchPoint(JsArray<Touch> touches) {
		Touch touch = touches.get(0);
		Point<Integer> point = new Point<Integer>(touch.getScreenX(), touch.getScreenY());
		return point;
	}

	private <H extends EventHandler> HandlerRegistration registerMouseHandler(H handler, Type<H> type) {
		HandlerRegistration registration = ((Widget) accessor.getPlayerContainer()).addDomHandler(handler, type);

		upMoveHandlersRegistrations.add(registration);
		return registration;
	}

	private <H extends EventHandler> HandlerRegistration registerTouchHandler(H handler, Type<H> type) {
		HandlerRegistration registration = RootPanel.get().addDomHandler(handler, type);

		upMoveHandlersRegistrations.add(registration);
		return registration;
	}
}
