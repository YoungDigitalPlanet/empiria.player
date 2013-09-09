package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;

public class CanvasDragHandlers {

	private final EventsBus eventsBus;
	
	@Inject
	public CanvasDragHandlers(EventsBus eventsBus) {
		this.eventsBus = eventsBus;
	}

	public void addHandlersToView(final CanvasPresenter canvasPresenter, Canvas canvas) {
		final Element canvasElement = canvas.asWidget().getElement();
		
		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				Point point = new Point(event.getX(), event.getY());
				canvasPresenter.mouseDown(point);
			}
		});
		
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				Point point = new Point(event.getX(), event.getY());
				canvasPresenter.mouseMove(point);
			}
		});
		
		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				canvasPresenter.mouseUp();
			}
		});

		canvas.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				canvasPresenter.mouseOut();
			}
		});
		
		canvas.addTouchStartHandler(new TouchStartHandler() {
			@Override
			public void onTouchStart(TouchStartEvent event) {
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
				event.preventDefault();
				Point point = getTouchPoint(event.getTouches(), canvasElement);
				canvasPresenter.mouseDown(point);
			}
		});
		
		canvas.addTouchMoveHandler(new TouchMoveHandler() {
			@Override
			public void onTouchMove(TouchMoveEvent event) {
				Point point = getTouchPoint(event.getTouches(), canvasElement);
				canvasPresenter.mouseMove(point);
			}
		});
		
		canvas.addTouchEndHandler(new TouchEndHandler() {
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				canvasPresenter.mouseUp();
			}
		});
		
		canvas.addTouchCancelHandler(new TouchCancelHandler() {
			@Override
			public void onTouchCancel(TouchCancelEvent event) {
				canvasPresenter.mouseOut();
			}
		});
	}
	
	private Point getTouchPoint(JsArray<Touch> touches, Element canvasElement) {
		Touch touch = touches.get(0);
		Point point = new Point(touch.getRelativeX(canvasElement), touch.getRelativeY(canvasElement));
		return point;
	}
}
