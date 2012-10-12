package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.NativeEvent;
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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;

public class PageEventsHandler implements TouchStartHandler, TouchCancelHandler, TouchEndHandler, TouchMoveHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler,
		MouseOutHandler {
	private TouchHandler handler;

	public PageEventsHandler() {
		RootPanel root = RootPanel.get();
		root.addDomHandler(this, TouchStartEvent.getType());
		root.addDomHandler(this, TouchEndEvent.getType());
		root.addDomHandler(this, TouchCancelEvent.getType());
		root.addDomHandler(this, TouchMoveEvent.getType());
//		if (Debug.isDebug()) {
//			root.addDomHandler(this, MouseOutEvent.getType());
//			root.addDomHandler(this, MouseDownEvent.getType());
//			root.addDomHandler(this, MouseUpEvent.getType());
//			root.addDomHandler(this, MouseMoveEvent.getType());
//		}
	}

	public void setTouchHandler(TouchHandler handler) {
		this.handler = handler;
	}

	public void removeTouchHandler(TouchHandler handler){
		if(handler.equals(this.handler)){
			this.handler = null;
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		onEvent(event.getNativeEvent());
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		onEvent(event.getNativeEvent());

	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		onEvent(event.getNativeEvent());
	}

	@Override
	public void onTouchCancel(TouchCancelEvent event) {
		onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		onEvent(event.getNativeEvent());

	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		onEvent(event.getNativeEvent());
	}

	public void onEvent(NativeEvent event) {
		if (handler != null) {
			switch (Event.getTypeInt(event.getType())) {
			case Event.ONMOUSEDOWN:
			case Event.ONTOUCHSTART:
				handler.onTouchStart(event);
				break;
			case Event.ONMOUSEUP:
			case Event.ONTOUCHEND:
			case Event.ONTOUCHCANCEL:
			case Event.ONMOUSEOUT:
				handler.onTouchEnd(event);
				break;
			case Event.ONMOUSEMOVE:
			case Event.ONTOUCHMOVE:
				handler.onTouchMove(event);
				break;
			default:
				break;
			}
		}
	}

}
