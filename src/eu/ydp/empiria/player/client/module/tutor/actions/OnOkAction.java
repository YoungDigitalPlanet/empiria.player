package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class OnOkAction implements OutcomeDrivenAction {

	@Inject
	private OutcomeAccessor outcomeAccessor;

	@Override
	public boolean actionOccured() {
		boolean selection = outcomeAccessor.isLastActionSelection();
		boolean lastMistaken = outcomeAccessor.isCurrentPageLastMistaken();
		return selection && !lastMistaken;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.ON_OK;
	}

}
