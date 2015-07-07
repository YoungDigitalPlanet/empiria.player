package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

@JsType
public class ExternalSoundInstance {

    private final MediaWrapperController mediaWrapperController;
    private final MediaWrapper<Widget> audioWrapper;

    @Inject
    public ExternalSoundInstance(@Assisted MediaWrapper<Widget> audioWrapper, @Assisted final Optional<OnEndCallback> onEndCallback,
                                 MediaWrapperController mediaWrapperController) {
        this.audioWrapper = audioWrapper;
        this.mediaWrapperController = mediaWrapperController;
        registerOnEndCallback(audioWrapper, onEndCallback);
    }

    private void registerOnEndCallback(MediaWrapper<Widget> audioWrapper, final Optional<OnEndCallback> onEndCallback) {
        if (onEndCallback.isPresent()) {
            mediaWrapperController.addHandler(MediaEventTypes.ON_END, audioWrapper, new MediaEventHandler() {
                @Override
                public void onMediaEvent(MediaEvent event) {
                    onEndCallback.get().onEnd();
                }
            });
        }
    }

    public void play() {
        mediaWrapperController.stopAndPlay(audioWrapper);
    }

    public void playLooped() {
        mediaWrapperController.playLooped(audioWrapper);
    }

    public void stop() {
        mediaWrapperController.stop(audioWrapper);
    }

    public void pause() {
        mediaWrapperController.pause(audioWrapper);
    }

    public void resume() {
        mediaWrapperController.resume(audioWrapper);
    }
}
