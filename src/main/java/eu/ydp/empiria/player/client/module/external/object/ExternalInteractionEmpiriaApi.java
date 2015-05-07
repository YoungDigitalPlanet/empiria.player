package eu.ydp.empiria.player.client.module.external.object;

import com.google.gwt.core.client.js.JsType;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.sound.ExternalInteractionSoundApi;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

@JsType
public class ExternalInteractionEmpiriaApi {

	@Inject
	@ModuleScoped
	private ExternalInteractionResponseModel responseModel;
	@Inject
	@ModuleScoped
	private ExternalInteractionSoundApi soundApi;

	public void onResultChange(ExternalInteractionStatus status) {
		int done = status.getDone();
		int errors = status.getErrors();
		responseModel.clearAnswers();

		for (int i = 1; i <= done; i++) {
			responseModel.addAnswer(String.valueOf(i));
		}
		for (int i = 1; i <= errors; i++) {
			responseModel.addAnswer(String.valueOf(-i));
		}
	}

	public void play(String src) {
		soundApi.play(src);
	}

	public void playLooped(String src) {
		soundApi.playLooped(src);
	}

	public void pause(String src) {
		soundApi.pause(src);
	}

	public void resume(String src) {
		soundApi.resume(src);
	}

	public void stop(String src) {
		soundApi.stop(src);
	}
}
