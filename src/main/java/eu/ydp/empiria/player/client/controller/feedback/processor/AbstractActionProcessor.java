package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.Resetable;

import java.util.List;

public abstract class AbstractActionProcessor extends SimpleModuleBase implements FeedbackActionProcessor, Resetable {

    protected InlineBodyGeneratorSocket inlineBodyGenerator;

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGenerator, FeedbackMark mark) {
        this.inlineBodyGenerator = inlineBodyGenerator;

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

    public abstract void clearFeedback();

}
