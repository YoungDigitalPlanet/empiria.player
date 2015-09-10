package eu.ydp.empiria.player.client.util.events.internal;

import eu.ydp.gwtutil.client.event.EventHandlerRegistrator;
import eu.ydp.gwtutil.client.event.HandlerRegistration;

import java.util.Set;

public abstract class AbstractEventHandler<H extends EventHandler, E extends Enum<E>, EV extends Event<H, E>> extends EventHandlerRegistrator<H, EventType<H, E>> {
    protected HandlerRegistration[] addHandlers(final H handler, final EventType<H, E>[] event) {
        HandlerRegistration[] registrations = new HandlerRegistration[event.length];
        for (int x = 0; x < event.length; ++x) {
            registrations[x] = addHandler(handler, event[x]);
        }
        return registrations;
    }

    protected void fireEvent(EV event) {
        final Set<H> eventHandlers = getHandlersAccordingToRunningMode(event.getAssociatedType());
        for (H handler : eventHandlers) {
            dispatchEvent(handler, event);
        }
    }

    protected abstract void dispatchEvent(H handler, EV event);

}
