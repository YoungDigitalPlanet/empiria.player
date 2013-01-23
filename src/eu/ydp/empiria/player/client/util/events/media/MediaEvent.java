package eu.ydp.empiria.player.client.util.events.media;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class MediaEvent extends AbstractEvent<MediaEventHandler, MediaEventTypes> {
	public static EventTypes<MediaEventHandler, MediaEventTypes> types = new EventTypes<MediaEventHandler, MediaEventTypes>();

	private Double currentTime;

	private TextTrackCue textTrackCue;

	private double volume;

	public MediaEvent(MediaEventTypes type) {
		this(type, null);
	}

	public MediaEvent(MediaEventTypes type, Object source) {
		super(type, source);
	}

	public MediaWrapper<?> getMediaWrapper() {
		return (super.getSource() instanceof MediaWrapper<?>) ? (MediaWrapper<?>) super.getSource() : null;// NOPMD
	}

	public void setCurrentTime(Double position) {
		this.currentTime = position;
	}

	public Double getCurrentTime() {
		return currentTime;
	}

	public void setTextTrackCue(TextTrackCue textTrackCue) {
		this.textTrackCue = textTrackCue;
	}

	public TextTrackCue getTextTrackCue() {
		return textTrackCue;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double value) {
		this.volume = value;
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

	public static Type<MediaEventHandler, MediaEventTypes>[] getTypes(MediaEventTypes... type) {
		return types.getTypes(type);
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MediaEvent [type=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}

}
