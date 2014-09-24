package eu.ydp.empiria.player.client.module.report;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;

public class WorkModeSwitcherForReport {

	@Inject
	private PlayerWorkModeService playerWorkModeService;

	public void switchIfNeed() {
		if (playerWorkModeService.getCurrentWorkMode()
		                         .equals(PlayerWorkMode.TEST)) {
			playerWorkModeService.updateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
		}
	}
}
