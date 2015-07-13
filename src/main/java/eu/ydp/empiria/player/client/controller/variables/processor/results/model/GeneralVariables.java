package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

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
        this.answers = copyOf(answers);
        this.answersEvaluation = copyOf(answersEvaluation);
        this.errors = errors;
        this.done = done;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = copyOf(answers);
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
        this.answersEvaluation = copyOf(answersEvaluation);
    }
}
