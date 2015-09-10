package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.util.events.internal.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public interface HasTouchHandlers {
    public HandlerRegistration addTouchHandler(TouchHandler handler, EventType<TouchHandler, TouchTypes> event);

    public HandlerRegistration[] addTouchHandlers(TouchHandler handler, EventType<TouchHandler, TouchTypes>... event);

}
