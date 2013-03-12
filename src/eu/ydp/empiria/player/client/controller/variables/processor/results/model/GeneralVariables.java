package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class GeneralVariables {

	private List<String> answers = Lists.newArrayList();
	private List<Boolean> answersEvaluation = Lists.newArrayList();
	private int errors = 0;
	private int done = 0;

	public GeneralVariables() {
	}

	public GeneralVariables(List<String> answers, List<Boolean> answersEvaluation, int errors, int done) {
		this.answers = answers;
		this.answersEvaluation = answersEvaluation;
		this.errors = errors;
		this.done = done;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public List<Boolean> getAnswersEvaluation() {
		return answersEvaluation;
	}

	public void setAnswersEvaluation(List<Boolean> answersEvaluation) {
		this.answersEvaluation = answersEvaluation;
	}
}
