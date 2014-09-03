package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;

public class TouchMoveHandlerImpl implements TouchMoveHandler {

	private final TouchEventsCoordinates touchEventsCoordinates;

	private final TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler;

	public TouchMoveHandlerImpl(TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler, final TouchEventsCoordinates touchEventsCoordinates) {
		this.touchOnMoveHandler = touchOnMoveHandler;
		this.touchEventsCoordinates = touchEventsCoordinates;
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		event.preventDefault();
		touchEventsCoordinates.addEvent(event);
		touchOnMoveHandler.onMove(touchEventsCoordinates);
	}

}
