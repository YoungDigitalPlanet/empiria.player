package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;

public class PersonaViewDtoConverter {

	public List<PersonaViewDto> createPersonasDtos(List<TutorPersonaProperties> personas) {
		List<PersonaViewDto> viewDtos = new ArrayList<PersonaViewDto>();
		for (TutorPersonaProperties tutorPersonaProperties : personas) {
			PersonaViewDto viewPersona = new PersonaViewDto(tutorPersonaProperties.getIndex(), tutorPersonaProperties.getAvatarFilename());
			viewDtos.add(viewPersona);
		}
		return viewDtos;
	}

}
