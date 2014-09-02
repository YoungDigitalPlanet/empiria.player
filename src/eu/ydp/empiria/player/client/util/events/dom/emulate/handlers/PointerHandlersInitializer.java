package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;

public class PointerHandlersInitializer implements ITouchHandlerInitializer {

	@Override
	public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerMoveHandlerImpl(touchOnMoveHandler), PointerMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerDownHandlerImpl(touchStartHandler), PointerDownEvent.getType());
	}

	@Override
	public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerUpHandlerImpl(touchEndHandler), PointerUpEvent.getType());
	}

	@Override
	public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn) {
	}
}
