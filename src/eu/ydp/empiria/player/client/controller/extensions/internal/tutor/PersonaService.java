package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.TutorEvent;
import eu.ydp.empiria.player.client.module.tutor.TutorEventTypes;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class PersonaService {

	private int currentPersonaIndex = 0;
	private final TutorConfig tutorConfig;
	private final EventsBus eventsBus;

	@Inject
	public PersonaService(@Assisted TutorConfig tutorConfig, EventsBus eventsBus) {
		this.tutorConfig = tutorConfig;
		this.eventsBus = eventsBus;
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
