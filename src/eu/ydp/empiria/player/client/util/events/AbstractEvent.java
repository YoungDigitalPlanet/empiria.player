package eu.ydp.empiria.player.client.util.events;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.util.events.scope.EventScope;

public abstract class AbstractEvent<H,E extends Enum<?>> extends Event<H> {
	private static  Map<Enum<?>, Type<EventHandler>> types = new HashMap<Enum<?>, Event.Type<EventHandler>>();
	protected E type;
	private EventScope<?> scope;

	public AbstractEvent(E type, EventScope<?> scope) {
		this.type = type;
		this.scope = scope;

	}

	protected static void checkIsPresent(Enum type){
		if(!types.containsKey(type)){
		//	types.put(type, (eu.ydp.empiria.player.client.util.events.Event.Type<EventHandler<?>>) new Type(type,scope));
		}
	}
	@Override
	public Event.Type<H> getAssociatedType() {
		checkIsPresent(type);
		return (eu.ydp.empiria.player.client.util.events.Event.Type<H>) types.get(type);
	}

	public E getType() {
		return type;
	}

	protected static Type get(Enum type){
		return null;
	}

	@Override
	public abstract void dispatch(H handler);

}
