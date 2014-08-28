package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.TouchHandler;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

public interface HasTouchHandlers {
	public HandlerRegistration addTouchHandler(TouchHandler handler, Type<TouchHandler, TouchTypes> event);

	public HandlerRegistration[] addTouchHandlers(TouchHandler handler, Type<TouchHandler, TouchTypes>... event);

}
