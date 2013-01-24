package eu.ydp.empiria.player.client.util.events.feedback;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface FeedbackEventHandler extends EventHandler {
	
	void onFeedbackEvent(FeedbackEvent event);
	
}
