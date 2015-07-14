package eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer;

import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerMoveHandler;

public class PointerMoveEvent extends PointerEvent<PointerMoveHandler> {

    private static final Type<PointerMoveHandler> TYPE = new Type<PointerMoveHandler>(PointerEventsConstants.POINTER_MOVE, new PointerMoveEvent());

    protected PointerMoveEvent() {
    }

    public static Type<PointerMoveHandler> getType() {
        return TYPE;
    }

    @Override
    public com.google.gwt.event.dom.client.DomEvent.Type<PointerMoveHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PointerMoveHandler handler) {
        handler.onPointerMove(this);
    }

}
