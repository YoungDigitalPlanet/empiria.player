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

package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public abstract class AbstractNoControlExecutor implements MediaExecutor<Widget> {

    protected MediaWrapper<Widget> mediaWrapper;
    protected BaseMediaConfiguration bmc;

    @Override
    public MediaWrapper<Widget> getMediaWrapper() {
        return mediaWrapper;
    }

    @Override
    public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
        this.mediaWrapper = descriptor;

    }

    @Override
    public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {// NOPMD
        this.bmc = baseMediaConfiguration;
    }

    @Override
    public BaseMediaConfiguration getBaseMediaConfiguration() {
        return bmc;
    }

    @Override
    @Deprecated
    public void play(String src) {// NOPMD
    }

    @Override
    public void play() {// NOPMD
    }

    @Override
    public void playLooped() {
    }

    @Override
    public void stop() {// NOPMD
    }

    @Override
    public void pause() {// NOPMD
    }

    @Override
    public void resume() {// NOPMD
    }

    @Override
    public void setMuted(boolean mute) {// NOPMD
    }

    @Override
    public void setVolume(double volume) {// NOPMD
    }

    @Override
    public void setCurrentTime(double time) {// NOPMD
    }

    @Override
    public void setSoundFinishedListener(SoundExecutorListener listener) {// NOPMD
    }

}
