package eu.ydp.empiria.player.client.module.expression;

import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.CORRECT;
import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.VALUES_NOT_SET;
import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.WRONG;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.evaluate.Evaluator;
import eu.ydp.empiria.player.client.module.expression.evaluate.EvaluatorFactory;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

public class ExpressionEvaluationController {

	@Inject
	private ExpressionValidator expressionValidator;
	
	@Inject
	private EvaluatorFactory factory;
	
	
	public ExpressionEvaluationResult evaluateExpression(ExpressionBean expressionBean){
		ExpressionEvaluationResult evaluationResult;
		boolean expressionValid = validateExpressionAgainstNotEmpty(expressionBean);
		if (expressionValid){
			evaluationResult = evaluate(expressionBean);
		} else {
			evaluationResult = VALUES_NOT_SET;
		}
		return evaluationResult;
	}
	
	private boolean validateExpressionAgainstNotEmpty(ExpressionBean expressionBean) {
		return expressionValidator.isAllResponsesAreNotEmpty(expressionBean);
	}	

	private ExpressionEvaluationResult evaluate(ExpressionBean expressionBean) {
		Evaluator evaluator = factory.createEvaluator(expressionBean.getMode());
		boolean expressionEvaluationResult = evaluator.evaluate(expressionBean);
		return (expressionEvaluationResult) ? CORRECT : WRONG;
	}
}
