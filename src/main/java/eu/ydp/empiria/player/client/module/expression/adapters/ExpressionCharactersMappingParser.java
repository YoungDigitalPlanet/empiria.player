package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;

import java.util.Map;

public class ExpressionCharactersMappingParser {

    @Inject
    private PipedReplacementsParser expressionReplacementsParser;

    public Map<String, String> parse(String raw) {
        Map<String, String> parts = expressionReplacementsParser.parse(raw);
        return findReplacements(parts);
    }

    private Map<String, String> findReplacements(Map<String, String> parts) {
        Map<String, String> replacements = Maps.newHashMap();
        for (Map.Entry<String, String> part : parts.entrySet()) {
            Map<String, String> mappings = findMappings(part.getKey(), part.getValue());
            replacements.putAll(mappings);
        }
        return replacements;
    }

    private Map<String, String> findMappings(String fromChars, String toChar) {
        Map<String, String> mappings = Maps.newHashMap();
        Iterable<String> fromSplitted = Splitter.on(",").split(fromChars);
        for (String from : fromSplitted) {
            mappings.put(from, toChar);
        }
        return mappings;
    }
}
