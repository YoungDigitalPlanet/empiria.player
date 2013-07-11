package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.module.tutor.CommandType;

public interface TutorCommandConfig {

	CommandType getType();
	String getAsset();
}
