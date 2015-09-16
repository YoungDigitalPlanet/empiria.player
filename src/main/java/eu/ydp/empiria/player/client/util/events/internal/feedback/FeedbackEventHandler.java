package eu.ydp.empiria.player.client.util.events.internal.feedback;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface FeedbackEventHandler extends EventHandler {

    void onFeedbackEvent(FeedbackEvent event);

}
