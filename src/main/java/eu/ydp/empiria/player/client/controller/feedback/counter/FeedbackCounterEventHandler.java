package eu.ydp.empiria.player.client.controller.feedback.counter;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface FeedbackCounterEventHandler extends EventHandler {// <EventScope<?>> {

    void onFeedbackCounterEvent(FeedbackCounterEvent event);
}
