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

package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.module.tutor.TutorEvent;
import eu.ydp.empiria.player.client.module.tutor.TutorEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonaServiceTest {
    @Mock
    private EventsBus eventsBus;
    @Mock
    private TutorConfig tutorConfig;

    @InjectMocks
    private PersonaService personaService;

    @Test
    public void shouldInitialyReturnZeroIndexAsPersonaIndex() throws Exception {
        int currentPersonaIndex = personaService.getCurrentPersonaIndex();

        assertThat(currentPersonaIndex).isEqualTo(0);
    }

    @Test
    public void shouldReturnPreviouslySetPersonaIndex() throws Exception {
        int expectedPersonaIndex = 1313;

        personaService.setCurrentPersonaIndex(expectedPersonaIndex);
        int currentPersonaIndex = personaService.getCurrentPersonaIndex();

        assertThat(currentPersonaIndex).isEqualTo(expectedPersonaIndex);
    }

    @Test
    public void shouldThrowEventWhenChangingCurrentPersona() throws Exception {
        // given
        int newPersonaIndex = 123;

        // when
        personaService.setCurrentPersonaIndex(newPersonaIndex);

        // then
        ArgumentCaptor<TutorEvent> eventCaptor = ArgumentCaptor.forClass(TutorEvent.class);
        verify(eventsBus).fireAsyncEvent(eventCaptor.capture());

        TutorEvent tutorEvent = eventCaptor.getValue();
        TutorEventTypes type = tutorEvent.getType();
        assertThat(type).isEqualTo(TutorEventTypes.TUTOR_CHANGED);
    }

    @Test
    public void shouldntThrowEventWhenIndexOfCurrentPersonaNotChanged() throws Exception {
        // given
        int samePersonaIndex = 0;

        // when
        personaService.setCurrentPersonaIndex(samePersonaIndex);

        // then
        Mockito.verifyZeroInteractions(eventsBus);
    }

    @Test
    public void shouldReturnPropertiesForCurrentPersona() throws Exception {
        // given
        int tutorPersonaIndex = 123;

        TutorPersonaProperties personaProperties = Mockito.mock(TutorPersonaProperties.class);
        when(tutorConfig.getTutorPersonaProperties(tutorPersonaIndex)).thenReturn(personaProperties);

        personaService.setCurrentPersonaIndex(tutorPersonaIndex);

        // when
        TutorPersonaProperties result = personaService.getPersonaProperties();

        // then
        assertThat(result).isEqualTo(personaProperties);
    }
}
