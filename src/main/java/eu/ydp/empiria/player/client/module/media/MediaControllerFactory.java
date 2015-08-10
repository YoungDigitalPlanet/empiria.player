package eu.ydp.empiria.player.client.module.media;

import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.button.MediaController;

public interface MediaControllerFactory {
    MediaController get(ModuleTagName moduleType);

    MediaController get(ModuleTagName moduleType, Object... params);

}
