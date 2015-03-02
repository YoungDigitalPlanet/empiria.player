package eu.ydp.empiria.player.client.module.tutor.actions;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.WRONG;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class OnWrongAction implements OutcomeDrivenAction {

	@Inject
	private OutcomeAccessor outcomeAccessor;

	@Override
	public boolean actionOccured() {
		boolean selection = outcomeAccessor.isLastActionSelection();
		LastMistaken lastMistaken = outcomeAccessor.getCurrentPageLastMistaken();
		return selection && WRONG.equals(lastMistaken);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.ON_WRONG;
	}

}
