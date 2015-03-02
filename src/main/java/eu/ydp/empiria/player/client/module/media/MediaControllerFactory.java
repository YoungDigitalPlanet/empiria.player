package eu.ydp.empiria.player.client.module.media;

import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.button.MediaController;

public interface MediaControllerFactory {
	public MediaController<?> get(ModuleTagName moduleType);

	public MediaController<?> get(ModuleTagName moduleType, Object... params);

}
