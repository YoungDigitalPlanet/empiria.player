package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.FULL;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = FULL;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void updateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			currentWorkMode = newWorkMode;
		}
	}
}
