package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.collections.MapStringToInt;

public class TutorService {

	private final Map<String, TutorConfig> tutors = newHashMap();
	private final Map<String, PersonaService> personaServices = newHashMap();
	private final Provider<PersonaService> personaServiceProvider; 
	private Optional<MapStringToInt> importedTutorIdToPersonaIndex = Optional.absent();
	
	@Inject
	public TutorService(Provider<PersonaService> personaServiceProvider) {
		this.personaServiceProvider = personaServiceProvider;
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
			int initialPersonaIndex = findInitialPersonaIndex(tutorId);
			personaService = personaServiceProvider.get();
			personaService.init(tutorConfig, initialPersonaIndex); 
			personaServices.put(tutorId, personaService);
		}
		
		return personaService;
	}
	
	private int findInitialPersonaIndex(String tutorId) {
		boolean containsImportedPersonaIndex = importedTutorIdToPersonaIndex.isPresent()  &&  importedTutorIdToPersonaIndex.get().containsKey(tutorId);
		if (containsImportedPersonaIndex){
			return importedTutorIdToPersonaIndex.get().get(tutorId);
		} else {
			return 0;
		}
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
	
	public void importCurrentPersonasForTutors(MapStringToInt importedTutorIdToPersonaIndex) {
		this.importedTutorIdToPersonaIndex = Optional.of(importedTutorIdToPersonaIndex);
	}
}
