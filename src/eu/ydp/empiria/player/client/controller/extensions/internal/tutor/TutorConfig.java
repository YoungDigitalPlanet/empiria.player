package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

public class TutorConfig {
	private TutorConfigJs js;

	public boolean supportsAction(ActionType type){
		throw new UnsupportedOperationException();
	}
	
	public Iterable<TutorCommandConfig> getCommandsForAction(ActionType type){
		throw new UnsupportedOperationException();
	}
	
	public int getTutorPersonasCount(){
		throw new UnsupportedOperationException();
	}
	
	public TutorPersonaProperties getTutorPersonaProperties(int tutorPersona){
		throw new UnsupportedOperationException();
	}
}
