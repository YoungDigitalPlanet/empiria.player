package eu.ydp.empiria.player.client.util.events.player;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class PlayerEvent extends Event<PlayerEventHandler,PlayerEventTypes> {
	public static Map<PlayerEventTypes, Type<PlayerEventHandler,PlayerEventTypes>> types = new HashMap<PlayerEventTypes, Event.Type<PlayerEventHandler,PlayerEventTypes>>();

	PlayerEventTypes type = null;

	private Object value;

	public PlayerEvent(PlayerEventTypes type, Object source, Object value) {
		this.type = type;
		this.value = value;
		setSource(source);
	}

	public PlayerEvent(PlayerEventTypes type) {
		this(type, null, null);
	}

	public Object getValue() {
		return value;
	}

	public PlayerEventTypes getType() {
		return type;
	}

	private static void checkIsPresent(PlayerEventTypes type) {
		if (!types.containsKey(type)) {
			types.put(type, new Type<PlayerEventHandler,PlayerEventTypes>(type, new CurrentPageScope()));
		}
	}

	@Override
	public Event.Type<PlayerEventHandler,PlayerEventTypes> getAssociatedType() {
		checkIsPresent(type);
		return types.get(type);
	}

	@Override
	public void dispatch(PlayerEventHandler handler) {
		handler.onPlayerEvent(this);
	}

	public static Type<PlayerEventHandler,PlayerEventTypes> getType(PlayerEventTypes type) {
		checkIsPresent(type);
		return types.get(type);
	}

}
