package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

import java.util.List;

public abstract class AbstractFeedbackActionProcessor implements FeedbackActionProcessor {

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        List<FeedbackAction> processedActions = Lists.newArrayList();

        clearFeedback();

        for (FeedbackAction action : actions) {
            if (canProcessAction(action)) {
                processSingleAction(action);
                processedActions.add(action);
            }
        }

        return processedActions;
    }

    protected abstract boolean canProcessAction(FeedbackAction action);

    protected abstract void processSingleAction(FeedbackAction action);

    protected abstract void clearFeedback();

}
