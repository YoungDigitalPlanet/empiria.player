package eu.ydp.empiria.player.client.util.events.feedback;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface FeedbackEventHandler extends EventHandler {

	void onFeedbackEvent(FeedbackEvent event);

}