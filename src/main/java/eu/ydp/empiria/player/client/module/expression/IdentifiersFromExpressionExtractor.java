package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.Lists;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import java.util.List;

public class IdentifiersFromExpressionExtractor {

    private static final String REGEX = "'([^'])*'";
    private static final RegExp PATTERN = RegExp.compile(REGEX, "g");

    public List<String> extractResponseIdentifiersFromTemplate(String template) {
        MatchResult matchResult = PATTERN.exec(template);

        List<String> identifiers = Lists.newArrayList();
        while (matchResult != null) {
            String group = matchResult.getGroup(0);
            String identifier = group.substring(1, group.length() - 1);
            identifiers.add(identifier);

            matchResult = PATTERN.exec(template);
        }

        return identifiers;
    }

}
