package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.FULL;

import com.google.inject.Inject;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = FULL;
	private PlayerWorkMode previousWorkMode = FULL;

	@Inject
	private PlayerWorkModeNotifier playerWorkModeNotifier;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void tryToUpdateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			previousWorkMode = currentWorkMode;
			currentWorkMode = newWorkMode;
			playerWorkModeNotifier.onWorkModeChange(previousWorkMode, currentWorkMode);
		}
	}

	public PlayerWorkMode getPreviousWorkMode() {
		return previousWorkMode;
	}
}
