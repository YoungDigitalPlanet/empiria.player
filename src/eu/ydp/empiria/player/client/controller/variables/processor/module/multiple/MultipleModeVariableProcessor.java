package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.CorrectAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class MultipleModeVariableProcessor implements VariableProcessor {

	private final ErrorAnswersCounter errorAnswersCounter;
	private final CorrectAnswersCounter correctAnswersCounter;
	private final LastGivenAnswersChecker lastGivenAnswersChecker;
	private final MultipleModeAnswersEvaluator multipleModeAnswersEvaluator;

	@Inject
	public MultipleModeVariableProcessor(ErrorAnswersCounter errorAnswersCounter, CorrectAnswersCounter correctAnswersCounter,
			LastGivenAnswersChecker lastGivenAnswersChecker, MultipleModeAnswersEvaluator multipleModeAnswersEvaluator) {
		this.errorAnswersCounter = errorAnswersCounter;
		this.correctAnswersCounter = correctAnswersCounter;
		this.lastGivenAnswersChecker = lastGivenAnswersChecker;
		this.multipleModeAnswersEvaluator = multipleModeAnswersEvaluator;
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
		boolean addedCorrectAnswer = lastGivenAnswersChecker.isAnyAnswerCorrect(answersChanges.getAddedAnswers(), response.correctAnswers);

		boolean lastmistaken = true;
		if(addedCorrectAnswer){
			lastmistaken = false;
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
		List<Boolean> answersEvaluation = multipleModeAnswersEvaluator.evaluateAnswers(response);
		return answersEvaluation;
	}
}
