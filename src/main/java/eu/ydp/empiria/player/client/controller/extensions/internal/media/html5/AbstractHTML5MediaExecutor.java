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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5OnMediaEventHandler;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.html5.HTML5MediaEventsType;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstractHTML5MediaExecutor<H extends MediaBase> implements MediaExecutor<MediaBase>, HTML5OnMediaEventHandler {

    private H media;
    private MediaWrapper<MediaBase> mediaDescriptor;
    private SoundExecutorListener listener;
    private BaseMediaConfiguration baseMediaConfiguration;

    protected HTML5MediaEventMapper mediaEventMapper;
    protected final HTML5MediaNativeListeners html5MediaNativeListeners;

    public AbstractHTML5MediaExecutor(HTML5MediaEventMapper mediaEventMapper, HTML5MediaNativeListeners html5MediaNativeListeners) {
        this.mediaEventMapper = mediaEventMapper;
        this.html5MediaNativeListeners = html5MediaNativeListeners;
        this.html5MediaNativeListeners.setCallbackListener(this);
    }

    @Override
    public void init() {
        if (media != null) {
            Element audioElement = mediaDescriptor.getMediaObject().getElement();
            configureMediaBase();
            initExecutor();
            setNativeListeners(audioElement);
        }
    }

    private void setNativeListeners(Element audioElement) {
        ArrayList<HTML5MediaEventsType> eventTypes = Lists.newArrayList(HTML5MediaEventsType.canplay, HTML5MediaEventsType.suspend, HTML5MediaEventsType.ended,
                HTML5MediaEventsType.error, HTML5MediaEventsType.pause, HTML5MediaEventsType.play, HTML5MediaEventsType.volumechange,
                HTML5MediaEventsType.timeupdate, HTML5MediaEventsType.durationchange);

        for (HTML5MediaEventsType eventType : eventTypes) {
            html5MediaNativeListeners.addListener(audioElement, eventType.toString());
        }
    }

    @Override
    public void onHtml5MediaEvent(HTML5MediaEventsType eventType) {
        mediaEventMapper.mapAndFireEvent(eventType, listener, mediaDescriptor);
    }

    protected String getMediaPreloadType() {
        return MediaElement.PRELOAD_METADATA;
    }

    private void configureMediaBase() {
        for (Map.Entry<String, String> entry : baseMediaConfiguration.getSources().entrySet()) {
            media.addSource(entry.getKey(), entry.getValue());
        }
        media.setPreload(getMediaPreloadType());
        media.setControls(!baseMediaConfiguration.isTemplate());
    }

    @Override
    public void setMediaWrapper(MediaWrapper<MediaBase> descriptor) {
        this.mediaDescriptor = descriptor;
        media = (H) descriptor.getMediaObject();
        if (mediaDescriptor instanceof AbstractHTML5MediaWrapper) {
            ((AbstractHTML5MediaWrapper) mediaDescriptor).setMediaExecutor(this);
        }
    }

    @Override
    public MediaWrapper<MediaBase> getMediaWrapper() {
        return mediaDescriptor;
    }

    protected H getMedia() {
        return media;
    }

    @Override
    public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {// NOPMD
        this.baseMediaConfiguration = baseMediaConfiguration;
    }

    @Override
    public BaseMediaConfiguration getBaseMediaConfiguration() {
        return baseMediaConfiguration;
    }

    @Override
    public void play(String src) {
        media.play();
    }

    public void setMedia(H media) {
        this.media = media;
    }

    @Override
    public void play() {
        if (UserAgentChecker.isMobileUserAgent(UserAgentChecker.MobileUserAgent.SAFARI)) {
            media.setSrc(baseMediaConfiguration.getSources().keySet().iterator().next());
        }
        media.setLoop(false);
        media.play();
    }

    @Override
    public void playLooped() {
        media.setLoop(true);
        media.play();
    }

    @Override
    public void stop() {
        setCurrentTime(0);
        media.pause();
    }

    @Override
    public void pause() {
        stopOnTime(media.getCurrentTime());
    }

    @Override
    public void resume() {
        media.play();
    }

    private void stopOnTime(double time) {
        try {
            media.pause();
            media.setCurrentTime(time);
        } catch (Exception e) {// NOPMD
            // chrome podczas przeladowania strony lekcji
            // generowal bledy
        }
    }

    @Override
    public void setMuted(boolean muted) {
        media.setMuted(muted);

    }

    @Override
    public void setVolume(double volume) {
        media.setVolume(volume);
    }

    @Override
    public void setCurrentTime(double time) {
        if (!Double.isNaN(media.getDuration())) {
            media.setCurrentTime(time);
        }
    }

    @Override
    public void setSoundFinishedListener(SoundExecutorListener listener) {
        this.listener = listener;
    }

    public abstract void initExecutor();

}
