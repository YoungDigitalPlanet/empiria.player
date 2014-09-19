package eu.ydp.empiria.player.client.module.workmode;

import eu.ydp.empiria.player.client.module.IModule;

import java.io.Serializable;

public interface WorkModeSwitcher extends Serializable {
	void enable(IModule module);
}
