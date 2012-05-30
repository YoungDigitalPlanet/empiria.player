package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.HashMap;
import java.util.Map;

public class FeedbackInteractionMuteEvent extends FeedbackInteractionEvent {

	boolean mute;
	
	public FeedbackInteractionMuteEvent(boolean mute){
		this.mute = mute;
	}
	
	@Override
	public InteractionEventType getType() {
		return InteractionEventType.FEEDBACK_MUTE;
	}

	@Override
	public Map<String, Object> getParams() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("mute", new Boolean(mute));
		return p;
	}

}
