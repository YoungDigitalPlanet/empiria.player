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

package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorPopupPresenterImplTest {
    @Mock
    private TutorPopupView tutorPopupView;
    @Mock
    private TutorService tutorService;
    @Mock
    private PersonaToViewDtoConverter personaConverter;
    @Mock
    private TutorConfig tutorConfig;

    @InjectMocks
    private TutorPopupPresenterImpl tutorPopupPresenterImpl;
    private final String tutorId = "tutorId";
    private final List<TutorPersonaProperties> personas = Lists.newArrayList();
    private final List<PersonaViewDto> viewPersonas = Lists.newArrayList();

    @Before
    public void setUp() {
        when(tutorService.getTutorConfig(tutorId)).thenReturn(tutorConfig);

        when(tutorConfig.getPersonas()).thenReturn(personas);

        when(personaConverter.convert(personas)).thenReturn(viewPersonas);

        tutorPopupPresenterImpl.init(tutorId);
    }

    @Test
    public void testInit() throws Exception {
        // given
        int index = 5;
        PersonaViewDto viewPersona = new PersonaViewDto(index, "URL");
        viewPersonas.add(viewPersona);
        viewPersona = new PersonaViewDto(++index, "URL");
        viewPersonas.add(viewPersona);

        // when
        tutorPopupPresenterImpl.init(tutorId);

        // then
        for (PersonaViewDto personaViewDto : viewPersonas) {
            verify(tutorPopupView).addPersona(personaViewDto);
            verify(tutorPopupView).addClickHandlerToPersona(Matchers.any(PopupClickCommand.class), eq(personaViewDto.getPersonaIndex()));
        }
    }

    @Test
    public void testShow() throws Exception {
        // given
        Integer index = 5;

        PersonaService personaService = mock(PersonaService.class);
        when(tutorService.getTutorPersonaService(tutorId)).thenReturn(personaService);
        when(personaService.getCurrentPersonaIndex()).thenReturn(index);

        // when
        tutorPopupPresenterImpl.show();

        // then
        verify(tutorPopupView).setSelected(index);
        verify(tutorPopupView).show();
    }

    @Test
    public void testClicked() throws Exception {
        // given
        int index = 7;
        PersonaViewDto personaView = new PersonaViewDto(index, "avatarUrl");
        PersonaService personaService = mock(PersonaService.class);
        when(tutorService.getTutorPersonaService(tutorId)).thenReturn(personaService);

        // then
        tutorPopupPresenterImpl.clicked(personaView);

        // when
        verify(personaService).setCurrentPersonaIndex(index);
    }
}
