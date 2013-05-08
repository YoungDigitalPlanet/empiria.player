package eu.ydp.empiria.player.client.module.expression;

import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.VALUES_NOT_SET;
import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.CORRECT;
import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.WRONG;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

public class ExpressionEvaluationController {

	@Inject
	private ExpressionValidator expressionValidator;
	
	@Inject
	private ExpressionCreator expressionCreator;
	
	@Inject
	private ExpressionEvaluator expressionEvaluator;
	
	
	public ExpressionEvaluationResult evaluateExpression(ExpressionBean expressionBean){
		
		ExpressionEvaluationResult evaluationResult = VALUES_NOT_SET;
		
		boolean allResponsesAreNotEmpty = validateExpressionAgainstNotEmpty(expressionBean);		
		if (allResponsesAreNotEmpty) {
			String expression = prepareExpression(expressionBean);
			evaluationResult = checkExpression(expression);
		}
		return evaluationResult;
	}

	private ExpressionEvaluationResult checkExpression(String expression) {
		boolean checkResult = expressionEvaluator.evaluate(expression);
		return (checkResult) ? CORRECT : WRONG;
	}

	private boolean validateExpressionAgainstNotEmpty(ExpressionBean expressionBean) {
		return expressionValidator.isAllResponsesAreNotEmpty(expressionBean);
	}

	private String prepareExpression(ExpressionBean expressionBean) {
		expressionCreator = new ExpressionCreator();
		return expressionCreator.getExpression(expressionBean);
	}	
}
