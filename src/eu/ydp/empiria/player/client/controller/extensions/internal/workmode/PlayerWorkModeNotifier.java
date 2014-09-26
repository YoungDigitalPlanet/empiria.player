package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;

import java.util.ArrayList;
import java.util.List;

public class PlayerWorkModeNotifier {

	private final List<WorkModeClientType> moduleList = new ArrayList<>();

	public void addModule(WorkModeClientType module) {
		moduleList.add(module);
	}

	public void onWorkModeChange(PlayerWorkMode previousWorkMode, PlayerWorkMode currentWorkMode) {
		WorkModeSwitcher previousWorkModeSwitcher = previousWorkMode.getWorkModeSwitcher();
		WorkModeSwitcher currentWorkModeSwitcher = currentWorkMode.getWorkModeSwitcher();

		for (WorkModeClientType module : moduleList) {
			previousWorkModeSwitcher.disable(module);
			currentWorkModeSwitcher.enable(module);
		}
	}

}
