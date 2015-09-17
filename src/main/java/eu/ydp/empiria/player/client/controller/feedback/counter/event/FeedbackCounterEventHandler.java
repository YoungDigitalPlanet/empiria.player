package eu.ydp.empiria.player.client.controller.feedback.counter.event;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface FeedbackCounterEventHandler extends EventHandler {

    void onFeedbackCounterEvent(FeedbackCounterEvent event);
}
