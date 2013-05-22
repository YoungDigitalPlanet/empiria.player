package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseBeanConverter {

	public Response convert(ResponseBean responseBean) {
		ResponseBuilder builder = new ResponseBuilder();
		builder.withCardinality(responseBean.getCardinality());
		builder.withCheckMode(responseBean.getCheckMode());
		builder.withEvaluate(responseBean.getEvaluate());
		builder.withIdentifier(responseBean.getIdentifier());

		CorrectAnswers correctAnswers = getCorrectAnswers(responseBean);
		List<String> groups = getGroups(responseBean);

		builder.withCorrectAnswers(correctAnswers);

		builder.withGroups(groups);

		return builder.build();
	}

	private List<String> getGroups(ResponseBean responseBean) {
		List<String> groups = new ArrayList<String>();
		for (ValueBean valueBean : responseBean.getCorrectResponse().getValues()) {

			if (valueBean.getGroup() != null) {
				groups.add(valueBean.getGroup());
			}
		}
		return groups;
	}

	private CorrectAnswers getCorrectAnswers(ResponseBean responseBean) {
		CorrectAnswers correctAnswers = new CorrectAnswers();

		for (ValueBean valueBean : responseBean.getCorrectResponse().getValues()) {

			correctAnswers.add(new ResponseValue(valueBean.getValue()));
		}
		return correctAnswers;
	}
}
