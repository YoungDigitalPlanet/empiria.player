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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationDescriptionSoundControllerTest {

    private ExplanationDescriptionSoundController testObj;
    private static final String FILE_NAME = "test.mp3";

    @Mock
    private ExplanationView explanationView;

    @Mock
    private DictionaryModuleFactory dictionaryModuleFactory;

    @Mock
    private DescriptionSoundController descriptionSoundController;

    @Mock
    private MediaEvent mediaEvent;

    @Mock
    private MediaWrapper<Widget> mediaWrapper;

    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

    @Captor
    private ArgumentCaptor<MediaEventHandler> abstractMediaHandlerCaptor;

    @Before
    public void setUp() {
        testObj = spy(new ExplanationDescriptionSoundController(explanationView, descriptionSoundController));
    }

    @Test
    public void shouldPlay_whenIsNotPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(false);

        // when
        testObj.playOrStopExplanationSound(FILE_NAME);

        // then
        verify(descriptionSoundController).createMediaWrapper(eq(FILE_NAME), callbackReceiverCaptor.capture());
        CallbackReceiver<MediaWrapper<Widget>> value = callbackReceiverCaptor.getValue();
        value.setCallbackReturnObject(mediaWrapper);
        verify(descriptionSoundController).playFromMediaWrapper(abstractMediaHandlerCaptor.capture(), eq(mediaWrapper));
        verify(explanationView).setExplanationPlayButtonStyle();
        abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);
        verify(explanationView).setExplanationStopButtonStyle();
        verify(descriptionSoundController).stopPlaying();
    }

    @Test
    public void shouldStopPlaying_whenPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(true);

        // when
        testObj.playOrStopExplanationSound(FILE_NAME);

        // then
        verify(testObj).stop();
    }
}
