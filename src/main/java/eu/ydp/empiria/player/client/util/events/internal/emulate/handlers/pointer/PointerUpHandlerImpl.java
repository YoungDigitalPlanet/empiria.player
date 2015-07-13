package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;

import javax.inject.Inject;

public class PointerUpHandlerImpl implements PointerUpHandler {

    private final TouchOnEndHandler touchOnEndHandler;
    private final PointerEventsCoordinates pointerEventsCoordinates;

    @Inject
    public PointerUpHandlerImpl(@Assisted TouchOnEndHandler touchOnEndHandler, PointerEventsCoordinates pointerEventsCoordinates) {
        this.touchOnEndHandler = touchOnEndHandler;
        this.pointerEventsCoordinates = pointerEventsCoordinates;
    }

    @Override
    public void onPointerUp(PointerUpEvent event) {
        if (event.isTouchEvent()) {
            pointerEventsCoordinates.removeEvent(event);
            touchOnEndHandler.onEnd(event.getNativeEvent());
        }
    }

}
