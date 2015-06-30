package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

public class ActionProcessorHelper extends AbstractFeedbackActionProcessor {

    private final ActionProcessorTarget target;

    public ActionProcessorHelper(ActionProcessorTarget target) {
        this.target = target;
    }

    @Override
    protected boolean canProcessAction(FeedbackAction action) {
        return target.canProcessAction(action);
    }

    @Override
    protected void processSingleAction(FeedbackAction action) {
        target.processSingleAction(action);
    }

    @Override
    protected void clearFeedback() {
        target.clearFeedback();
    }

}
