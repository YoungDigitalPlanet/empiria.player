package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerUpHandler;

public class PointerUpHandlerOnImage implements PointerUpHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnImageEndHandler touchOnImageEndHandler;

	@Inject
	public PointerUpHandlerOnImage(@Assisted TouchOnImageEndHandler touchOnImageEndHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnImageEndHandler = touchOnImageEndHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerUp(PointerUpEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.removeEvent(event);
			touchOnImageEndHandler.onEnd(pointerEventsCoordinates.getTouchOnImageEvent());
		}
	}
}
