package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClient;

import java.util.EnumSet;

public enum PlayerWorkMode {
	FULL {
		@Override
		EnumSet<PlayerWorkMode> getAvailableTransitions() {
			return EnumSet.allOf(PlayerWorkMode.class);
		}
	},
	PREVIEW {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enablePreviewMode();
		}

		@Override
		EnumSet<PlayerWorkMode> getAvailableTransitions() {
			return EnumSet.noneOf(PlayerWorkMode.class);
		}
	},
	TEST {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enableTestMode();
		}

		@Override
		EnumSet<PlayerWorkMode> getAvailableTransitions() {
			return EnumSet.of(PREVIEW, TEST_SUBMITTED);
		}
	},
	TEST_SUBMITTED {
		@Override
		public void changeWorkMode(WorkModeClient workModeClient) {
			workModeClient.enableTestSubmittedMode();
		}

		@Override
		EnumSet<PlayerWorkMode> getAvailableTransitions() {
			return EnumSet.of(PREVIEW, TEST);
		}
	};

	public void changeWorkMode(WorkModeClient workModeClient) {
	}

	public boolean canChangeModeTo(PlayerWorkMode newWorkMode) {
		return getAvailableTransitions().contains(newWorkMode);
	}

	abstract EnumSet<PlayerWorkMode> getAvailableTransitions();
}
