package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchStartHandlerImpl implements TouchStartHandler {

	private final TouchEventsCoordinates touchEventsCoordinates;

	private final TouchOnStartHandler<EventsCoordinates> touchStartHandler;

	public TouchStartHandlerImpl(final TouchOnStartHandler<EventsCoordinates> touchStartHandler, final TouchEventsCoordinates touchEventsCoordinates) {
		this.touchStartHandler = touchStartHandler;
		this.touchEventsCoordinates = touchEventsCoordinates;
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		event.preventDefault();
		touchEventsCoordinates.addEvent(event);
		touchStartHandler.onStart(touchEventsCoordinates);
	}
}
