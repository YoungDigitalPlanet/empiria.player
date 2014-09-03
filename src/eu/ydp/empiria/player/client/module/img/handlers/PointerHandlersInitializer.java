package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.ITouchHandlerInitializer;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;

public class PointerHandlersInitializer implements ITouchHandlerInitializer<EventsCoordinates> {

	@Inject
	PointerEventsCoordinates pointerEventsCoordinates;

	@Override
	public void addTouchMoveHandler(final TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerMoveHandlerImpl(touchOnMoveHandler, pointerEventsCoordinates), PointerMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnStartHandler<EventsCoordinates> touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerDownHandlerImpl(touchStartHandler, pointerEventsCoordinates), PointerDownEvent.getType());
	}

	@Override
	public void addTouchEndHandler(final TouchOnEndHandler<EventsCoordinates> touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerUpHandlerImpl(touchEndHandler, pointerEventsCoordinates), PointerUpEvent.getType());
	}

	@Override
	public void addTouchCancelHandler(TouchOnCancelHandler<EventsCoordinates> touchCancelHandler, Widget listenOn) {
		// TODO Auto-generated method stub
	}
}
