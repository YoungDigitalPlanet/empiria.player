package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.style.StyleSocket;

import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_EXPRESSION_MAPPING;

public class ExpressionCharacterMappingProvider {

    public static final String SELECTOR = ".qp-expression-mapping";

    private final StyleSocket styleSocket;

    private final ExpressionCharactersMappingParser parser;

    private Map<String, String> mapping;

    @Inject
    public ExpressionCharacterMappingProvider(StyleSocket styleSocket, ExpressionCharactersMappingParser parser) {
        this.styleSocket = styleSocket;
        this.parser = parser;
    }

    public Map<String, String> getMapping() {
        ensureMappingInitalized();
        return mapping;
    }

    private void ensureMappingInitalized() {
        if (mapping == null) {
            Map<String, String> styles = styleSocket.getStyles(SELECTOR);
            if (styles.containsKey(EMPIRIA_EXPRESSION_MAPPING)) {
                String replacementsRaw = styles.get(EMPIRIA_EXPRESSION_MAPPING);
                mapping = parser.parse(replacementsRaw);
            } else {
                mapping = Maps.newHashMap();
            }
        }
    }

}
