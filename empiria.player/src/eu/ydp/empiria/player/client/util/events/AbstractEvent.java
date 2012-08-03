package eu.ydp.empiria.player.client.util.events;

public abstract class AbstractEvent<H, E extends Enum<E>> extends Event<H, E> {
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
	public eu.ydp.empiria.player.client.util.events.Event.Type<H, E> getAssociatedType() {
		return getTypes().getType(type);
	}
}
