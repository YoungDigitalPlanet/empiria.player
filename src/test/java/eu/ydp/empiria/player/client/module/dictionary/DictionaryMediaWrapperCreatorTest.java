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

package eu.ydp.empiria.player.client.module.dictionary;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.DictionaryMediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryMediaWrapperCreatorTest {

    @InjectMocks
    private DictionaryMediaWrapperCreator testObj;

    @Mock
    private DictionaryMimeSourceProvider dictionaryMimeSourceProvider;

    @Mock
    private EventsBus eventsBus;

    @Captor
    private ArgumentCaptor<PlayerEvent> playerEventCaptor;

    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackArgumentCaptor;

    @Test
    public void shouldFireCreateMediaWrapperEvent() {
        // given
        String fileName = "test.mp3";
        String mime = "audio/mp3";
        Map<String, String> sourcesWithTypes = Maps.newHashMap();
        sourcesWithTypes.put(fileName, mime);
        CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = mock(CallbackReceiver.class);

        when(dictionaryMimeSourceProvider.getSourcesWithTypes(fileName)).thenReturn(sourcesWithTypes);

        // when
        testObj.create(fileName, callbackReceiver);

        // then
        verify(eventsBus).fireEvent(playerEventCaptor.capture());
        PlayerEvent calledEvent = playerEventCaptor.getValue();
        assertEquals(PlayerEventTypes.CREATE_MEDIA_WRAPPER, calledEvent.getType());
    }
}
