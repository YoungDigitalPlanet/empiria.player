package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static eu.ydp.empiria.player.client.module.tutor.CommandType.valueOf;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;
import eu.ydp.empiria.player.client.module.tutor.CommandType;

public class TutorCommandConfig {
	
	public static TutorCommandConfig fromJs(TutorCommandJs commandJs) {
		CommandType type = valueOf(commandJs.getType().toString());
		String asset = commandJs.getAsset();
		return new TutorCommandConfig(asset, type);
	}

	private final String asset;
	private final CommandType type;

	public TutorCommandConfig(String asset, CommandType type) {
		this.asset = asset;
		this.type = type;
	}

	public CommandType getType(){
		return type;
	}
	
	public String getAsset(){
		return asset;
	}

}
