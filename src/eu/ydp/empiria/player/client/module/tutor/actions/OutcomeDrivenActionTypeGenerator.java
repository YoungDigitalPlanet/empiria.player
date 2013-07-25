package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class OutcomeDrivenActionTypeGenerator {

	@Inject @ModuleScoped private OnPageAllOkAction pageAllOk;
	@Inject @ModuleScoped private TutorConfig tutorConfig;

	public Optional<ActionType> findActionType() {
		ActionType actionType = pageAllOk.getActionType();
		if (tutorConfig.supportsAction(actionType) && pageAllOk.actionOccured()) {
			return Optional.of(actionType);
		}
		return Optional.absent();
	}

}
