package eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events;

import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.PointerEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.PointerEventsConstants;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers.PointerUpHandler;

public class PointerUpEvent extends PointerEvent<PointerUpHandler> {

	private static final Type<PointerUpHandler> TYPE = new Type<PointerUpHandler>(PointerEventsConstants.POINTER_UP, new PointerUpEvent());

	protected PointerUpEvent() {
	}

	public static Type<PointerUpHandler> getType() {
		return TYPE;
	}

	@Override
	public com.google.gwt.event.dom.client.DomEvent.Type<PointerUpHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PointerUpHandler handler) {
		handler.onPointerUp(this);
	}

}
