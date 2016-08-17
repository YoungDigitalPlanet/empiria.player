package eu.ydp.empiria.player.client.util.events.internal.emulate;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.ITouchHandlerInitializer;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.TouchHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.TouchHandlerProvider;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;

//TODO dopisac rozpoznawanie gestow
public class TouchRecognition extends AbstractEventHandler<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers, MouseDownHandler, MouseUpHandler,
        MouseMoveHandler, MouseOutHandler {
    private final Widget listenOn;
    private final boolean emulateClickAsTouch;
    private final boolean globalTouchEnd;
    private final ITouchHandlerInitializer touchHandlerInitializer;
    private final PlayerContentView playerContentView;
    private boolean touchMoveHandlers = false;
    private boolean isMouseDown = false;

    @AssistedInject
    public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
                            TouchHandlerProvider touchHandlersProvider, PlayerContentView playerContentView) {
        this(listenOn, emulateClickAsTouch, false, touchHandlersProvider, playerContentView);
    }

    @AssistedInject
    public TouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
                            @Assisted("globalTouchEnd") Boolean globalTouchEnd, TouchHandlerProvider touchHandlerProvider,
                            PlayerContentView playerContentView) {
        this.listenOn = listenOn;
        this.emulateClickAsTouch = emulateClickAsTouch;
        this.globalTouchEnd = globalTouchEnd;
        this.touchHandlerInitializer = touchHandlerProvider.getTouchHandlersInitializer();
        this.playerContentView = playerContentView;
    }

    private void addTouchMoveHandlers() {
        if (!touchMoveHandlers) {
            touchHandlerInitializer.addTouchMoveHandler(createTouchMoveHandler(), listenOn);
            if (emulateClickAsTouch) {
                getListenOnWidget().addDomHandler(this, MouseMoveEvent.getType());
            }
            touchMoveHandlers = true;
        }
    }

    private void addTouchEndHandlers() {
        touchHandlerInitializer.addTouchEndHandler(createTouchEndHandler(), listenOn);

        if (emulateClickAsTouch) {
            getListenOnWidget().addDomHandler(this, MouseUpEvent.getType());
        }
    }

    private void addTouchStartHandlers() {
        touchHandlerInitializer.addTouchStartHandler(createTouchStartHandler(), listenOn);

        if (emulateClickAsTouch) {
            listenOn.addDomHandler(this, MouseDownEvent.getType());
        }
    }

    private void addTouchCancelHandlers() {
        touchHandlerInitializer.addTouchCancelHandler(createTouchCancelHandler(), listenOn);
        if (emulateClickAsTouch) {
            getListenOnWidget().addDomHandler(this, MouseOutEvent.getType());
        }
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
    public HandlerRegistration addTouchHandler(TouchHandler handler, EventType<TouchHandler, TouchTypes> event) {
        addTouchHandlers((TouchTypes) event.getType());
        return addHandler(handler, event);
    }

    @Override
    public HandlerRegistration[] addTouchHandlers(TouchHandler handler, EventType<TouchHandler, TouchTypes>... events) {
        for (EventType<TouchHandler, TouchTypes> event : events) {
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
        isMouseDown = false;
        touchEnd(event.getNativeEvent());
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {
        isMouseDown = true;
        touchStart(event.getNativeEvent());
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        if (isMouseDown) {
            touchCancel(event.getNativeEvent());
        }
    }

    private TouchOnMoveHandler createTouchMoveHandler() {
        return new TouchOnMoveHandler() {

            @Override
            public void onMove(NativeEvent event) {
                touchMove(event);

            }
        };
    }

    private TouchOnEndHandler createTouchEndHandler() {
        return new TouchOnEndHandler() {

            @Override
            public void onEnd(NativeEvent event) {
                touchEnd(event);
            }
        };
    }

    private TouchOnStartHandler createTouchStartHandler() {
        return new TouchOnStartHandler() {

            @Override
            public void onStart(NativeEvent event) {
                touchStart(event);
            }
        };
    }

    private TouchOnCancelHandler createTouchCancelHandler() {
        return new TouchOnCancelHandler() {

            @Override
            public void onCancel(NativeEvent event) {
                touchCancel(event);
            }
        };
    }

    private Widget getListenOnWidget() {
        if (globalTouchEnd) {
            return playerContentView;
        }

        return listenOn;
    }
}
