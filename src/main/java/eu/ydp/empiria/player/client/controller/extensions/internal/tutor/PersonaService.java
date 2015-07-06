package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.tutor.TutorEvent;
import eu.ydp.empiria.player.client.module.tutor.TutorEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public class PersonaService {

	@Inject
	private EventsBus eventsBus;

	private int currentPersonaIndex;
	private TutorConfig tutorConfig;

	public void init(TutorConfig tutorConfig, Integer initialPersonaIndex) {
		this.tutorConfig = tutorConfig;
		this.currentPersonaIndex = initialPersonaIndex;
	}

	public TutorPersonaProperties getPersonaProperties() {
		return tutorConfig.getTutorPersonaProperties(currentPersonaIndex);
	}

	public int getCurrentPersonaIndex() {
		return currentPersonaIndex;
	}

	public void setCurrentPersonaIndex(int personaIndex) {
		if (personaIndex != currentPersonaIndex) {
			this.currentPersonaIndex = personaIndex;
			eventsBus.fireAsyncEvent(new TutorEvent(TutorEventTypes.TUTOR_CHANGED, this, personaIndex));
		}
	}
}
