package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClientType;

import java.util.ArrayList;
import java.util.List;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.FULL;

public class PlayerWorkModeService {

	private final List<WorkModeClientType> moduleList = new ArrayList<>();
	private PlayerWorkMode currentWorkMode = FULL;
	private PlayerWorkMode previousWorkMode = FULL;

	public void registerModule(WorkModeClientType module) {
		moduleList.add(module);
		notifyModule(module);
	}

	public void tryToUpdateWorkMode(PlayerWorkMode newWorkMode) {
		if (currentWorkMode.canChangeModeTo(newWorkMode)) {
			previousWorkMode = currentWorkMode;
			currentWorkMode = newWorkMode;
			notifyModules();
		}
	}

	private void notifyModules() {
		for (WorkModeClientType module : moduleList) {
			notifyModule(module);
		}
	}

	private void notifyModule(WorkModeClientType module) {
		disablePreviousModule(module);
		enableCurrentModule(module);
	}

	private void disablePreviousModule(WorkModeClientType module) {
		previousWorkMode.getWorkModeSwitcher()
						.disable(module);
	}

	private void enableCurrentModule(WorkModeClientType module) {
		currentWorkMode.getWorkModeSwitcher()
					   .enable(module);
	}
}
