package eu.ydp.empiria.player.client.module.external.interaction.api;

import com.google.gwt.core.client.js.JsType;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

@JsType
public class ExternalInteractionEmpiriaApi extends ExternalEmpiriaApi {

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
