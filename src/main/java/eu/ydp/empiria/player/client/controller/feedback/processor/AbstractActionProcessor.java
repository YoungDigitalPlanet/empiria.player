package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.module.*;
import java.util.List;

public abstract class AbstractActionProcessor extends ParentedModuleBase implements FeedbackActionProcessor, FeedbackActionProcessorTarget, ISimpleModule, IResetable {

    protected InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket, FeedbackMark mark) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;

        clearFeedback();

        return processActions(actions, mark);
    }

    private List<FeedbackAction> processActions(List<FeedbackAction> actions, FeedbackMark mark) {
        List<FeedbackAction> processedActions = Lists.newArrayList();
        for (FeedbackAction action : actions) {
            if (canProcessAction(action)) {
                processSingleAction(action, mark);
                processedActions.add(action);
            }
        }

        return processedActions;
    }

    @Override
    public void reset() {
        clearFeedback();
    }
}
