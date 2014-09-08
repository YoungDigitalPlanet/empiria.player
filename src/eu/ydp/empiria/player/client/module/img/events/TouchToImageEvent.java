package eu.ydp.empiria.player.client.module.img.events;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchToImageEvent {

	private Point getPoint(TouchEvent<? extends EventHandler> touchEvent, int index) {
		Touch touch = touchEvent.getTouches().get(index);

		return new Point(touch.getClientX(), touch.getClientY());
	}

	private int getLength(TouchEvent<? extends EventHandler> touchEvent) {
		return touchEvent.getTouches().length();
	}

	public TouchOnImageEvent getTouchOnImageEvent(TouchEvent<? extends EventHandler> touchEvent) {
		List<Point> pointsList = new ArrayList<>();
		if (touchEvent != null) {

			for (int i = 0; i < getLength(touchEvent); i++) {
				pointsList.add(getPoint(touchEvent, i));
			}
		}
		return new TouchOnImageEvent(pointsList);
	}
}
