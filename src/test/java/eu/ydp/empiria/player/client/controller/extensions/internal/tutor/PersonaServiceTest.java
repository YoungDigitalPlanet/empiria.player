package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.tutor.TutorEvent;
import eu.ydp.empiria.player.client.module.tutor.TutorEventTypes;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

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
