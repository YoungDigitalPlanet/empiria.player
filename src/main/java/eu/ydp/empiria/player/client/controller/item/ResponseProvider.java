package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ResponseSocket;

import java.util.List;

public class ResponseProvider implements ResponseSocket {

    @Inject
    @PageScoped
    private AnswerEvaluationSupplier answerEvaluationProvider;

    @Inject
    @PageScoped
    private ItemResponseManager itemResponseManager;

    @Override
    public Response getResponse(String id) {
        return itemResponseManager.getVariable(id);
    }

    @Override
    public List<Boolean> evaluateResponse(Response response) {
        return answerEvaluationProvider.evaluateAnswer(response);
    }

}
