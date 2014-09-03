package eu.ydp.empiria.player.client.module.img.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerUpHandler;

public class PointerUpHandlerImpl implements PointerUpHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnEndHandler<EventsCoordinates> touchOnEndHandler;

	public PointerUpHandlerImpl(TouchOnEndHandler<EventsCoordinates> touchOnEndHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnEndHandler = touchOnEndHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerUp(PointerUpEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.removeEvent(event);
			touchOnEndHandler.onEnd(pointerEventsCoordinates);
		}
	}

}
