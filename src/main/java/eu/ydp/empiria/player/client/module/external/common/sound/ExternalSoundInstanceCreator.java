/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

    public void createSound(final String audioName, final ExternalSoundInstanceCallback callback, Optional<OnEndCallback> onEndCallback, Optional<OnPauseCallback> onPauseCallback) {
        String audioPath = paths.getExternalFilePath(audioName);
        mediaWrapperCreator.createExternalMediaWrapper(audioPath, createCallbackReceiver(audioName, callback, onEndCallback, onPauseCallback));
    }

    private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audioName, final ExternalSoundInstanceCallback callback, final Optional<OnEndCallback> onEndCallback, final Optional<OnPauseCallback> onPauseCallback) {
        return new CallbackReceiver<MediaWrapper<Widget>>() {
            @Override
            public void setCallbackReturnObject(MediaWrapper<Widget> audioWrapper) {
                ExternalSoundInstance soundInstance = moduleFactory.getExternalSoundInstance(audioWrapper, onEndCallback, onPauseCallback);
                callback.onSoundCreated(soundInstance, audioName);
            }
        };
    }
}
