package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerUpHandler;

public class PointerUpHandlerOnImage implements PointerUpHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnImageEndHandler touchOnEndHandler;

	public PointerUpHandlerOnImage(TouchOnImageEndHandler touchOnEndHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnEndHandler = touchOnEndHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerUp(PointerUpEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.removeEvent(event);
			touchOnEndHandler.onEnd(pointerEventsCoordinates.getTouchOnImageEvent());
		}
	}

}
