package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.counting.CorrectAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.counting.ErrorAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.LastAnswersChanges;

public class SingleModeVariableProcessor implements VariableProcessor {

	private final ErrorAnswersCounter errorAnswersCounter;
	private final CorrectAnswersCounter correctAnswersCounter;
	private final LastGivenAnswersChecker lastGivenAnswersChecker;
	
	@Inject
	public SingleModeVariableProcessor(ErrorAnswersCounter errorAnswersCounter, CorrectAnswersCounter correctAnswersCounter, LastGivenAnswersChecker lastGivenAnswersChecker) {
		this.errorAnswersCounter = errorAnswersCounter;
		this.correctAnswersCounter = correctAnswersCounter;
		this.lastGivenAnswersChecker = lastGivenAnswersChecker;
	}

	@Override
	public int calculateErrors(Response response) {
		int errors = errorAnswersCounter.countErrorsForNotOrderedAnswersInResponse(response);
		return errors;
	}

	@Override
	public int calculateDone(Response response) {
		int correctAnswers = correctAnswersCounter.countCorrectForNotOrderedAnswers(response);
		return correctAnswers;
	}

	@Override
	public boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
		boolean anyAddedAnswerNotCorrect = lastGivenAnswersChecker.isAnyAddedAnswerNotCorrect(answersChanges.getAddedAnswers(), response.correctAnswers);
		boolean anyRemovedAnswerCorrect = lastGivenAnswersChecker.isAnyRemovedAnswerCorrect(answersChanges.getRemovedAnswers(), response.correctAnswers);
		
		boolean lastmistaken = false;
		if(anyAddedAnswerNotCorrect || anyRemovedAnswerCorrect){
			lastmistaken = true;
		}
		return lastmistaken;
	}

	@Override
	public int calculateMistakes(boolean lastmistaken, int previousMistakes) {
		if(lastmistaken){
			return previousMistakes+1;
		}else{
			return previousMistakes;
		}
	}

}
