package eu.ydp.empiria.player.client.util.events.media;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class MediaEvent extends AbstractEvent<MediaEventHandler, MediaEventTypes> {
	public static EventTypes<MediaEventHandler, MediaEventTypes> types = new EventTypes<MediaEventHandler, MediaEventTypes>();

	private final Object value;

	public MediaEvent(MediaEventTypes type) {
		this(type, null, 0);
	}

	public MediaEvent(MediaEventTypes type, Object source) {
		this(type, source, 0);
	}

	public MediaEvent(MediaEventTypes type, Object source, Object value) {
		super(type, source);
		this.value = value;

	}

	public MediaWrapper<?> getMediaWrapper() {
		return (super.getSource() instanceof MediaWrapper<?>) ? (MediaWrapper<?>) super.getSource() : null;// NOPMD
	}

	public Object getValue() {
		return value;
	}

	@Override
	protected EventTypes<MediaEventHandler, MediaEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(MediaEventHandler handler) {
		handler.onMediaEvent(this);
	}

	public static Type<MediaEventHandler, MediaEventTypes> getType(MediaEventTypes type) {
		return types.getType(type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MediaEvent [type=");
		builder.append(getType());
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
