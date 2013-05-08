package eu.ydp.empiria.player.client.module.expression;

import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

import com.google.inject.Inject;

public class ExpressionEvaluator {

	@Inject
	SymjaExpressionAdapter symjaExpressionAdapter;
	
	public boolean evaluate(String expression) {
		expression = symjaExpressionAdapter.process(expression);
		return processLogicalExpressionEvaluator(expression);	
	}
	
	private boolean processLogicalExpressionEvaluator(String expression) {
		Parser p = new Parser();
		ASTNode obj = p.parse(expression);
		DoubleEvaluator engine = new DoubleEvaluator();
		return engine.evaluateNodeLogical(obj);
	}	
}
