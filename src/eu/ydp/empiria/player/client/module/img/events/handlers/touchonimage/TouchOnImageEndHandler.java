package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;

public class TouchOnImageEndHandler {

	private final CanvasMoveEvents canvasMoveEvents;

	public TouchOnImageEndHandler(CanvasMoveEvents canvasMoveEvents) {
		this.canvasMoveEvents = canvasMoveEvents;
	}

	public void onEnd(TouchOnImageEvent touchOnImageEvent) {
		canvasMoveEvents.onMoveEnd();
	}
}
