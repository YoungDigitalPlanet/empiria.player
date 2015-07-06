package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public class PointerDownHandlerImpl implements PointerDownHandler {

	private final TouchOnStartHandler touchOnStartHandler;
	private final PointerEventsCoordinates pointerEventsCoordinates;

	@Inject
	public PointerDownHandlerImpl(@Assisted TouchOnStartHandler touchOnStartHandler, PointerEventsCoordinates pointerEventsCoordinates) {
		this.touchOnStartHandler = touchOnStartHandler;
		this.pointerEventsCoordinates = pointerEventsCoordinates;
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (event.isTouchEvent()) {
			pointerEventsCoordinates.addEvent(event);
			touchOnStartHandler.onStart(event.getNativeEvent());
		}
	}

}
