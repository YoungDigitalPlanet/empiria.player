package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.inject.Provider;
import eu.ydp.gwtutil.client.collections.MapStringToIntMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorServiceTest {
    @Mock
    private Provider<PersonaService> personaServiceProvider;
    @InjectMocks
    private TutorService tutorService;
    private TutorConfig tutorConfig;

    @Test
    public void shouldReturnRegisteredTutorConfig() throws Exception {
        // given
        String tutorId = "tutorId";
        tutorConfig = mock(TutorConfig.class);
        tutorService.registerTutor(tutorId, tutorConfig);

        // when
        TutorConfig result = tutorService.getTutorConfig(tutorId);

        // then
        assertThat(result).isEqualTo(tutorConfig);
    }

    @Test
    public void shouldCreateNewPersonaService() throws Exception {
        // given
        String tutorId = "tutorId";
        PersonaService createdPersonaService = expectPersonaServiceCreationForTutorId(tutorId);

        // when
        PersonaService result = tutorService.getTutorPersonaService(tutorId);

        // then
        assertThat(result).isEqualTo(createdPersonaService);
    }

    @Test
    public void shouldReturnPreviouslyCreatedPersonaService() throws Exception {
        // given
        String tutorId = "tutorId";
        PersonaService createdPersonaService = expectPersonaServiceCreationForTutorId(tutorId);

        // when
        tutorService.getTutorPersonaService(tutorId);
        PersonaService personaService = tutorService.getTutorPersonaService(tutorId);

        // then
        assertThat(personaService).isEqualTo(createdPersonaService);
        verify(personaService).init(tutorConfig, 0);
    }

    @Test
    public void shouldReturnDifferentServicesForDifferentTutors() throws Exception {
        // given
        String tutorId1 = "tutorId1";
        String tutorId2 = "tutorId2";
        when(personaServiceProvider.get()).thenReturn(mock(PersonaService.class), mock(PersonaService.class));

        // when
        PersonaService personaService1 = tutorService.getTutorPersonaService(tutorId1);
        PersonaService personaService2 = tutorService.getTutorPersonaService(tutorId2);

        // then
        verify(personaService1).init(any(TutorConfig.class), eq(0));
        verify(personaService2).init(any(TutorConfig.class), eq(0));
    }

    @Test
    public void buildTutorToCurrentPersonaIndexMap() throws Exception {
        // given
        PersonaService persona1 = mock(PersonaService.class);
        PersonaService persona2 = mock(PersonaService.class);
        when(personaServiceProvider.get()).thenReturn(persona1, persona2);

        String tutorId1 = "tutorId1";
        String tutorId2 = "tutorId2";

        tutorService.registerTutor(tutorId1, mock(TutorConfig.class));
        tutorService.registerTutor(tutorId2, mock(TutorConfig.class));

        // when
        Map<String, Integer> tutorIdToCurrentPersonaIndexMap = tutorService.buildTutorIdToCurrentPersonaIndexMap();

        // then
        assertThat(tutorIdToCurrentPersonaIndexMap.size()).isEqualTo(2);
        assertThat(tutorIdToCurrentPersonaIndexMap.get(tutorId1)).isEqualTo(0);
        assertThat(tutorIdToCurrentPersonaIndexMap.get(tutorId2)).isEqualTo(0);
        verify(persona1).init(any(TutorConfig.class), eq(0));
        verify(persona2).init(any(TutorConfig.class), eq(0));
    }

    @Test
    public void importPersonaIndexes() throws Exception {
        // given
        PersonaService persona0 = mock(PersonaService.class);
        PersonaService persona1 = mock(PersonaService.class);
        when(personaServiceProvider.get()).thenReturn(persona0, persona1);

        String tutorId1 = "tutorId1";
        String tutorId2 = "tutorId2";

        int tutorPersonaIndex1 = 1;
        int tutorPersonaIndex2 = 2;

        MapStringToIntMock tutorIdToCurrentPersonaMap = new MapStringToIntMock();
        tutorIdToCurrentPersonaMap.put(tutorId1, tutorPersonaIndex1);
        tutorIdToCurrentPersonaMap.put(tutorId2, tutorPersonaIndex2);

        // when
        tutorService.importCurrentPersonasForTutors(tutorIdToCurrentPersonaMap);
        PersonaService tutorPersonaService1 = tutorService.getTutorPersonaService(tutorId1);
        PersonaService tutorPersonaService2 = tutorService.getTutorPersonaService(tutorId2);

        // then
        verify(tutorPersonaService1).init(any(TutorConfig.class), eq(tutorPersonaIndex1));
        verify(tutorPersonaService2).init(any(TutorConfig.class), eq(tutorPersonaIndex2));
    }

    @Test
    public void askForOtherPersonaThanImported() throws Exception {
        // given
        PersonaService persona0 = mock(PersonaService.class);
        PersonaService persona1 = mock(PersonaService.class);
        when(personaServiceProvider.get()).thenReturn(persona0, persona1);

        String tutorId1 = "tutorId1";
        String tutorId2 = "tutorId2";

        int tutorPersonaIndex1 = 1;

        MapStringToIntMock tutorIdToCurrentPersonaMap = new MapStringToIntMock();
        tutorIdToCurrentPersonaMap.put(tutorId1, tutorPersonaIndex1);

        // when
        tutorService.importCurrentPersonasForTutors(tutorIdToCurrentPersonaMap);
        PersonaService personaService1 = tutorService.getTutorPersonaService(tutorId1);
        PersonaService personaService2 = tutorService.getTutorPersonaService(tutorId2);

        // then
        verify(personaService1).init(any(TutorConfig.class), eq(tutorPersonaIndex1));
        verify(personaService2).init(any(TutorConfig.class), eq(0));
    }

    private PersonaService expectPersonaServiceCreationForTutorId(String tutorId) {
        tutorConfig = mock(TutorConfig.class);
        PersonaService createdPersonaService = mock(PersonaService.class);
        when(personaServiceProvider.get()).thenReturn(createdPersonaService);

        tutorService.registerTutor(tutorId, tutorConfig);

        when(createdPersonaService.getCurrentPersonaIndex()).thenReturn(0);

        return createdPersonaService;
    }
}
