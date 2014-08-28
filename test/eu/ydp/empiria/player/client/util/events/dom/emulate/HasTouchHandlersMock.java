package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.TouchHandler;
import eu.ydp.gwtutil.client.event.AbstractEventHandler;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

public class HasTouchHandlersMock extends AbstractEventHandler<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers {

	@Override
	public HandlerRegistration addTouchHandler(TouchHandler handler, Type<TouchHandler, TouchTypes> event) {
		return addHandler(handler, event);
	}

	@Override
	protected void dispatchEvent(TouchHandler handler, TouchEvent event) {
		handler.onTouchEvent(event);
	}

	public void emulateEvent(TouchEvent event) {
		fireEvent(event);
	}

	@Override
	public HandlerRegistration[] addTouchHandlers(TouchHandler handler, Type<TouchHandler, TouchTypes>... event) {
		HandlerRegistration[] reg = new HandlerRegistration[event.length];
		for (int x = 0; x < reg.length; ++x) {
			reg[x] = addHandler(handler, event[x]);
		}
		return reg;
	}

}
