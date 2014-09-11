package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;

public class TouchOnImageEndHandlerImpl implements TouchOnImageEndHandler {

	private final CanvasMoveEvents canvasMoveEvents;

	public TouchOnImageEndHandlerImpl(CanvasMoveEvents canvasMoveEvents) {
		this.canvasMoveEvents = canvasMoveEvents;
	}

	@Override
	public void onEnd(TouchOnImageEvent touchOnImageEvent) {
		canvasMoveEvents.onMoveEnd();
	}
}
