package eu.ydp.empiria.player.client.util.events.internal;

import eu.ydp.gwtutil.client.event.EventImpl;

public abstract class AbstractEvent<H, E extends Enum<E>> extends EventImpl<H, E> {
    protected abstract EventTypes<H, E> getTypes();

    private E type = null; // NOPMD

    public AbstractEvent(E type, Object source) {
        this.type = type;
        setSource(source);
    }

    public E getType() {
        return type;
    }

    @Override
    public EventImpl.Type<H, E> getAssociatedType() {
        return getTypes().getType(type);
    }
}
