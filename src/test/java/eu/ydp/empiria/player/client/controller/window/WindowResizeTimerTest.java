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

package eu.ydp.empiria.player.client.controller.window;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WindowResizeTimerTest {

    @InjectMocks
    private WindowResizeTimer testObj;
    @Mock
    private EventsBus eventsBus;
    @Captor
    private ArgumentCaptor<PlayerEvent> eventCaptor;

    @Test
    public void shouldExecuteCommand() {
        // given

        // when
        testObj.run();

        // then
        verify(eventsBus).fireAsyncEvent(eventCaptor.capture());
        PlayerEvent playerEvent = eventCaptor.getValue();
        assertThat(playerEvent.getType()).isEqualTo(PlayerEventTypes.WINDOW_RESIZED);
    }
}
