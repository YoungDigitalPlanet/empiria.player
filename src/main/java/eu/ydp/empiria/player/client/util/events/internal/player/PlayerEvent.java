package eu.ydp.empiria.player.client.util.events.internal.player;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.gwtutil.client.event.EventType;

public class PlayerEvent extends AbstractEvent<PlayerEventHandler, PlayerEventTypes> {
    public static EventTypes<PlayerEventHandler, PlayerEventTypes> types = new EventTypes<PlayerEventHandler, PlayerEventTypes>();
    private final Object value;

    public PlayerEvent(PlayerEventTypes type, Object value, Object source) {
        super(type, source);
        this.value = value;
    }

    public PlayerEvent(PlayerEventTypes type) {
        this(type, null, null);
    }

    public Object getValue() {
        return value;
    }

    @Override
    protected EventTypes<PlayerEventHandler, PlayerEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(PlayerEventHandler handler) {
        handler.onPlayerEvent(this);
    }

    public static EventType<PlayerEventHandler, PlayerEventTypes> getType(PlayerEventTypes type) {
        return types.getType(type);
    }

    public static EventType<PlayerEventHandler, PlayerEventTypes>[] getTypes(PlayerEventTypes... typeList) {
        return types.getTypes(typeList);
    }

}
