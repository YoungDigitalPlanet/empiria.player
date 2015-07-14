package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.inject.Inject;

import java.util.Map;

public class DefaultExpressionCharactersAdapter {

    @Inject
    private ExpressionCharacterMappingProvider replacementsProvider;

    public String process(String expression) {
        expression = fixDivide(expression);
        expression = fixComma(expression);
        expression = fixReplacements(expression);
        return expression;
    }

    private String fixReplacements(String expression) {
        Map<String, String> replacements = replacementsProvider.getMapping();
        for (String from : replacements.keySet()) {
            String to = replacements.get(from);
            expression = expression.replace(from, to);
        }
        return expression;
    }

    private String fixDivide(String expression) {
        expression = expression.replaceAll(":", "/");
        return expression;
    }

    private String fixComma(String expression) {
        expression = expression.replaceAll(",", ".");
        return expression;
    }

}
