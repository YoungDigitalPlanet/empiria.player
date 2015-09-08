package eu.ydp.empiria.player.client.controller.feedback.counter.event;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface FeedbackCounterEventHandler extends EventHandler {

    void onFeedbackCounterEvent(FeedbackCounterEvent event);
}
