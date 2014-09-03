package eu.ydp.empiria.player.client.module.img.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerMoveHandler;

public class PointerMoveHandlerImpl implements PointerMoveHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler;

	public PointerMoveHandlerImpl(final TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnMoveHandler = touchOnMoveHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerMove(PointerMoveEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.addEvent(event);
			touchOnMoveHandler.onMove(pointerEventsCoordinates);
		}
	}

}
