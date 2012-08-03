package eu.ydp.empiria.player.client.controller.multiview;

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
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.gwtutil.client.debug.Debug;

public class PageEvents implements TouchStartHandler, TouchCancelHandler, TouchEndHandler, TouchMoveHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler, MouseOutHandler {
	private final MultiPageView view;

	public PageEvents(MultiPageView view) {
		RootPanel root=  RootPanel.get();
		root.addDomHandler(this, TouchStartEvent.getType());
		root.addDomHandler(this, TouchEndEvent.getType());
		root.addDomHandler(this, TouchCancelEvent.getType());
		root.addDomHandler(this, TouchMoveEvent.getType());
		if(Debug.isDebug()){
			root.addDomHandler(this, MouseOutEvent.getType());
			root.addDomHandler(this, MouseDownEvent.getType());
			root.addDomHandler(this, MouseUpEvent.getType());
			root.addDomHandler(this, MouseMoveEvent.getType());
		}
		this.view = view;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		view.onEvent(event.getNativeEvent());


	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		view.onEvent(event.getNativeEvent());

	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		view.onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		view.onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		view.onEvent(event.getNativeEvent());
	}

	@Override
	public void onTouchCancel(TouchCancelEvent event) {
		view.onEvent(event.getNativeEvent());

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		view.onEvent(event.getNativeEvent());

	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		view.onEvent(event.getNativeEvent());
	}

}
