package eu.ydp.empiria.player.client.util.events.internal.connection;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;

public class ConnectionEvent extends AbstractEvent<ConnectionEventHandler, ConnectionEventTypes> {
    public static EventTypes<ConnectionEventHandler, ConnectionEventTypes> types = new EventTypes<ConnectionEventHandler, ConnectionEventTypes>();
    private String sourceItem;
    private String targetItem;

    public ConnectionEvent(ConnectionEventTypes type, String source, String target) {
        super(type, null);
        this.sourceItem = source;
        this.targetItem = target;
    }

    public String getSourceItem() {
        return sourceItem;
    }

    public String getTargetItem() {
        return targetItem;
    }

    public ConnectionEvent(ConnectionEventTypes type) {
        super(type, null);
    }

    @Override
    protected EventTypes<ConnectionEventHandler, ConnectionEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(ConnectionEventHandler handler) {
        handler.onConnectionEvent(this);
    }

    public static Type<ConnectionEventHandler, ConnectionEventTypes> getType(ConnectionEventTypes type) {
        return types.getType(type);
    }

}
