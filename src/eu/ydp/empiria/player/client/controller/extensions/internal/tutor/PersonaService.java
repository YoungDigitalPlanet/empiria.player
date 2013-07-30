package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class PersonaService {

	private int currentPersopnaIndex = 0;
	private final TutorConfig tutorConfig;
	private final EventsBus eventsBus;
	
	@Inject
	public PersonaService(@Assisted TutorConfig tutorConfig, EventsBus eventsBus) {
		this.tutorConfig = tutorConfig;
		this.eventsBus = eventsBus;
	}

	public TutorPersonaProperties getPersonaProperties(){
		return tutorConfig.getTutorPersonaProperties(currentPersopnaIndex);
	}
	
	public int getCurrentPersonaIndex(){
		return currentPersopnaIndex;
	}
	
	public void setCurrentPersonaIndex(int personaIndex){
		this.currentPersopnaIndex = personaIndex;
		//TODO: throw TutorPersonaChangedEvent
	}
}
