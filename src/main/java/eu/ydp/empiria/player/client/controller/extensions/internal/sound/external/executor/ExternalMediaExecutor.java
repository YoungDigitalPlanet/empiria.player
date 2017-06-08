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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.executor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class ExternalMediaExecutor implements MediaExecutor<Widget> {

    @Inject
    private ExternalMediaEngine mediaEngine;

    private MediaWrapper<Widget> wrapper;
    private BaseMediaConfiguration baseMediaConfiguration;

    @Override
    public MediaWrapper<Widget> getMediaWrapper() {
        return wrapper;
    }

    @Override
    public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
        this.wrapper = descriptor;
    }

    @Override
    public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
        this.baseMediaConfiguration = baseMediaConfiguration;
    }

    @Override
    public BaseMediaConfiguration getBaseMediaConfiguration() {
        return baseMediaConfiguration;
    }

    @Override
    public void init() {
        mediaEngine.init(wrapper, baseMediaConfiguration.getSources().keySet());
    }

    @Override
    @Deprecated
    public void play(String src) {
        throw new UnsupportedOperationException("Operation is not supported. ExternalMediaExecutor.play(String src).");
    }

    @Override
    public void play() {
        mediaEngine.play(wrapper);
    }

    @Override
    public void playLooped() {
    }

    @Override
    public void stop() {
        mediaEngine.stop(wrapper);
    }

    @Override
    public void pause() {
        mediaEngine.pause(wrapper);
    }

    @Override
    public void resume() {
    }

    @Override
    public void setMuted(boolean mute) {
    }

    @Override
    public void setVolume(double volume) {
    }

    @Override
    public void setCurrentTime(double time) {
        mediaEngine.setCurrentTime(wrapper, time);
    }

    @Override
    public void setSoundFinishedListener(SoundExecutorListener listener) {
    }
}
