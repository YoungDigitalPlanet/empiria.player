package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public class GeneralVariables {

	private List<String> answers;
	private List<Boolean> answersEvaluation;
	private int errors;
	private int done;

	public GeneralVariables() {
		answers = newArrayList();
		answersEvaluation = newArrayList();
	}

	public GeneralVariables(List<String> answers, List<Boolean> answersEvaluation, int errors, int done) {
		this.answers = unmodifiableList(answers);
		this.answersEvaluation = unmodifiableList(answersEvaluation);
		this.errors = errors;
		this.done = done;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = unmodifiableList(answers);
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
		this.answersEvaluation = unmodifiableList(answersEvaluation);
	}
}
