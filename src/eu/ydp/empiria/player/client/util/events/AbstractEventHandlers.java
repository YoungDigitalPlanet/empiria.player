package eu.ydp.empiria.player.client.util.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event.Type;

public abstract class AbstractEventHandlers<H extends EventHandler, E extends Enum<E>, EV extends Event<H, E>> {
	private final Map<Type<H, E>, Set<H>> handlers = new HashMap<Type<H, E>, Set<H>>();

	protected HandlerRegistration addHandler(final H handler, final Type<H, E> event) {
		final Set<H> eventHandlers = getHandlers(event);
		eventHandlers.add(handler);
		HandlerRegistration registration = new HandlerRegistration() {

			@Override
			public void removeHandler() {
				eventHandlers.remove(handler);
			}
		};
		return registration;
	}

	protected Set<H> getHandlers(Type<H, E> event) {
		Set<H> eventHandlers = null;
		if ((eventHandlers = handlers.get(event)) == null) {
			eventHandlers = new HashSet<H>();
			handlers.put(event, eventHandlers);
		}
		return eventHandlers;
	}

	protected void fireEvent(EV event) {
		// concurrentModificationException in dev mode
		final Set<H> eventHandlers = GWT.isProdMode() ? getHandlers(event.getAssociatedType()) : new HashSet<H>(getHandlers(event.getAssociatedType()));
		for (H handler : eventHandlers) {
			dispatchEvent(handler, event);
		}
	}

	protected abstract void dispatchEvent(H handler, EV event);

}
