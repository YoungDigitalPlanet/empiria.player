package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.CorrectAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

public class MultipleModeVariableProcessor implements VariableProcessor {

	private final ErrorAnswersCounter errorAnswersCounter;
	private final CorrectAnswersCounter correctAnswersCounter;
	private final LastGivenAnswersChecker lastGivenAnswersChecker;

	@Inject
	public MultipleModeVariableProcessor(ErrorAnswersCounter errorAnswersCounter, CorrectAnswersCounter correctAnswersCounter,
			LastGivenAnswersChecker lastGivenAnswersChecker) {
		this.errorAnswersCounter = errorAnswersCounter;
		this.correctAnswersCounter = correctAnswersCounter;
		this.lastGivenAnswersChecker = lastGivenAnswersChecker;
	}

	@Override
	public int calculateErrors(Response response) {
		int errors = errorAnswersCounter.countErrorAnswersAdjustedToMode(response);
		return errors;
	}

	@Override
	public int calculateDone(Response response) {
		int correctAnswers = correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response);
		return correctAnswers;
	}

	@Override
	public boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
		boolean anyAddedAnswerNotCorrect = lastGivenAnswersChecker.isAnyAddedAnswerNotCorrect(answersChanges.getAddedAnswers(), response.correctAnswers);
		boolean anyRemovedAnswerCorrect = lastGivenAnswersChecker.isAnyRemovedAnswerCorrect(answersChanges.getRemovedAnswers(), response.correctAnswers);

		boolean lastmistaken = false;
		if (anyAddedAnswerNotCorrect || anyRemovedAnswerCorrect) {
			lastmistaken = true;
		}
		return lastmistaken;
	}

	@Override
	public int calculateMistakes(boolean lastmistaken, int previousMistakes) {
		if (lastmistaken) {
			return previousMistakes + 1;
		} else {
			return previousMistakes;
		}
	}

	@Override
	public List<Boolean> evaluateAnswers(Response response) {
		Evaluate evaluate = response.evaluate;
		List<Boolean> answersEvaluation;
		
		if(evaluate == Evaluate.CORRECT){
			answersEvaluation = evaluateCorrectAnswers(response.values, response.correctAnswers);
		}else if( (evaluate == Evaluate.USER) || (evaluate == Evaluate.DEFAULT) ){
			answersEvaluation = evaluateUserAnswers(response.values, response.correctAnswers);
		}else{
			throw new RuntimeException("Unsupported answers evaluation mode: " + evaluate);
		}
		
		return answersEvaluation;
	}

	private List<Boolean> evaluateUserAnswers(List<String> userAnswers, CorrectAnswers correctAnswers) {
		List<Boolean> userAnswersEvaluation = Lists.newArrayList();
		for (String userAnswer : userAnswers) {
			boolean isCorrectAnswer = correctAnswers.containsAnswer(userAnswer);
			userAnswersEvaluation.add(isCorrectAnswer);
		}
		return userAnswersEvaluation;
	}
	
	private List<Boolean> evaluateCorrectAnswers(List<String> userAnswers, CorrectAnswers correctAnswers) {
		List<Boolean> correctAnswersEvaluation = Lists.newArrayList();
		for (ResponseValue correctAnswerResponseValue : correctAnswers.getAllResponseValues()) {
			boolean isCorrectAnswer = checkIfUserGaveAnswerFittingToResponseValue(correctAnswerResponseValue, userAnswers);
			correctAnswersEvaluation.add(isCorrectAnswer);
		}
		return correctAnswersEvaluation;
	}

	private boolean checkIfUserGaveAnswerFittingToResponseValue(ResponseValue responseValue, Iterable<String> userAnswers) {
		List<String> responseAnswers = responseValue.getAnswers();
		boolean isAnyUserAnswerFitting = CollectionsUtil.containsAnyOfElements(userAnswers, responseAnswers);
		return isAnyUserAnswerFitting;
	}
}
