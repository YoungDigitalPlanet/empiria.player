package eu.ydp.empiria.player.client.controller.workmode;

import java.io.Serializable;

public interface WorkModeSwitcher extends Serializable {
	void enable(WorkModeClientType module);

	void disable(WorkModeClientType module);
}
