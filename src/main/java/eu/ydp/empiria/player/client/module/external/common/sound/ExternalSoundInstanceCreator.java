package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalSoundInstanceCreator {

    private ExternalPaths paths;
    private MediaWrapperCreator mediaWrapperCreator;
    private ExternalInteractionModuleFactory moduleFactory;

    @Inject
    public ExternalSoundInstanceCreator(@ModuleScoped ExternalPaths paths, MediaWrapperCreator mediaWrapperCreator,
                                        ExternalInteractionModuleFactory moduleFactory) {
        this.paths = paths;
        this.mediaWrapperCreator = mediaWrapperCreator;
        this.moduleFactory = moduleFactory;
    }


    public void createSound(final String audioName, final ExternalSoundInstanceCallback callback, Optional<OnEndCallback> onEndCallback) {
        String audioPath = paths.getExternalFilePath(audioName);
        mediaWrapperCreator.createMediaWrapper(audioPath, createCallbackReceiver(audioName, callback, onEndCallback));
    }

    private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audioName, final ExternalSoundInstanceCallback callback, final Optional<OnEndCallback> onEndCallback) {
        return new CallbackReceiver<MediaWrapper<Widget>>() {
            @Override
            public void setCallbackReturnObject(MediaWrapper<Widget> audioWrapper) {
                ExternalSoundInstance soundInstance = moduleFactory.getExternalSoundInstance(audioWrapper, onEndCallback);
                callback.onSoundCreated(soundInstance, audioName);
            }
        };
    }
}
