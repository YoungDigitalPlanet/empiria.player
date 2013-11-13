package eu.ydp.empiria.player.client.module.tutor;

import javax.inject.Inject;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SingleRunTutorEndHandler implements TutorEndHandler {
	private Optional<EndHandler> recentEndHandler = Optional.absent();

	@Inject
	@ModuleScoped
	private ActionExecutorService executorService;

	@Override
	public void onEnd(boolean shouldExecuteDefaultAction) {
		if (shouldExecuteDefaultAction) {
			executorService.execute(ActionType.DEFAULT, this);
		}

		if (recentEndHandler.isPresent()) {
			recentEndHandler.get().onEnd();
			recentEndHandler = Optional.absent();
		}
	}

	@Override
	public void setEndHandler(EndHandler recentEndHandler) {
		this.recentEndHandler = Optional.fromNullable(recentEndHandler);
	}
}