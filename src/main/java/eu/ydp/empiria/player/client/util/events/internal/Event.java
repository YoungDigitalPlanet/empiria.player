package eu.ydp.empiria.player.client.util.events.internal;

public interface Event<H, T extends Enum<T>> {
    EventType<H, T> getAssociatedType();

    Object getSource();

    void dispatch(H handler);
}
