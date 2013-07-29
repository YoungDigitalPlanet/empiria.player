package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;

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
	private String tutorId = "tutorId";
	private List<TutorPersonaProperties> personas = Lists.newArrayList();
	private List<PersonaViewDto> viewPersonas = Lists.newArrayList();

	@Before
	public void setUp() {
		when(tutorService.getTutorConfig(tutorId))
			.thenReturn(tutorConfig);
		
		when(tutorConfig.getPersonas())
			.thenReturn(personas);
		
		when(personaConverter.convert(personas))
			.thenReturn(viewPersonas);
		
		tutorPopupPresenterImpl.init(tutorId);
	}
	
	@Test
	public void testInit() throws Exception {
		//given
		int index = 5;
		PersonaViewDto viewPersona = new PersonaViewDto(index, "URL");
		viewPersonas.add(viewPersona);
		
		//when
		tutorPopupPresenterImpl.init(tutorId);
		
		//then
		for (PersonaViewDto personaViewDto : viewPersonas) {
			verify(tutorPopupView).addPersona(personaViewDto);
			verify(tutorPopupView).addClickHandlerToPersona(Mockito.any(PopupClickCommand.class));
		}
	}

	@Test
	public void testShow() throws Exception {
		//given
		Integer index = 5;
		
		PersonaService personaService = mock(PersonaService.class);
		when(tutorService.getTutorPersonaService(tutorId)).thenReturn(personaService);
		when(personaService.getCurrentPersonaIndex()).thenReturn(index);
		
		
		//when
		tutorPopupPresenterImpl.show();
		

		//then
		verify(tutorPopupView).setSelected(index);
		verify(tutorPopupView).show();
	}

	@Test
	public void testClicked() throws Exception {
		//given
		int index = 7;
		PersonaViewDto personaView = new PersonaViewDto(index, "avatarUrl");
		PersonaService personaService = mock(PersonaService.class);
		when(tutorService.getTutorPersonaService(tutorId))
			.thenReturn(personaService);
		
		
		//then
		tutorPopupPresenterImpl.clicked(personaView);
		
		
		//when
		verify(personaService).setCurrentPersonaIndex(index);
	}
}
