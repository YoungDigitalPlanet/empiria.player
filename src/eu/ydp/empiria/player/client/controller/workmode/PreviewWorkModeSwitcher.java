package eu.ydp.empiria.player.client.controller.workmode;


public class PreviewWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(WorkModeClientType module) {
		if (module instanceof WorkModePreviewClient) {
			((WorkModePreviewClient) module).enablePreviewMode();
		}
	}

	@Override
	public void disable(WorkModeClientType module) {
	}
}
