package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

public class MultipleModeAnswersEvaluator {

	private static final Logger LOGGER = Logger.getLogger(MultipleModeAnswersEvaluator.class.getName());
	
	public List<Boolean> evaluateAnswers(Response response) {
		Evaluate evaluate = response.evaluate;
		List<Boolean> answersEvaluation;
		
		if(evaluate == Evaluate.CORRECT){
			answersEvaluation = evaluateCorrectAnswers(response.values, response.correctAnswers);
		}else if( (evaluate == Evaluate.USER) || (evaluate == Evaluate.DEFAULT) ){
			answersEvaluation = evaluateUserAnswers(response.values, response.correctAnswers);
		}else{
			String message = "Unsupported answers evaluation mode: " + evaluate+", "+Evaluate.DEFAULT+" will be used!";
			LOGGER.warning(message);
			answersEvaluation = evaluateUserAnswers(response.values, response.correctAnswers);
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
		for (ResponseValue correctAnswerResponseValue : correctAnswers.getAllResponsValues()) {
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
