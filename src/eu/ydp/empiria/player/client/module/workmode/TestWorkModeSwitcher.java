package eu.ydp.empiria.player.client.module.workmode;

import eu.ydp.empiria.player.client.module.IModule;

public class TestWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(IModule module) {
		if (module instanceof WorkModeTestClient) {
			((WorkModeTestClient) module).enableTestMode();
		}
	}

	@Override
	public void disable(IModule module) {
		if (module instanceof WorkModeTestClient) {
			((WorkModeTestClient) module).disableTestMode();
		}
	}
}
