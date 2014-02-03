package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.List;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class AnswersChangesFormater {

	public static final String ADDED_ASWER_PREFIX = "+";
	public static final String REMOVED_ASWER_PREFIX = "-";

	public List<String> formatLastAnswerChanges(LastAnswersChanges answersChanges) {
		List<String> addedAnswers = answersChanges.getAddedAnswers();
		List<String> removedAnswers = answersChanges.getRemovedAnswers();

		List<String> formattedAddedAnswers = formatAnswersByAddingPrefix(addedAnswers, ADDED_ASWER_PREFIX);
		List<String> formattedRemovedAnswers = formatAnswersByAddingPrefix(removedAnswers, REMOVED_ASWER_PREFIX);

		List<String> formattedAnswers = Lists.newArrayList();
		formattedAnswers.addAll(formattedAddedAnswers);
		formattedAnswers.addAll(formattedRemovedAnswers);

		return formattedAnswers;
	}

	private List<String> formatAnswersByAddingPrefix(List<String> answers, String prefix) {
		List<String> formattedAnswers = Lists.newArrayList();
		for (String answer : answers) {
			String formattedAnswer = prefix + answer;
			formattedAnswers.add(formattedAnswer);
		}
		return formattedAnswers;
	}

}
