package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.FULL;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = FULL;
	private PlayerWorkMode previousWorkMode = FULL;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void tryToUpdateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			previousWorkMode = currentWorkMode;
			currentWorkMode = newWorkMode;
		}
	}

	public PlayerWorkMode getPreviousWorkMode() {
		return previousWorkMode;
	}
}
