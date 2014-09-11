package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageStartHandlerImpl implements TouchOnImageStartHandler {

	private final CanvasMoveEvents canvasMoveEvents;

	public TouchOnImageStartHandlerImpl(CanvasMoveEvents canvasMoveEvents) {
		this.canvasMoveEvents = canvasMoveEvents;
	}

	@Override
	public void onStart(TouchOnImageEvent touchOnImageEvent) {
		Point point = touchOnImageEvent.getPoint(0);
		canvasMoveEvents.onMoveStart(point);
	}
}
