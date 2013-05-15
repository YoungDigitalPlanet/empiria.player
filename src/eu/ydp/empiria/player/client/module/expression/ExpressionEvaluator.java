package eu.ydp.empiria.player.client.module.expression;

import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.debug.gwtlogger.ILogger;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class ExpressionEvaluator {

	@Inject
	SymjaExpressionAdapter symjaExpressionAdapter;
		
	ILogger log = new Logger();
	
	public boolean evaluate(String expression) {
		expression = symjaExpressionAdapter.process(expression);
		boolean result;
		try {
			result = processLogicalExpressionEvaluator(expression);
		} catch (ArithmeticException e) {
			result = false;
		} catch (SyntaxError e) {
			result = false;			
		} catch (Exception e) {			
			result = false;
			log.severe("ExpressionEvaluator problem while evaluate: " + expression + " " + e.toString() + " " + e.toString());
		}
		 
		return result;
	}
	
	private boolean processLogicalExpressionEvaluator(String expression) {
		Parser p = new Parser();
		ASTNode obj = p.parse(expression);
		DoubleEvaluator engine = new DoubleEvaluator();
		return engine.evaluateNodeLogical(obj);
	}	
}
