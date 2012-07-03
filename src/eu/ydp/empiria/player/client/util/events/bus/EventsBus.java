package eu.ydp.empiria.player.client.util.events.bus;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.EventHandler;
import eu.ydp.empiria.player.client.util.events.Event.Type;

public interface EventsBus {

	public abstract <H extends EventHandler> HandlerRegistration addHandler(Type<H> type, H handler);

	public abstract <H extends EventHandler> HandlerRegistration addHandlerToSource(Type<H> type, Object source, H handler);

	public abstract <H extends EventHandler> HandlerRegistration addAsyncHandler(Type<H> type, H handler);

	public abstract <H extends EventHandler> HandlerRegistration addAsyncHandlerToSource(Type<H> type, Object source, H handler);

	public abstract <H extends EventHandler, E extends Event<H>> void fireEvent(E event);

	public abstract <H extends EventHandler, E extends Event<H>> void fireEventWithCallback(E event);

	public abstract <H extends EventHandler, E extends Event<H>> void fireEventFromSource(E event, Object source);

	public abstract <H extends EventHandler, E extends Event<H>> void fireAsyncEvent(E event);

	public abstract <H extends EventHandler, E extends Event<H>> void fireAsyncEventFromSource(E event, Object source);

}