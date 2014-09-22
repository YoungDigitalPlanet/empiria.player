package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import com.google.common.base.Optional;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.FULL;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = FULL;
	private Optional<PlayerWorkMode> previousWorkMode = Optional.absent();

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void updateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			previousWorkMode = Optional.of(currentWorkMode);
			currentWorkMode = newWorkMode;
		}
	}

	public Optional<PlayerWorkMode> getPreviousWorkMode() {
		return previousWorkMode;
	}
}
