package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseBeanConverter {

	public Response convert(ResponseBean responseBean) {
		ResponseBuilder builder = new ResponseBuilder();
		builder.withBaseType(responseBean.getBaseType()).withCardinality(responseBean.getCardinality());
		builder.withCheckMode(responseBean.getCheckMode());
		builder.withEvaluate(responseBean.getEvaluate());
		builder.withIdentifier(responseBean.getIdentifier());

		CorrectAnswers correctAnswers = new CorrectAnswers();
		List<String> groups = new ArrayList<String>();

		for (ValueBean valueBean : responseBean.getCorrectResponse().getValues()) {

			correctAnswers.add(new ResponseValue(valueBean.getValue()));
			if (valueBean.getGroup() != null) {
				groups.add(valueBean.getGroup());
			}
		}

		builder.withCorrectAnswers(correctAnswers);

		builder.withGroups(groups);

		return builder.build();
	}
}
