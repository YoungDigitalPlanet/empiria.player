package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.TouchHandler;
import eu.ydp.gwtutil.client.event.AbstractEventHandler;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

//TODO dopisac rozpoznawanie gestow
public class TouchRecognition extends AbstractEventHandler<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers, MouseDownHandler, MouseUpHandler,
		MouseMoveHandler {
	private final Widget listenOn;
	private boolean touchMoveHandlers = false;
	private boolean emulateClickAsTouch = true;
	private boolean globalTouchEnd;
	private final TouchHandlersInitializer touchHandlersInitializer;

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, UserAgentUtil userAgentUtil, TouchHandlersInitializer touchHandlersInitializer) {
		this.listenOn = listenOn;
		this.touchHandlersInitializer = touchHandlersInitializer;
	}

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
			TouchHandlersInitializer touchHandlersInitializer) {
		this.listenOn = listenOn;
		this.emulateClickAsTouch = emulateClickAsTouch.booleanValue();
		this.touchHandlersInitializer = touchHandlersInitializer;
	}

	@AssistedInject
	public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
			@Assisted("globalTouchEnd") Boolean globalTouchEnd, TouchHandlersInitializer touchHandlersInitializer) {
		this.listenOn = listenOn;
		this.emulateClickAsTouch = emulateClickAsTouch.booleanValue();
		this.globalTouchEnd = globalTouchEnd.booleanValue();
		this.touchHandlersInitializer = touchHandlersInitializer;
	}

	private void addTouchMoveHandlers() {
		if (!touchMoveHandlers) {
			touchHandlersInitializer.addTouchMoveHandler(createTouchMoveHandler(), listenOn);
			touchMoveHandlers = true;
		}
	}

	private void addTouchEndHandlers() {
		touchHandlersInitializer.addTouchEndHandler(createTouchEndHandler(), listenOn);

		if (emulateClickAsTouch) {
			((globalTouchEnd) ? RootPanel.get() : listenOn).addDomHandler(this, MouseUpEvent.getType());
		}
	}

	private void addTouchStartHandlers() {
		touchHandlersInitializer.addTouchStartHandler(createTouchStartHandler(), listenOn);

		if (emulateClickAsTouch) {
			listenOn.addDomHandler(this, MouseDownEvent.getType());
		}
	}

	private void addTouchCancelHandlers() {
		touchHandlersInitializer.addTouchCancelHandler(createTouchCancelHandler(), listenOn);
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

	private CreateTouchMoveHandler createTouchMoveHandler() {
		return new CreateTouchMoveHandler() {

			@Override
			public void onMove(NativeEvent nativeEvent) {
				touchMove(nativeEvent);

			}
		};
	}

	private CreateTouchEndHandler createTouchEndHandler() {
		return new CreateTouchEndHandler() {

			@Override
			public void onEnd(NativeEvent nativeEvent) {
				touchEnd(nativeEvent);
			}
		};
	}

	private CreateTouchStartHandler createTouchStartHandler() {
		return new CreateTouchStartHandler() {

			@Override
			public void onStart(NativeEvent nativeEvent) {
				touchStart(nativeEvent);
			}
		};
	}

	private CreateTouchCancelHandler createTouchCancelHandler() {
		return new CreateTouchCancelHandler() {

			@Override
			public void onCancel(NativeEvent nativeEvent) {
				touchCancel(nativeEvent);
			}
		};
	}
}
