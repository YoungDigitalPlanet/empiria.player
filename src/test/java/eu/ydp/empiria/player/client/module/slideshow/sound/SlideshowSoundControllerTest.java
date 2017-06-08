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

package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SlideshowSoundControllerTest {

    @InjectMocks
    private SlideshowSoundController testObj;
    @Mock
    private MediaWrapperController mediaWrapperController;
    @Mock
    private SlideshowSounds slideshowSounds;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private SlideEndHandler command;
    @Mock
    private Optional<SlideEndHandler> audioEnd;
    @Mock
    private MediaWrapper<Widget> sound;
    @Mock
    private MediaEvent mediaEvent;
    private final String filepath = "test.mp3";

    @Before
    public void init() {
        when(slideshowSounds.getSound(filepath)).thenReturn(sound);
    }

    @Test
    public void shouldInitSounds() {
        // given

        // when
        testObj.initSound(filepath);
        testObj.initSound(filepath);

        // then
        verify(slideshowSounds, times(2)).initSound(filepath);
    }

    @Test
    public void shouldAddHandlerOnInit() {
        // given
        EventType<MediaEventHandler, MediaEventTypes> eventType = MediaEvent.getType(MediaEventTypes.ON_END);

        // then
        verify(eventsBus).addHandler(eventType, testObj);
    }

    @Test
    public void shouldPlayNewSound() {
        // given

        // when
        testObj.playSound(filepath, command);

        // then
        verify(mediaWrapperController).play(sound);
    }


    @Test
    public void shouldPauseSound() {
        // given

        // when
        testObj.pauseSound(filepath);

        // then
        verify(mediaWrapperController).pause(sound);
    }

    @Test
    public void shouldStopAllSounds() {
        // given
        Collection<MediaWrapper<Widget>> sounds = Lists.newArrayList();
        sounds.add(sound);
        sounds.add(sound);
        when(slideshowSounds.getAllSounds()).thenReturn(sounds);

        // when
        testObj.stopAllSounds();

        // then
        verify(mediaWrapperController, times(2)).stop(sound);
    }

    @Test
    public void shouldExecuteAudioEnd_whenSlideSoundsContainsWrapper() {
        // given
        when(slideshowSounds.containsWrapper(any(MediaWrapper.class))).thenReturn(true);
        testObj.playSound(filepath, command);

        // when
        testObj.onMediaEvent(mediaEvent);

        // then
        verify(command).onEnd();
    }

    @Test
    public void shouldNotExecuteAudioEnd_andSlideSoundDoesNotContainWrapper() {
        // given
        when(slideshowSounds.containsWrapper(any(MediaWrapper.class))).thenReturn(false);
        testObj.playSound(filepath, command);

        // when
        testObj.onMediaEvent(mediaEvent);

        // then
        verify(command, never()).onEnd();

    }
}
