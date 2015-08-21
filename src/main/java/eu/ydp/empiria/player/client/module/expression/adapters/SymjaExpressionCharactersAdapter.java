package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.inject.Inject;

public class SymjaExpressionCharactersAdapter {

    private final DefaultExpressionCharactersAdapter defaultAdapter;

    @Inject
    public SymjaExpressionCharactersAdapter(DefaultExpressionCharactersAdapter defaultAdapter) {
        this.defaultAdapter = defaultAdapter;
    }

    public String process(String expression) {
        expression = fixOperators(expression);
        expression = defaultAdapter.process(expression);
        return expression;
    }

    private String fixOperators(String expression) {
        if (!containsGreaterLessThanOrEquals(expression)) {
            expression = expression.replaceFirst("=", "==");
        }
        return expression;
    }

    private boolean containsGreaterLessThanOrEquals(String expression) {
        return expression.matches("^.*?(>=|=>|<=|=<).*$");
    }

}
