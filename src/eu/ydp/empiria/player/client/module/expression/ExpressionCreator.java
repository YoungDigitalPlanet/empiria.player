package eu.ydp.empiria.player.client.module.expression;

import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionCreator {

	public String getExpression(ExpressionBean expressionBean) {
		String template = expressionBean.getTemplate();
		List<Response> responses = expressionBean.getResponses();

		for (Response response : responses) {
			String identifier = getIdentifier(response);
			String value = getValue(response);

			template = template.replace(identifier, value);
		}

		return template;
	}

	private String getIdentifier(Response response) {
		return "'" + response.getID() + "'";
	}

	private String getValue(Response response) {
		List<String> values = response.values;
		return values.get(0);
	}

}
