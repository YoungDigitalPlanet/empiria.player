package eu.ydp.empiria.player.client.controller.workmode;


public class TestSubmittedWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(WorkModeClientType module) {
		if (module instanceof WorkModeTestSubmittedClient) {
			((WorkModeTestSubmittedClient) module).enableTestSubmittedMode();
		}
	}

	@Override
	public void disable(WorkModeClientType module) {
		if (module instanceof WorkModeTestSubmittedClient) {
			((WorkModeTestSubmittedClient) module).disableTestSubmittedMode();
		}
	}
}
