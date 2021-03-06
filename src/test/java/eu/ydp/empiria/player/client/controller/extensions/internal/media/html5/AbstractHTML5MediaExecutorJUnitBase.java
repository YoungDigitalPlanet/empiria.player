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

import com.google.common.collect.ImmutableMap;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.Element;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class AbstractHTML5MediaExecutorJUnitBase {

    protected AbstractHTML5MediaExecutor<MediaBase> instance;

    @Mock
    protected HTML5MediaEventMapper mediaEventMapper;
    @Mock
    protected HTML5MediaNativeListeners html5MediaNativeListeners;

    @Mock
    protected MediaBase mediaBase;
    @Mock
    protected BaseMediaConfiguration mediaConfiguration;

    @Test
    public void shouldTestInitNoConfiguration() {
        // given
        Element element = mock(Element.class);
        MediaWrapper<MediaBase> mediaDescriptor = mock(MediaWrapper.class);
        when(mediaDescriptor.getMediaObject()).thenReturn(mediaBase);
        when(mediaBase.getElement()).thenReturn(element);

        instance.setMediaWrapper(mediaDescriptor);
        mediaConfiguration = new BaseMediaConfiguration(new HashMap<String, String>(), false);
        instance.setBaseMediaConfiguration(mediaConfiguration);

        // when
        instance.init();

        // then
        verify(mediaBase).setPreload(Matchers.eq(getAssumedMediaPreloadType()));
        verify(mediaBase).setControls(Matchers.eq(true));
        for (Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : createEventsPairMap().entrySet()) {
            verify(html5MediaNativeListeners).addListener(element, typePair.getKey().toString());
        }
    }

    @Test
    public void shouldInitiateValues_whenMediaBaseIsSet() {
        // given
        Element element = mock(Element.class);
        MediaWrapper<MediaBase> mediaDescriptor = mock(MediaWrapper.class);
        when(mediaDescriptor.getMediaObject()).thenReturn(mediaBase);
        when(mediaBase.getElement()).thenReturn(element);

        instance.setMediaWrapper(mediaDescriptor);
        instance.setBaseMediaConfiguration(mediaConfiguration);

        // when
        instance.init();

        // then
        verify(mediaBase).setPreload(Matchers.eq(getAssumedMediaPreloadType()));
        verify(mediaBase).setControls(Matchers.eq(false));
        for (Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : createEventsPairMap().entrySet()) {
            verify(html5MediaNativeListeners).addListener(element, typePair.getKey().toString());
        }
    }

    @Test
    public void shouldInitiateValues_whenMediaBaseIsNotSet() {
        // given
        instance.setMedia(null);

        // when
        instance.init();

        // then
        verifyZeroInteractions(mediaEventMapper);
        verify(html5MediaNativeListeners).setCallbackListener(instance);
        verifyNoMoreInteractions(html5MediaNativeListeners);
    }

    @Test
    public void shouldSetMediaWrapper() {
        // given
        instance.setMedia(null);
        MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
        doReturn(mediaBase).when(mediaWrapper).getMediaObject();

        // when
        instance.setMediaWrapper(mediaWrapper);

        // then
        assertEquals(mediaBase, instance.getMedia());
        assertEquals(mediaWrapper, instance.getMediaWrapper());
    }

    @Test
    public void shouldMapHtml5EventWhenFired() {
        // given
        MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
        doReturn(mediaBase).when(mediaWrapper).getMediaObject();
        Map<HTML5MediaEventsType, MediaEventTypes> pairMap = createEventsPairMap();
        SoundExecutorListener soundExecutorListener = mock(SoundExecutorListener.class);

        instance.setMediaWrapper(mediaWrapper);
        instance.setSoundFinishedListener(soundExecutorListener);

        // when - then
        for (Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : pairMap.entrySet()) {
            instance.onHtml5MediaEvent(typePair.getKey());
            verify(mediaEventMapper).mapAndFireEvent(typePair.getKey(), soundExecutorListener, mediaWrapper);
        }
    }

    @Test
    public void shouldSetBaseMediaConfiguration() {
        // given
        instance.setBaseMediaConfiguration(mediaConfiguration);

        // when
        BaseMediaConfiguration result = instance.getBaseMediaConfiguration();

        // then
        assertEquals(mediaConfiguration, result);
    }

    private Map<HTML5MediaEventsType, MediaEventTypes> createEventsPairMap() {
        Map<HTML5MediaEventsType, MediaEventTypes> pairMap = ImmutableMap.<HTML5MediaEventsType, MediaEventTypes>builder()
                .put(HTML5MediaEventsType.canplay, MediaEventTypes.CAN_PLAY)
                .put(HTML5MediaEventsType.suspend, MediaEventTypes.SUSPEND)
                .put(HTML5MediaEventsType.durationchange, MediaEventTypes.ON_DURATION_CHANGE)
                .put(HTML5MediaEventsType.ended, MediaEventTypes.ON_END)
                .put(HTML5MediaEventsType.error, MediaEventTypes.ON_ERROR)
                .put(HTML5MediaEventsType.pause, MediaEventTypes.ON_PAUSE)
                .put(HTML5MediaEventsType.timeupdate, MediaEventTypes.ON_TIME_UPDATE)
                .put(HTML5MediaEventsType.volumechange, MediaEventTypes.ON_VOLUME_CHANGE)
                .put(HTML5MediaEventsType.play, MediaEventTypes.ON_PLAY)
                .build();
        return pairMap;
    }

    @Test
    public void shouldSetMedia() {
        // given
        instance.setMedia(mediaBase);

        // when
        MediaBase result = instance.getMedia();

        // then
        assertEquals(mediaBase, result);
    }

    @Test
    public void shouldPlayMediaElement() {
        // given
        instance.setMedia(mediaBase);

        // when
        instance.play();

        // then
        verify(mediaBase).setLoop(false);
        verify(mediaBase).play();
    }

    @Test
    public void shouldPlayMediaElementLooped() {
        // given
        instance.setMedia(mediaBase);

        // when
        instance.playLooped();

        // then
        verify(mediaBase).setLoop(true);
        verify(mediaBase).play();
    }

    @Test
    public void shouldPlayMediaElementWithString() {
        // given
        instance.setMedia(mediaBase);

        // when
        instance.play("");

        // then
        verify(mediaBase).play();
    }

    @Test
    public void shouldStopPlaying() {
        // given
        instance.setMedia(mediaBase);

        // when
        instance.stop();

        // then
        verify(mediaBase).pause();
        verify(mediaBase).setCurrentTime(Matchers.eq(0d));
    }

    @Test
    public void shouldPauseMediaElement_andSetCurrentTimeOnIt() {
        // given
        double currentTime = 30;
        when(mediaBase.getCurrentTime()).thenReturn(currentTime);
        instance.setMedia(mediaBase);

        // when
        instance.pause();

        // then
        verify(mediaBase).pause();
        verify(mediaBase).setCurrentTime(currentTime);
    }

    @Test
    public void shouldMutePlayedElement() {
        // given
        instance.setMedia(mediaBase);

        // when - then
        instance.setMuted(true);
        verify(mediaBase).setMuted(Matchers.eq(true));
        instance.setMuted(false);
        verify(mediaBase).setMuted(Matchers.eq(false));
    }

    @Test
    public void shouldSetGivenVolume() {
        // given
        instance.setMedia(mediaBase);

        // when-then
        instance.setVolume(3);
        verify(mediaBase).setVolume(Matchers.eq(3d));
        instance.setVolume(5.6);
        verify(mediaBase).setVolume(Matchers.eq(5.6d));
    }

    @Test
    public void shouldSetCurrentTime() {
        // given
        instance.setMedia(mediaBase);

        // when
        instance.setCurrentTime(3);

        // then
        verify(mediaBase).setCurrentTime(Matchers.eq(3d));
    }

    @Test
    public void shouldSetCurrentTimeOnZeroWhenNANGiven() {
        // given
        instance.setMedia(mediaBase);
        doReturn(Double.NaN).when(mediaBase).getDuration();

        // when
        instance.setCurrentTime(3);

        // then
        verify(mediaBase, times(0)).setCurrentTime(Matchers.anyDouble());
    }

    @Test
    public void shouldGetMediaPreloadType() {
        // given
        String expected = getAssumedMediaPreloadType();

        // when
        String result = instance.getMediaPreloadType();

        // then
        assertEquals(expected, result);
    }

    protected String getAssumedMediaPreloadType() {
        return MediaElement.PRELOAD_METADATA;
    }
}
