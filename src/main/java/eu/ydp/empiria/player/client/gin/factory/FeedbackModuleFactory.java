package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.ModuleFeedbackProcessor;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounter;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterCleaner;

public interface FeedbackModuleFactory {
    ModuleFeedbackProcessor getModuleFeedbackProcessor(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

//    <T> FeedbackCounter<T> getFeedbackCounter(FeedbackCounterCleaner<T> counterRemovable);
}
