package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class GeneralVariablesJUnitTest {

	@Test
	public void answersModification_collectionShouldBeCopied() {
		// given
		List<String> answers = Lists.newArrayList("a");
		List<Boolean> answersEvaluation = Lists.newArrayList();
		GeneralVariables gv = new GeneralVariables(answers, answersEvaluation, 0, 0);

		// when
		answers.set(0, "b");

		// then
		assertThat(gv.getAnswers().get(0)).isEqualTo("a");

	}

	@Test
	public void answersModification_setByMutator_collectionShouldBeCopied() {
		// given
		GeneralVariables gv = new GeneralVariables();
		List<String> answers = Lists.newArrayList("a");
		gv.setAnswers(answers);

		// when
		answers.set(0, "b");

		// then
		assertThat(gv.getAnswers().get(0)).isEqualTo("a");

	}

	@Test
	public void evaluationModification_collectionShouldBeCopied() {
		// given
		List<String> answers = Lists.newArrayList();
		List<Boolean> answersEvaluation = Lists.newArrayList(true);
		GeneralVariables gv = new GeneralVariables(answers, answersEvaluation, 0, 0);

		// when
		answersEvaluation.set(0, false);

		// then
		assertThat(gv.getAnswersEvaluation().get(0)).isTrue();

	}

	@Test
	public void evaluationModification_setByMutator_collectionShouldBeCopied() {
		// given
		GeneralVariables gv = new GeneralVariables();
		List<Boolean> answersEvaluation = Lists.newArrayList(true);
		gv.setAnswersEvaluation(answersEvaluation);
		
		// when
		answersEvaluation.set(0, false);

		// then
		assertThat(gv.getAnswersEvaluation().get(0)).isTrue();

	}

}
