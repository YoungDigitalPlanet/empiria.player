package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static eu.ydp.empiria.player.client.module.tutor.CommandType.valueOf;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;
import eu.ydp.empiria.player.client.module.tutor.CommandType;

public class TutorCommandConfig {

	private final TutorCommandJs commandJs;
	
	public TutorCommandConfig(TutorCommandJs commandJs) {
		this.commandJs = commandJs;
	}

	public CommandType getType(){
		return valueOf(commandJs.getType().toString());
	}
	
	public String getAsset(){
		return commandJs.getAsset();
	}
}
