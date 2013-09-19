package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class OnPageAllOkAction implements OutcomeDrivenAction {

	@Inject
	private OutcomeAccessor outcomeAccessor;
	
	@Override
	public boolean actionOccured() {
		return outcomeAccessor.isPageAllOk();
	}

	@Override
	public ActionType getActionType() {
		return ActionType.ON_PAGE_ALL_OK;
	}

}
