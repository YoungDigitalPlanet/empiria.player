package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;

public class WorkModeUpdaterForAssessment {

	@Inject
	private PlayerWorkModeService playerWorkModeService;

	public void update(String mode) {
		Optional<PlayerWorkMode> modeOptional = Enums.getIfPresent(PlayerWorkMode.class, mode.toUpperCase());
		if (modeOptional.isPresent()) {
			playerWorkModeService.updateWorkMode(modeOptional.get());
		}
	}

}
