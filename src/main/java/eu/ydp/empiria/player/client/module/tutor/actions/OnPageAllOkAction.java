package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class OnPageAllOkAction implements OutcomeDrivenAction {

    @Inject
    private FeedbackActionConditions actionConditions;

    @Override
    public boolean actionOccured() {
        return actionConditions.isPageAllOk();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.ON_PAGE_ALL_OK;
    }

}
