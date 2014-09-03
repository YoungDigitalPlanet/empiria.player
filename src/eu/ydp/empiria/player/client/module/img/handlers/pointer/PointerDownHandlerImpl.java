package eu.ydp.empiria.player.client.module.img.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerDownHandler;

public class PointerDownHandlerImpl implements PointerDownHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnStartHandler<EventsCoordinates> touchOnStartHandler;

	public PointerDownHandlerImpl(final TouchOnStartHandler<EventsCoordinates> touchOnStartHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnStartHandler = touchOnStartHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.addEvent(event);
			touchOnStartHandler.onStart(pointerEventsCoordinates);
		}
	}

}
