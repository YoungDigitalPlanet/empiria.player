package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.HashMap;
import java.util.Map;

public class FeedbackInteractionMuteEvent extends FeedbackInteractionEvent {

	protected boolean mute;

	public FeedbackInteractionMuteEvent(boolean mute){
		this.mute = mute;
	}

	@Override
	public InteractionEventType getType() {
		return InteractionEventType.FEEDBACK_MUTE;
	}

	@Override
	public Map<String, Object> getParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mute", Boolean.valueOf(mute));
		return params;
	}

}
