package eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events;

import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.PointerEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.PointerEventsConstants;
import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers.PointerDownHandler;

public class PointerDownEvent extends PointerEvent<PointerDownHandler> {

	private static final Type<PointerDownHandler> TYPE = new Type<PointerDownHandler>(PointerEventsConstants.POINTER_DOWN, new PointerDownEvent());

	protected PointerDownEvent() {
	}

	public static Type<PointerDownHandler> getType() {
		return TYPE;
	}

	@Override
	public com.google.gwt.event.dom.client.DomEvent.Type<PointerDownHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PointerDownHandler handler) {
		handler.onPointerDown(this);
	}

}
