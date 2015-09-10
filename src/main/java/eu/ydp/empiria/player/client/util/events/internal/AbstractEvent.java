package eu.ydp.empiria.player.client.util.events.internal;

import eu.ydp.gwtutil.client.event.Event;
import eu.ydp.gwtutil.client.event.EventType;

public abstract class AbstractEvent<H, E extends Enum<E>> implements Event<H, E> {
    private final Object source;

    private final E type;

    public AbstractEvent(E type, Object source) {
        this.type = type;
        this.source = source;
    }

    public E getType() {
        return type;
    }

    @Override
    public EventType<H, E> getAssociatedType() {
        return getTypes().getType(type);
    }

    @Override
    public Object getSource() {
        return source;
    }

    protected abstract EventTypes<H, E> getTypes();
}
