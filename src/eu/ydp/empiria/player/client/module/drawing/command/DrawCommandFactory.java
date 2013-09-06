package eu.ydp.empiria.player.client.module.drawing.command;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawCommandFactory {
	@Inject @ModuleScoped private Provider<ClearAllDrawCommand> clearAllComand;

	public DrawCommand createCommand(DrawCommandType type) {
		if(type == DrawCommandType.CLEAR_ALL) {
			return clearAllComand.get();
		}
		return null;
	}
}
