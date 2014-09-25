package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;

public class PlayerWorkModeModuleContainer {

	private final List<WorkModeClientType> moduleList = new ArrayList<>();

	public void addModule(WorkModeClientType module) {
		moduleList.add(module);
	}

	public void onWorkModeChange(Optional<PlayerWorkMode> previousWorkMode, PlayerWorkMode currentWorkMode) {
		Optional<WorkModeSwitcher> previousWorkModeSwitcher = Optional.absent();
		if (previousWorkMode.isPresent()) {
			previousWorkModeSwitcher = Optional.of(previousWorkMode.get().getWorkModeSwitcher());
		}

		WorkModeSwitcher currentWorkModeSwitcher = currentWorkMode.getWorkModeSwitcher();

		for (WorkModeClientType module : moduleList) {
			if (previousWorkModeSwitcher.isPresent()) {
				previousWorkModeSwitcher.get().disable(module);
			}
			currentWorkModeSwitcher.enable(module);
		}
	}

}
