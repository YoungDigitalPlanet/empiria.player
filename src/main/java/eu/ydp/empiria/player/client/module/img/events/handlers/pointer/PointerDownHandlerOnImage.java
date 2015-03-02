package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerDownHandler;

public class PointerDownHandlerOnImage implements PointerDownHandler {

	private final PointerEventsCoordinates pointerEventsCoordinates;

	private final TouchOnImageStartHandler touchOnStartHandler;

	@Inject
	public PointerDownHandlerOnImage(@Assisted final TouchOnImageStartHandler touchOnStartHandler, final PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnStartHandler = touchOnStartHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (event.isTouchEvent()) {
			event.preventDefault();
			pointerEventsCoordinates.addEvent(event);
			touchOnStartHandler.onStart(pointerEventsCoordinates.getTouchOnImageEvent());
		}
	}

}
