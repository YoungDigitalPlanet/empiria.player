package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

public class PlayerWorkModeService {

	private PlayerWorkMode currentWorkMode = PlayerWorkMode.FULL;

	public PlayerWorkMode getCurrentWorkMode() {
		return currentWorkMode;
	}

	public void setCurrentWorkMode(PlayerWorkMode currentWorkMode) {
		this.currentWorkMode = currentWorkMode;
	}
}
