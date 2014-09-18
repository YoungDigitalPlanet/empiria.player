package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClient;

public enum PlayerWorkMode {
	FULL,
	PREVIEW {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enablePreviewMode();
		}
	}, TEST {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enableTestMode();
		}
	}, TEST_SUBMITTED {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enableTestSubmittedMode();
		}
	};

	public void changeWorkMode(WorkModeClient workModeClient) {
	}

}
