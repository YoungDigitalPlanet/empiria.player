package eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates;

import java.util.LinkedHashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

public class PointerEventsCoordinates implements EventsCoordinates<PointerEvent> {

	private final Map<Long, Point> pointerPoints = new LinkedHashMap<Long, Point>();

	@Override
	public void addEvent(PointerEvent pointerEvent) {
		if (pointerEvent.isPrimary() && !pointerPoints.containsKey(pointerEvent.getPointerId())) {
			pointerPoints.clear();
		}
		pointerPoints.put(pointerEvent.getPointerId(), new Point(pointerEvent.getClientX(), pointerEvent.getClientY()));

	}

	@Override
	public Point getEvent(int index) {
		return (Point) pointerPoints.values().toArray()[index];
	}

	@Override
	public void removeEvent(PointerEvent pointerEvent) {

		pointerPoints.remove(pointerEvent.getPointerId());
	}

	@Override
	public int getLength() {
		return pointerPoints.size();
	}

	public boolean isMultiTouch() {
		return getLength() > 1;
	}

}
