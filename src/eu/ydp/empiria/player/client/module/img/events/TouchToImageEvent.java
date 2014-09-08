package eu.ydp.empiria.player.client.module.img.events;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchToImageEvent {

	public TouchOnImageEvent getTouchOnImageEvent(TouchEvent<? extends EventHandler> touchEvent) {
		List<Point> pointsList = getPoints(touchEvent);

		return new TouchOnImageEvent(pointsList);
	}

	private List<Point> getPoints(TouchEvent<? extends EventHandler> touchEvent) {
		List<Point> points = new ArrayList<>();
		int touchesCount = touchEvent.getTouches().length();

		for (int i = 0; i < touchesCount; i++) {
			Touch touch = touchEvent.getTouches().get(i);
			Point point = new Point(touch.getClientX(), touch.getClientY());

			points.add(point);
		}

		return points;
	}
}
