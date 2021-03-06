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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;
import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class DefaultMediaEventControllerJUnitTest {

    @InjectMocks
    private DefaultMediaEventController testObj;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    protected MediaEvent mediaEvent;
    @Mock
    private Logger logger;

    @Mock
    protected AbstractMediaProcessor mediaProcessor;
    @Mock
    protected MediaExecutor<?> mediaExecutor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mediaExecutor.getMediaWrapper()).thenReturn(mock(MediaWrapper.class));
    }

    @Test
    public void shouldProcess_onChangeVolume() {
        // given
        double volume = 10.25;
        double assumedVolume = 10.25;
        when(mediaEvent.getAssociatedType().getType()).thenReturn(CHANGE_VOLUME);
        when(mediaEvent.getVolume()).thenReturn(volume);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).setVolume(assumedVolume);
    }

    @Test
    public void shouldProcess_stop() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(STOP);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).stop();
    }

    @Test
    public void shouldProcess_pause() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(PAUSE);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).pause();
    }

    @Test
    public void shouldProcess_resume() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(RESUME);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).resume();
    }

    @Test
    public void shouldProcess_setCurrentTime() {
        // given
        double currentTime = 10.25;
        double assumedCurrentTime = 10.25;
        when(mediaEvent.getAssociatedType().getType()).thenReturn(SET_CURRENT_TIME);
        when(mediaEvent.getCurrentTime()).thenReturn(currentTime);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).setCurrentTime(assumedCurrentTime);
    }

    @Test
    public void shouldProcess_play() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(PLAY);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).play();
    }

    @Test
    public void shouldProcess_playLooped() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(PLAY_LOOPED);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).playLooped();
        verifyNoMoreInteractions(mediaExecutor);
    }

    @Test
    public void shouldProcess_mute() {
        // given
        boolean isMuted = false;
        boolean assumedIsMuted = true;
        when(mediaEvent.getAssociatedType().getType()).thenReturn(MUTE);
        when(mediaExecutor.getMediaWrapper().isMuted()).thenReturn(isMuted);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).setMuted(assumedIsMuted);
    }

    @Test
    public void shouldProcess_unMute() {
        // given
        boolean isMuted = true;
        boolean assumedIsMuted = false;
        when(mediaEvent.getAssociatedType().getType()).thenReturn(MUTE);
        when(mediaExecutor.getMediaWrapper().isMuted()).thenReturn(isMuted);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).setMuted(assumedIsMuted);
    }

    @Test
    public void shouldProcess_ended() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(ENDED);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).stop();
    }

    @Test
    public void shouldProcess_onEnd() {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(ON_END);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(mediaExecutor).stop();
    }

    @Test
    public void shouldProcess_onError() {
        // given
        String assumedErrorMsg = "Media Error";
        when(mediaEvent.getAssociatedType().getType()).thenReturn(ON_ERROR);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verify(logger).info(assumedErrorMsg);
    }

    @Test
    @Parameters
    public void shouldNotProcessOtherEvents(MediaEventTypes type) {
        // given
        when(mediaEvent.getAssociatedType().getType()).thenReturn(type);

        // when
        testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

        // then
        verifyZeroInteractions(mediaExecutor, mediaProcessor, logger);
    }

    public Object[] parametersForShouldNotProcessOtherEvents() {
        final List<MediaEventTypes> mappedEvents = Lists.newArrayList(CHANGE_VOLUME, STOP, PAUSE, RESUME, SET_CURRENT_TIME, PLAY, PLAY_LOOPED, MUTE, ENDED,
                ON_END, ON_ERROR);
        List<MediaEventTypes> events = FluentIterable.from(Lists.newArrayList(MediaEventTypes.values())).filter(new Predicate<MediaEventTypes>() {

            @Override
            public boolean apply(MediaEventTypes type) {
                return !mappedEvents.contains(type);
            }

        }).toList();

        return $(events.toArray());
    }
}
