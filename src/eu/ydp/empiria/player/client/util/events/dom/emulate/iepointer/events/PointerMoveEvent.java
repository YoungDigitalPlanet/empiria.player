package eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events;

import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.PointerEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.PointerEventsConstants;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerMoveHandler;

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
