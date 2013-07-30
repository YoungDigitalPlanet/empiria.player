package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;

public interface PersonaServiceFactory {

	PersonaService createPersonaService(TutorConfig tutorConfig);
	
}
