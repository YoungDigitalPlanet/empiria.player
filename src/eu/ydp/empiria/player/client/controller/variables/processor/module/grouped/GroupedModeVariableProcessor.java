package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.DoneToCountModeAdjuster;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorsToCountModeAdjuster;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class GroupedModeVariableProcessor implements VariableProcessor {

	private GroupedAnswersManager groupedAnswersManager;
	private ErrorsToCountModeAdjuster errorsToCountModeAdjuster;
	private DoneToCountModeAdjuster doneToCountModeAdjuster;

	@Inject
	public GroupedModeVariableProcessor(
			@PageScoped GroupedAnswersManager groupedAnswersManager, 
			ErrorsToCountModeAdjuster errorsToCountModeAdjuster,
			DoneToCountModeAdjuster doneToCountModeAdjuster) {
		this.groupedAnswersManager = groupedAnswersManager;
		this.errorsToCountModeAdjuster = errorsToCountModeAdjuster;
		this.doneToCountModeAdjuster = doneToCountModeAdjuster;
	}

	@Override
	public int calculateErrors(Response response) {
		List<String> currentAnswers = response.values;
		List<String> groups = response.groups;
		CountMode countMode = response.getCountMode();

		int errors = countErrorsInResponse(response, currentAnswers, groups);
		return errorsToCountModeAdjuster.adjustValueToCountMode(errors, countMode);
	}

	private int countErrorsInResponse(Response response, List<String> currentAnswers, List<String> groups) {
		int amountOfCorrectAnswers = countAmountOfCorrectAnswers(response, currentAnswers, groups);
		int errors = currentAnswers.size() - amountOfCorrectAnswers;
		return errors;
	}

	@Override
	public int calculateDone(Response response) {
		List<String> currentAnswers = response.values;
		List<String> groups = response.groups;
		CountMode countMode = response.getCountMode();

		int amountOfCorrectAnswers = countAmountOfCorrectAnswers(response, currentAnswers, groups);
		return doneToCountModeAdjuster.adjustValueToCountMode(amountOfCorrectAnswers, response.correctAnswers, countMode);
	}

	private int countAmountOfCorrectAnswers(Response response, List<String> currentAnswers, List<String> groups) {
		int amountOfCorrectAnswers = 0;

		for (String currentAnswer : currentAnswers) {
			boolean answerCorrect = groupedAnswersManager.isAnswerCorrectInAnyOfGroups(currentAnswer, response, groups);
			if (answerCorrect) {
				amountOfCorrectAnswers++;
			}
		}
		return amountOfCorrectAnswers;
	}

	@Override
	public boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
		List<String> addedAnswers = answersChanges.getAddedAnswers();

		boolean addedCorrectAnswer = hasAddedAnyCorrectAnswer(addedAnswers, response);
		boolean lastmistaken; 
		
		if(addedCorrectAnswer){
			lastmistaken = false;
		}else{
			lastmistaken = true;
		}
		return lastmistaken;
	}

	private boolean hasAddedAnyCorrectAnswer(List<String> addedAnswers, Response response) {
		int correctAnswers = countAmountOfCorrectAnswers(response, addedAnswers, response.groups);
		boolean containsCorrectAnswers = correctAnswers > 0;
		return containsCorrectAnswers;
	}

	@Override
	public int calculateMistakes(boolean lastmistaken, int previousMistakes) {
		int newMistakes = previousMistakes;
		if (lastmistaken) {
			newMistakes = previousMistakes + 1;
		}
		return newMistakes;
	}

	@Override
	public List<Boolean> evaluateAnswers(Response response) {
		Evaluate evaluate = response.evaluate;
		if (evaluate != Evaluate.USER) {
			throw new UnsupportedOperationException("Cannot evaluate answers in mode: " + evaluate
					+ " for grouped answers. Only allowed evaluation type for grouped answers is: " + Evaluate.USER);
		}
		
		return evaluateUserAnswers(response);
	}

	private List<Boolean> evaluateUserAnswers(Response response) {
		List<Boolean> answersEvaluation = Lists.newArrayList();
		for(String currentAnswer : response.values){
			boolean answerCorrect = groupedAnswersManager.isAnswerCorrectInAnyOfGroups(currentAnswer, response, response.groups);
			answersEvaluation.add(answerCorrect);
		}
		return answersEvaluation;
	}

}
