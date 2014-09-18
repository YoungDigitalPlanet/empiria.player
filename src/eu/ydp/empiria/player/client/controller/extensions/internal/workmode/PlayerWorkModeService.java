package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = PlayerWorkMode.FULL;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void updateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			currentWorkMode = newWorkMode;
		}
	}
}
