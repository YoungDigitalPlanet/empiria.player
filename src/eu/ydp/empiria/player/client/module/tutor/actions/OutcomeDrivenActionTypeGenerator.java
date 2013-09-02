package eu.ydp.empiria.player.client.module.tutor.actions;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class OutcomeDrivenActionTypeGenerator {

	private TutorConfig tutorConfig;

	private OutcomeDrivenActionTypeProvider actionTypeProvider;

	@Inject
	public OutcomeDrivenActionTypeGenerator(@ModuleScoped TutorConfig tutorConfig, @ModuleScoped OutcomeDrivenActionTypeProvider actionTypeProvider) {
		this.tutorConfig = tutorConfig;
		this.actionTypeProvider = actionTypeProvider;
	}

	private final Predicate<OutcomeDrivenAction> actionPredicate = new Predicate<OutcomeDrivenAction>() {

		@Override
		public boolean apply(OutcomeDrivenAction action) {
			ActionType actionType = action.getActionType();
			return tutorConfig.supportsAction(actionType) && action.actionOccured();
		}
	};

	public Optional<ActionType> findActionType() {
		Set<OutcomeDrivenAction> actions = actionTypeProvider.getActions();

		Optional<OutcomeDrivenAction> action = Iterables.tryFind(actions, actionPredicate);
		if (action.isPresent()) {
			ActionType actionType = action.get().getActionType();
			return Optional.of(actionType);
		}

		return Optional.absent();
	}

}
