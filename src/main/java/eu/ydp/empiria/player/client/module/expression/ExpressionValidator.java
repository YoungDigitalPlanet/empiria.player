package eu.ydp.empiria.player.client.module.expression;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

import java.util.List;

public class ExpressionValidator {

    public boolean isAllResponsesAreNotEmpty(ExpressionBean expressionBean) {
        List<Response> responses = expressionBean.getResponses();

        if (responses.isEmpty()) {
            return false;
        }

        for (Response response : responses) {
            if (hasEmptyValue(response)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasEmptyValue(Response response) {
        List<String> values = response.values;
        return (values == null || values.isEmpty() || values.get(0).isEmpty());
    }
}
