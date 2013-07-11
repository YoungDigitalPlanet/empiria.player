package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.module.tutor.ActionType;

public interface TutorConfig {

	boolean supportsAction(ActionType type);
	Iterable<TutorCommandConfig> getCommandsForAction(ActionType type);
	int getTutorPersonasCount();
	TutorPersonaProperties getTutorPersonaProperties(int tutorPersona);
}
