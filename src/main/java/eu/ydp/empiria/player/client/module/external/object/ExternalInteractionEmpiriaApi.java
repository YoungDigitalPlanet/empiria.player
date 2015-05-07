package eu.ydp.empiria.player.client.module.external.object;

import com.google.gwt.core.client.js.JsType;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.ExternalInteractionResponseModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

@JsType
public class ExternalInteractionEmpiriaApi {

	@Inject
	@ModuleScoped
	private ExternalInteractionResponseModel responseModel;

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
}
