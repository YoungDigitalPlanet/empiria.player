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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaExecutor<T extends Widget> {
    MediaWrapper<T> getMediaWrapper();

    void setMediaWrapper(MediaWrapper<T> descriptor);

    void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration);

    BaseMediaConfiguration getBaseMediaConfiguration();

    void init();

    @Deprecated
    void play(String src);

    void play();

    void playLooped();

    void stop();

    void pause();

    void resume();

    void setMuted(boolean mute);

    void setVolume(double volume);

    void setCurrentTime(double time);

    void setSoundFinishedListener(SoundExecutorListener listener);
}
