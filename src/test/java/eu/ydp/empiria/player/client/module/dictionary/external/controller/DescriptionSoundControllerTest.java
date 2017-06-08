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
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DescriptionSoundControllerTest {

    private static final String FILE_NAME = "test.mp3";

    @InjectMocks
    public DescriptionSoundController testObject;
    @Mock
    private Entry entry;
    @Mock
    private DictionaryMediaWrapperCreator dictionaryMediaWrapperCreator;
    @Mock
    private CallbackReceiver<MediaWrapper<Widget>> callbackReceiver;
    @Mock
    private MediaEventHandler abstractMediaHandler;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private MediaWrapper<Widget> mediaWrapper;
    @Mock
    private CurrentPageScope currentPageScope;
    @Mock
    private PageScopeFactory pageScopeFactory;
    @Mock
    private MediaWrapperController mediaWrapperController;
    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

    @Before
    public void setUp() {
        when(entry.getEntrySound()).thenReturn(FILE_NAME);
    }

    @Test
    public void shouldCreateMediaWrapper_whenFilenameIsNotNull() {
        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldNotCreateMediaWrapper_whenFilenameIsNull() {
        // given
        when(entry.getEntrySound()).thenReturn(null);

        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator, never()).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldNotCreateMediaWrapper_whenFilenameIsEmpty() {
        // given
        when(entry.getEntrySound()).thenReturn("");

        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator, never()).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldAddAllMediaHandlersAndPlay_whenPlayFromMediaWrapperIsCalled() {
        // given
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(currentPageScope);

        // when
        testObject.playFromMediaWrapper(abstractMediaHandler, mediaWrapper);

        // then
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), mediaWrapper, abstractMediaHandler, pageScopeFactory.getCurrentPageScope());
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, abstractMediaHandler, pageScopeFactory.getCurrentPageScope());
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), mediaWrapper, abstractMediaHandler, pageScopeFactory.getCurrentPageScope());
        verify(mediaWrapperController).stopAndPlay(mediaWrapper);
    }

    @Test
    public void shouldStopMediaWrapper_whenStopMediaWrapperIsCalled() {
        // then
        testObject.playFromMediaWrapper(abstractMediaHandler, mediaWrapper);
        testObject.stopMediaWrapper();

        // then
        verify(mediaWrapperController).stop(mediaWrapper);
    }

}
