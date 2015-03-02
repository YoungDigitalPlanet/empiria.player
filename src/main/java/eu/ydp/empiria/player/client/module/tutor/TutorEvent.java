package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class TutorEvent extends AbstractEvent<TutorEventHandler, TutorEventTypes> {

	private static final EventTypes<TutorEventHandler, TutorEventTypes> types = new EventTypes<TutorEventHandler, TutorEventTypes>();

	private boolean isMuted;
	private int personaIndex;

	public TutorEvent(TutorEventTypes type, Object source, int personaIndex) {
		super(type, source);
		this.personaIndex = personaIndex;
	}

	public TutorEvent(TutorEventTypes type, boolean isMuted, Object source) {
		super(type, source);
		this.isMuted = isMuted;
	}

	public int getPersonaIndex() {
		return personaIndex;
	}

	@Override
	protected EventTypes<TutorEventHandler, TutorEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(TutorEventHandler handler) {
		handler.onTutorChanged(this);
	}

	public static Type<TutorEventHandler, TutorEventTypes> getType(TutorEventTypes type) {
		return types.getType(type);
	}

	public boolean isMuted() {
		return isMuted;
	}
}