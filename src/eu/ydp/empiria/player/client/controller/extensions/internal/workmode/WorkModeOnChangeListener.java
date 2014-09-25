package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import com.google.common.base.Optional;

public interface WorkModeOnChangeListener {
	public void onWorkModeChange(Optional<PlayerWorkMode> previousWorkMode, PlayerWorkMode currentWorkMode);
}
