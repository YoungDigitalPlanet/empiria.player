package eu.ydp.empiria.player.client.gin.factory;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.external.common.sound.ExternalSoundInstance;
import eu.ydp.empiria.player.client.module.external.common.sound.OnEndCallback;
import eu.ydp.empiria.player.client.module.external.common.sound.OnPauseCallback;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface ExternalInteractionModuleFactory {
	ExternalSoundInstance getExternalSoundInstance(MediaWrapper<Widget> audioWrapper, Optional<OnEndCallback> onEndCallback, Optional<OnPauseCallback> onPauseCallback);
}
