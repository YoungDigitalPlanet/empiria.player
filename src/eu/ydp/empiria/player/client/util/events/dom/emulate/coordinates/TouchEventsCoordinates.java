package eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates;

import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;

import eu.ydp.empiria.player.client.util.position.Point;

public class TouchEventsCoordinates implements EventsCoordinates<TouchEvent> {

	private TouchEvent touchEvent = null;

	@Override
	public void addEvent(TouchEvent event) {
		touchEvent = event;
	}

	@Override
	public Point getEvent(int index) {
		Touch touch = (Touch) touchEvent.getTouches().get(index);

		return new Point(touch.getClientX(), touch.getClientY());
	}

	@Override
	public void removeEvent(TouchEvent event) {
		touchEvent = null;
	}

	@Override
	public int getLength() {
		return touchEvent.getTouches().length();
	}
}
