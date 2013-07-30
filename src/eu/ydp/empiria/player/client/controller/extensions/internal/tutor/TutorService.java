package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PersonaServiceFactory;

public class TutorService {

	private final Map<String, TutorConfig> tutors = newHashMap();
	private final Map<String, PersonaService> personaServices = newHashMap();
	private final PersonaServiceFactory personaServiceFactory;
	
	@Inject
	public TutorService(PersonaServiceFactory personaServiceFactory) {
		this.personaServiceFactory = personaServiceFactory;
	}

	public TutorConfig getTutorConfig(String tutorId){
		return tutors.get(tutorId);
	}
	
	public void registerTutor(String tutorId, TutorConfig tutorConfig){
		tutors.put(tutorId, tutorConfig);
	}
	
	public PersonaService getTutorPersonaService(String tutorId){
		PersonaService personaService = personaServices.get(tutorId);
		
		if(personaService == null) {
			TutorConfig tutorConfig = getTutorConfig(tutorId);
			personaService = personaServiceFactory.createPersonaService(tutorConfig);
			personaServices.put(tutorId, personaService);
		}
		
		return personaService;
	}
}
