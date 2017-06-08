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

package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.core.creator.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.FeedbackAudioMuteButtonModule;

public class AudioMuteButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

    @Inject
    private Provider<FeedbackAudioMuteButtonModule> provider;

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator() {

            @Override
            public IModule createModule() {
                FeedbackAudioMuteButtonModule button = provider.get();
                initializeModule(button);
                return button;
            }
        };
    }

    @Override
    public String getModuleNodeName() {
        return ModuleTagName.AUDIO_MUTE_BUTTON.tagName();
    }

}
