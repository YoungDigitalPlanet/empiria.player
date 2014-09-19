package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageStartHandler {

	private final CanvasMoveEvents canvasMoveEvents;

	public TouchOnImageStartHandler(CanvasMoveEvents canvasMoveEvents) {
		this.canvasMoveEvents = canvasMoveEvents;
	}

	public void onStart(TouchOnImageEvent touchOnImageEvent) {
		Point point = touchOnImageEvent.getPoint(0);
		canvasMoveEvents.onMoveStart(point);
	}
}
