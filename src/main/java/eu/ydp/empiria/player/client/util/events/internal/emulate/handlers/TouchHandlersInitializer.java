package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchCancelHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchEndHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchStartHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchHandlersInitializer implements ITouchHandlerInitializer {

	@Override
	public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchMoveHandlerImpl(touchOnMoveHandler), TouchMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchStartHandlerImpl(touchStartHandler), TouchStartEvent.getType());

	}

	@Override
	public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchEndHandlerImpl(touchEndHandler), TouchEndEvent.getType());

	}

	@Override
	public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchCancelHandlerImpl(touchCancelHandler), TouchCancelEvent.getType());
	}
}
