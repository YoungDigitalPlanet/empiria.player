package eu.ydp.empiria.player.client.module.external.sound;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.ExternalInteractionPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionSoundApi {

	@Inject
	private MediaWrapperSoundActions soundActions;
	@Inject
	private ExternalInteractionSoundWrappers wrappers;
	@Inject
	@ModuleScoped
	private ExternalInteractionPaths paths;

	public void play(final String src) {
		String filePath = paths.getExternalFilePath(src);
		wrappers.execute(filePath, soundActions.getPlayAction());
	}

	public void playLooped(String src) {
		String filePath = paths.getExternalFilePath(src);
		wrappers.execute(filePath, soundActions.getPlayLoopedAction());
	}

	public void stop(String src) {
		String filePath = paths.getExternalFilePath(src);
		wrappers.execute(filePath, soundActions.getStopAction());
	}

	public void pause(String src) {
		String filePath = paths.getExternalFilePath(src);
		wrappers.execute(filePath, soundActions.getPauseAction());
	}

	public void resume(String src) {
		String filePath = paths.getExternalFilePath(src);
		wrappers.execute(filePath, soundActions.getResumeAction());
	}
}
