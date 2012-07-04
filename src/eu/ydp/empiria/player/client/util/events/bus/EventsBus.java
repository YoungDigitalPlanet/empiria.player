package eu.ydp.empiria.player.client.util.events.bus;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.EventHandler;
import eu.ydp.empiria.player.client.util.events.Event.Type;

public interface EventsBus {

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandler(Type<H, T> type, H handler);

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H, T> type, Object source, H handler);

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H, T> type, H handler);

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H, T> type, Object source, H handler);

	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event);

	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventWithCallback(E event);

	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventFromSource(E event, Object source);

	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEvent(E event);

	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEventFromSource(E event, Object source);

}