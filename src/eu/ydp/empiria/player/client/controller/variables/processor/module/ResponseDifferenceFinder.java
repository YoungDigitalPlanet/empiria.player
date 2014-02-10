package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class ResponseDifferenceFinder {

	public LastAnswersChanges findChangesOfAnswers(List<String> previousAnswers, List<String> currentAnswers) {
		List<String> addedAnswers = findAddedAnswers(previousAnswers, currentAnswers);
		List<String> removedAnswers = findRemovedAnswers(previousAnswers, currentAnswers);

		LastAnswersChanges answerChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
		return answerChanges;
	}

	private List<String> findAddedAnswers(List<String> previousAnswers, List<String> currentAnswers) {
		List<String> addedAnswers = Lists.newArrayList();
		for (String currentAnswer : currentAnswers) {
			if (isValidAnswer(currentAnswer) && isNewAnswer(previousAnswers, currentAnswer)) {
				addedAnswers.add(currentAnswer);
			}
		}
		return addedAnswers;
	}

	private List<String> findRemovedAnswers(List<String> previousAnswers, List<String> currentAnswers) {
		List<String> removedAnswers = Lists.newArrayList();
		for (String previousAnswer : previousAnswers) {
			if (isValidAnswer(previousAnswer) && wasRemoved(previousAnswer, currentAnswers)) {
				removedAnswers.add(previousAnswer);
			}
		}
		return removedAnswers;
	}

	private boolean wasRemoved(String previousAnswer, List<String> currentAnswers) {
		boolean isPresentInCurrentAnswers = currentAnswers.contains(previousAnswer);
		return !isPresentInCurrentAnswers;
	}

	private boolean isNewAnswer(List<String> previousAnswers, String currentAnswer) {
		return !previousAnswers.contains(currentAnswer);
	}

	private boolean isValidAnswer(String currentAnswer) {
		boolean isNotNullOrEmpty = !Strings.isNullOrEmpty(currentAnswer);
		return isNotNullOrEmpty;
	}

}
