package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers.PointerDownHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers.PointerMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers.PointerUpHandler;
import eu.ydp.gwtutil.client.event.AbstractEventHandler;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

//TODO dopisac rozpoznawanie gestow
public class TouchRecognition extends AbstractEventHandler<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers, TouchStartHandler,
		TouchEndHandler, TouchMoveHandler, TouchCancelHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler, PointerDownHandler, PointerUpHandler,
		PointerMoveHandler {
	private final Widget listenOn;
	private boolean touchMoveHandlers = false;
	private boolean emulateClickAsTouch = true;
	private boolean globalTouchEnd;
	private final UserAgentUtil userAgentUtil;

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, UserAgentUtil userAgentUtil) {
		this.listenOn = listenOn;
		this.userAgentUtil = userAgentUtil;
	}

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch, UserAgentUtil userAgentUtil) {
		this.listenOn = listenOn;
		this.emulateClickAsTouch = emulateClickAsTouch.booleanValue();
		this.userAgentUtil = userAgentUtil;
	}

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
			@Assisted("globalTouchEnd") Boolean globalTouchEnd, UserAgentUtil userAgentUtil) {
		this.listenOn = listenOn;
		this.emulateClickAsTouch = emulateClickAsTouch.booleanValue();
		this.globalTouchEnd = globalTouchEnd.booleanValue();
		this.userAgentUtil = userAgentUtil;
	}

	private void addTouchMoveHandlers() {
		if (!touchMoveHandlers) {
			if (userAgentUtil.isIE()) {
				listenOn.addDomHandler(this, PointerMoveEvent.getType());
			} else {
				listenOn.addDomHandler(this, TouchMoveEvent.getType());
			}

			if (emulateClickAsTouch) {
				listenOn.addDomHandler(this, MouseMoveEvent.getType());
			}
			touchMoveHandlers = true;
		}
	}

	private void addTouchEndHandlers() {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(this, PointerUpEvent.getType());
		} else {
			listenOn.addDomHandler(this, TouchEndEvent.getType());
		}

		if (emulateClickAsTouch) {
			((globalTouchEnd) ? RootPanel.get() : listenOn).addDomHandler(this, MouseUpEvent.getType());
		}
	}

	private void addTouchStartHandlers() {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(this, PointerDownEvent.getType());
		} else {
			listenOn.addDomHandler(this, TouchStartEvent.getType());
		}
		if (emulateClickAsTouch) {
			listenOn.addDomHandler(this, MouseDownEvent.getType());
		}
	}

	private void addTouchCancelHandlers() {
		listenOn.addDomHandler(this, TouchCancelEvent.getType());
	}

	private void touchStart(NativeEvent event) {
		fireEvent(new TouchEvent(TouchTypes.TOUCH_START, event));
	}

	private void touchEnd(NativeEvent event) {
		fireEvent(new TouchEvent(TouchTypes.TOUCH_END, event));
	}

	private void touchMove(NativeEvent event) {
		fireEvent(new TouchEvent(TouchTypes.TOUCH_MOVE, event));
	}

	private void touchCancel(NativeEvent event) {
		fireEvent(new TouchEvent(TouchTypes.TOUCH_CANCEL, event));
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
		case TOUCH_CANCEL:
			addTouchCancelHandlers();
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
	public HandlerRegistration[] addTouchHandlers(TouchHandler handler, Type<TouchHandler, TouchTypes>... events) {
		for (Type<TouchHandler, TouchTypes> event : events) {
			addTouchHandlers((TouchTypes) event.getType());
		}
		return addHandlers(handler, events);
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

	@Override
	public void onTouchCancel(TouchCancelEvent event) {
		touchCancel(event.getNativeEvent());
	}

	@Override
	public void onPointerMove(PointerMoveEvent event) {
		if (!event.isTouchEvent()) {
			return;
		}
		touchMove(event.getNativeEvent());
	}

	@Override
	public void onPointerUp(PointerUpEvent event) {
		if (!event.isTouchEvent()) {
			return;
		}
		touchEnd(event.getNativeEvent());
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (!event.isTouchEvent()) {
			return;
		}
		touchStart(event.getNativeEvent());
	}
}
