package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

public class PersonaToViewDtoConverter {

	private final EmpiriaPaths empiriaPaths;

	@Inject
	public PersonaToViewDtoConverter(EmpiriaPaths empiriaPaths) {
		this.empiriaPaths = empiriaPaths;
	}

	public List<PersonaViewDto> convert(List<TutorPersonaProperties> personas) {
		List<PersonaViewDto> viewDtos = new ArrayList<PersonaViewDto>();
		for (TutorPersonaProperties tutorPersonaProperties : personas) {
			PersonaViewDto viewPersona = convert(tutorPersonaProperties);
			viewDtos.add(viewPersona);
		}
		return viewDtos;
	}

	private PersonaViewDto convert(TutorPersonaProperties tutorPersonaProperties) {
		String name = tutorPersonaProperties.getName();
		String avatarFilenameSuffix = tutorPersonaProperties.getAvatarFilename();
		String avatarFileName = name + avatarFilenameSuffix;
		String avatarFilenamePath = empiriaPaths.getCommonsFilePath(avatarFileName);
		PersonaViewDto viewPersona = new PersonaViewDto(tutorPersonaProperties.getIndex(), avatarFilenamePath);
		return viewPersona;
	}

}
