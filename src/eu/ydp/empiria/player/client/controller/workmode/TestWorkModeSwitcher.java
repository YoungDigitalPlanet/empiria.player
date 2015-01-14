package eu.ydp.empiria.player.client.controller.workmode;


public class TestWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(WorkModeClientType module) {
		if (module instanceof WorkModeTestClient) {
			((WorkModeTestClient) module).enableTestMode();
		}
	}

	@Override
	public void disable(WorkModeClientType module) {
		if (module instanceof WorkModeTestClient) {
			((WorkModeTestClient) module).disableTestMode();
		}
	}
}
