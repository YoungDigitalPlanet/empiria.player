package eu.ydp.empiria.player.client.module.expression;


public class SymjaExpressionAdapter {

	public String process(String expression) {
		expression = fixOperators(expression);
		expression = fixDivide(expression);
		expression = fixComma(expression);
		
		return expression;
	}

	private String fixDivide(String expression) {
		expression = expression.replaceFirst(":", "/");
		return expression;
	}

	private String fixComma(String expression) {
		expression = expression.replaceFirst(",", ".");
		return expression;
	}

	private String fixOperators(String expression) {
		if (!expression.matches("^.*?(>=|=>|<=|=<).*$")) {
			expression = expression.replaceFirst("=", "==");
		}
		return expression;
	}
	
}
