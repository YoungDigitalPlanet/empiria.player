package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;

import javax.inject.Inject;

public class PointerMoveHandlerImpl implements PointerMoveHandler {

    private final TouchOnMoveHandler touchOnMoveHandler;

    @Inject
    public PointerMoveHandlerImpl(@Assisted TouchOnMoveHandler touchOnMoveHandler) {
        this.touchOnMoveHandler = touchOnMoveHandler;
    }

    @Override
    public void onPointerMove(PointerMoveEvent event) {
        if (event.isTouchEvent()) {
            touchOnMoveHandler.onMove(event.getNativeEvent());
        }
    }

}
