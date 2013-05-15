package eu.ydp.empiria.player.client.module.expression.evaluate;

import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.ExpressionCreator;
import eu.ydp.empiria.player.client.module.expression.SymjaExpressionAdapter;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionEvaluator implements Evaluator {

	@Inject
	private ExpressionCreator expressionCreator;
	
	@Inject
	private SymjaExpressionAdapter symjaExpressionAdapter;
	
	public boolean evaluate(ExpressionBean expressionBean) {
		String expression = prepareExpression(expressionBean);
		String fixedExpression = symjaExpressionAdapter.process(expression);
		boolean result = processEvaluation(fixedExpression);
		return result;
	}

	private boolean processEvaluation(String fixedExpression) {
		boolean result;
		try {
			result = processLogicalExpressionEvaluator(fixedExpression);
		} catch (ArithmeticException e) {
			result = false;
		}
		return result;
	}
	
	private boolean processLogicalExpressionEvaluator(String expression) {
		Parser p = new Parser();
		ASTNode obj = p.parse(expression);
		DoubleEvaluator engine = new DoubleEvaluator();
		return engine.evaluateNodeLogical(obj);
	}	
	


	private String prepareExpression(ExpressionBean expressionBean) {
		return expressionCreator.getExpression(expressionBean);
	}
}
