package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;

public class TouchEndHandlerImpl implements TouchEndHandler {

	private final TouchEventsCoordinates touchEventsCoordinates;

	private final TouchOnEndHandler<EventsCoordinates> touchOnEndHandler;

	public TouchEndHandlerImpl(final TouchOnEndHandler<EventsCoordinates> touchOnEndHandler, final TouchEventsCoordinates touchEventsCoordinates) {
		this.touchOnEndHandler = touchOnEndHandler;
		this.touchEventsCoordinates = touchEventsCoordinates;
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		event.preventDefault();
		touchEventsCoordinates.removeEvent(event);
		touchOnEndHandler.onEnd(touchEventsCoordinates);
	}
}
