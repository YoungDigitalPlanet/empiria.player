package eu.ydp.empiria.player.client.module.expression.adapters;


public class DefaultExpressionCharactersAdapter {

	public String process(String expression) {
		expression = fixDivide(expression);
		expression = fixComma(expression);
		
		return expression;
	}

	private String fixDivide(String expression) {
		expression = expression.replaceFirst(":", "/");
		return expression;
	}

	private String fixComma(String expression) {
		expression = expression.replaceAll(",", ".");
		return expression;
	}
	
}
