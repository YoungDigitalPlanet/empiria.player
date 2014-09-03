package eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

public class PointerEventsCoordinates {

	private final Map<Long, Point> pointerPoints = new LinkedHashMap<Long, Point>();

	public void addEvent(PointerEvent<?> pointerEvent) {
		if (pointerEvent.isPrimary() && !pointerPoints.containsKey(pointerEvent.getPointerId())) {
			pointerPoints.clear();
		}
		pointerPoints.put(pointerEvent.getPointerId(), new Point(pointerEvent.getClientX(), pointerEvent.getClientY()));

	}

	public Point getPoint(int index) {
		return (Point) pointerPoints.values().toArray()[index];
	}

	public void removeEvent(PointerEvent<?> pointerEvent) {

		pointerPoints.remove(pointerEvent.getPointerId());
	}

	public int getLength() {
		return pointerPoints.size();
	}

	public boolean isMultiTouch() {
		return getLength() > 1;
	}

	public TouchOnImageEvent getTouchOnImageEvent() {
		return new TouchOnImageEvent(new ArrayList<Point>(pointerPoints.values()));
	}

}
