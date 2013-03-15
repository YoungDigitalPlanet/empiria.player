package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

public class ResponseBuilder {

	private Evaluate evaluate = Evaluate.USER;
	private CorrectAnswers correctAnswers = new CorrectAnswers();
	private List<String> values = Lists.newArrayList();
	private List<String> groups = Lists.newArrayList();
	private String identifier = "defaultIdentifier";
	private BaseType baseType = BaseType.STRING;
	private Cardinality cardinality;

	public ResponseBuilder withEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
		return this;
	}

	public ResponseBuilder withCorrectAnswers(CorrectAnswers correctAnswers) {
		this.correctAnswers = new CorrectAnswers();
		return this;
	}

	public ResponseBuilder withValues(List<String> values) {
		this.values = values;
		return this;
	}

	public ResponseBuilder withGroups(List<String> groups) {
		this.groups = groups;
		return this;
	}

	public ResponseBuilder withIdentifier(String identifier) {
		this.identifier = identifier;
		return this;
	}

	public ResponseBuilder withBaseType(BaseType baseType) {
		this.baseType = baseType;
		return this;
	}

	public ResponseBuilder withCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
		return this;
	}

	public ResponseBuilder withCorrectAnswers(String... correctAnswers) {
		CorrectAnswers correctAnswersHolder = new CorrectAnswers();
		for (String correctAnswer : correctAnswers) {
			ResponseValue responseValue = new ResponseValue(correctAnswer);
			correctAnswersHolder.add(responseValue);
		}
		this.correctAnswers = correctAnswersHolder;
		return this;
	}

	public ResponseBuilder withCurrentUserAnswers(String... currentUserAnswers) {
		this.values = Arrays.asList(currentUserAnswers);
		return this;
	}

	public ResponseBuilder withGroups(String... groups) {
		this.groups = Arrays.asList(groups);
		return this;
	}

	public Response build() {
		return new Response(correctAnswers, values, groups, identifier, evaluate, baseType, cardinality);
	}
}
