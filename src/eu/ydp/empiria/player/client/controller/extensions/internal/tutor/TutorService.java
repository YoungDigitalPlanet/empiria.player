package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PersonaServiceFactory;
import eu.ydp.gwtutil.client.collections.MapStringToInt;
import static com.google.common.collect.Maps.newHashMap;

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
	
	public Map<String, Integer> buildTutorIdToCurrentPersonaIndexMap() {
		Set<String> tutorsIds = tutors.keySet();
		Map<String, Integer> tutorIdToCurrentPersonaMap = new HashMap<String, Integer>();
		
		for (String tutorId : tutorsIds) {
			PersonaService personaService = getTutorPersonaService(tutorId);
			int personaIndex = personaService.getCurrentPersonaIndex();
			tutorIdToCurrentPersonaMap.put(tutorId, personaIndex);
		}
		return tutorIdToCurrentPersonaMap;
	}
	
	public void setCurrentPersonasForTutors(MapStringToInt tutorIdToPersonaIndex) {
		for(String tutorId : tutorIdToPersonaIndex.keys() ) {
			int selectedPersonaIndex = tutorIdToPersonaIndex.get(tutorId);
			PersonaService personaService = getTutorPersonaService(tutorId);
			personaService.setCurrentPersonaIndex(selectedPersonaIndex);
		}
	}
}
