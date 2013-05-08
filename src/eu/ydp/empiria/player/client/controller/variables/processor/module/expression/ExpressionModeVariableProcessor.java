package eu.ydp.empiria.player.client.controller.variables.processor.module.expression;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.module.expression.ExpressionEvaluationController;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

public class ExpressionModeVariableProcessor implements VariableProcessor {

	private ExpressionEvaluationController expressionEvaluationController;
	
	@Inject
	public ExpressionModeVariableProcessor(
			ExpressionEvaluationController expressionEvaluationController) {
		this.expressionEvaluationController = expressionEvaluationController;
	}

	@Override
	public int calculateErrors(Response response) {
		ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);
		
		if(evaluationResult == ExpressionEvaluationResult.WRONG){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int calculateDone(Response response) {
		ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);
		
		if(evaluationResult == ExpressionEvaluationResult.CORRECT){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
		if(answersChanges.containChanges()){
			ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);
			if(evaluationResult == ExpressionEvaluationResult.WRONG){
				return true;
			}
		}
		
		return false;
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
		ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);
		
		List<Boolean> evaluation = Lists.newArrayList();
		if(evaluationResult == ExpressionEvaluationResult.CORRECT){
			evaluation.add(true);
		}else{
			evaluation.add(false);
		}
		return evaluation;
	}
	
	private ExpressionEvaluationResult calculateExpressionOfResponse(Response response) {
		ExpressionBean expression = response.getExpression();
		ExpressionEvaluationResult evaluationResult = expressionEvaluationController.evaluateExpression(expression);
		return evaluationResult;
	}

}
