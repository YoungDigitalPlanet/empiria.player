package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.*;

import com.google.common.base.Optional;
import com.google.inject.Inject;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = FULL;
	private Optional<PlayerWorkMode> previousWorkMode = Optional.absent();

	@Inject
	private WorkModeOnChangeListener workModeOnChangeListener;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void tryToUpdateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			previousWorkMode = Optional.of(currentWorkMode);
			currentWorkMode = newWorkMode;
			workModeOnChangeListener.onWorkModeChange(previousWorkMode, currentWorkMode);
		}
	}

	public Optional<PlayerWorkMode> getPreviousWorkMode() {
		return previousWorkMode;
	}
}
