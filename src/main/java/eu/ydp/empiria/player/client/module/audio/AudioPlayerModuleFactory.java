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

package eu.ydp.empiria.player.client.module.audio;

import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.DefaultAudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.FlashAudioPlayerModule;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.Map;

@Singleton
public class AudioPlayerModuleFactory {
    @Inject
    private Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider;
    @Inject
    private Provider<FlashAudioPlayerModule> flashAudioPlayerModuleProvider;
    @Inject
    private MediaChecker mediaChecker;

    @Inject
    public AudioPlayerModuleFactory (Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider,
                                     Provider<FlashAudioPlayerModule> flashAudioPlayerModuleProvider,
                                     MediaChecker mediaChecker){
        this.defaultAudioPlayerModuleProvider = defaultAudioPlayerModuleProvider;
        this.flashAudioPlayerModuleProvider = flashAudioPlayerModuleProvider;
        this.mediaChecker = mediaChecker;

    }

    public AudioPlayerModule getAudioPlayerModule(Map<String, String> sources){
        AudioPlayerModule player;
        if (doesPlayerNeedsFlash(sources)) {
            player = flashAudioPlayerModuleProvider.get();
        } else {
            player = defaultAudioPlayerModuleProvider.get();
        }
        return player;
    }
    private boolean doesPlayerNeedsFlash(Map<String, String> sources) {
        return ((!mediaChecker.isHtml5Mp3Supported() && !SourceUtil.containsOgg(sources)) || !Audio.isSupported()) && UserAgentChecker.isLocal();
    }
}
