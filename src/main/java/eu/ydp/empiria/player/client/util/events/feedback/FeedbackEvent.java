package eu.ydp.empiria.player.client.util.events.feedback;

import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class FeedbackEvent extends AbstractEvent<FeedbackEventHandler, FeedbackEventTypes> {

	private static final EventTypes<FeedbackEventHandler, FeedbackEventTypes> types = new EventTypes<FeedbackEventHandler, FeedbackEventTypes>();

	private boolean isMuted;

	public FeedbackEvent(FeedbackEventTypes type, Object source) {
		super(type, source);
	}

	public FeedbackEvent(FeedbackEventTypes type, boolean isMuted, Object source) {
		super(type, source);
		this.isMuted = isMuted;
	}

	@Override
	protected EventTypes<FeedbackEventHandler, FeedbackEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(FeedbackEventHandler handler) {
		handler.onFeedbackEvent(this);
	}

	public static Type<FeedbackEventHandler, FeedbackEventTypes> getType(FeedbackEventTypes type) {
		return types.getType(type);
	}

	public boolean isMuted() {
		return isMuted;
	}

}
