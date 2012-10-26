package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.AbstractEventHandlers;
import eu.ydp.empiria.player.client.util.events.Event.Type;

//TODO dopisac rozpoznawanie gestow
public class TouchRecognition extends AbstractEventHandlers<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers, TouchStartHandler,
		TouchEndHandler, TouchMoveHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler {
	private final Widget listenOn;
	private boolean touchMoveHandlers = false;
	private boolean touchStartHandlers = false;
	private boolean touchEndHandlers = false;
	private boolean emulateClickAsTouch = true;

	@Inject
	public TouchRecognition(@Assisted Widget listenOn, @Assisted Boolean emulateClickAsTouch) {
		this.listenOn = listenOn;
		this.emulateClickAsTouch = emulateClickAsTouch.booleanValue();
	}

	private void addTouchMoveHandlers() {
		if (!touchMoveHandlers) {
			listenOn.addDomHandler(this, TouchMoveEvent.getType());
			if (emulateClickAsTouch) {
				listenOn.addDomHandler(this, MouseMoveEvent.getType());
			}
			touchMoveHandlers = true;
		}
	}

	private void addTouchEndHandlers() {
		if (!touchEndHandlers) {
			listenOn.addDomHandler(this, TouchEndEvent.getType());
			if (emulateClickAsTouch) {
				listenOn.addDomHandler(this, MouseUpEvent.getType());
			}
			touchEndHandlers = false;
		}
	}

	private void addTouchStartHandlers() {
		if (!touchStartHandlers) {
			listenOn.addDomHandler(this, TouchStartEvent.getType());
			if (emulateClickAsTouch) {
				listenOn.addDomHandler(this, MouseDownEvent.getType());
			}
			touchStartHandlers = false;
		}
	}

	private void checkEvent(NativeEvent event) {
//		if (!(listenOn instanceof FocusWidget) && UserAgentChecker.isStackAndroidBrowser()) {
//	//		event.preventDefault();
//		}
	}

	private void touchStart(NativeEvent event) {
		checkEvent(event);
		fireEvent(new TouchEvent(TouchTypes.TOUCH_START, event));
	}

	private void touchEnd(NativeEvent event) {
		checkEvent(event);
		fireEvent(new TouchEvent(TouchTypes.TOUCH_END, event));
	}

	private void touchMove(NativeEvent event) {
		checkEvent(event);
		fireEvent(new TouchEvent(TouchTypes.TOUCH_MOVE, event));
	}

	@Override
	protected void dispatchEvent(TouchHandler handler, TouchEvent event) {
		handler.onTouchEvent(event);
	}

	private void addTouchHandlers(TouchTypes type) {
		switch (type) {
		case TOUCH_END:
			addTouchEndHandlers();
			break;
		case TOUCH_START:
			addTouchStartHandlers();
			break;
		case TOUCH_MOVE:
			addTouchMoveHandlers();
			break;
		default:
			break;
		}

	}

	@Override
	public HandlerRegistration addTouchHandler(TouchHandler handler, Type<TouchHandler, TouchTypes> event) {
		addTouchHandlers((TouchTypes) event.getType());
		return addHandler(handler, event);
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		touchMove(event.getNativeEvent());
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		touchEnd(event.getNativeEvent());
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		touchStart(event.getNativeEvent());

	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		touchMove(event.getNativeEvent());
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		touchEnd(event.getNativeEvent());

	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		touchStart(event.getNativeEvent());

	}
}
