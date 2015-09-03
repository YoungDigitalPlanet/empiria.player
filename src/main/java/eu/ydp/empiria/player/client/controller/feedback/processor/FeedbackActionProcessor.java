package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

import java.util.List;

public interface FeedbackActionProcessor {

    /**
     * Implementation should process actions, and return list of processed actions.
     *
     * @param actions actions to process
     * @return processed actions
     */
    List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket, FeedbackMark mark);

    boolean canProcessAction(FeedbackAction action);

    void processSingleAction(FeedbackAction action, FeedbackMark mark);

}
