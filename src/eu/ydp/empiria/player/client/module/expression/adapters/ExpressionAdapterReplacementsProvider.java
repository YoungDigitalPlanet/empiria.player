package eu.ydp.empiria.player.client.module.expression.adapters;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_EXPRESSION_MAPPING;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.style.StyleSocket;

public class ExpressionAdapterReplacementsProvider {

	public static final String SELECTOR = ".qp-expression-mapping";

	private final StyleSocket styleSocket;
	
	private final ExpressionAdapterReplacementsParser parser;
	
	private Map<String, String> replacements;
	
	@Inject 
	public ExpressionAdapterReplacementsProvider(StyleSocket styleSocket, ExpressionAdapterReplacementsParser parser) {
		this.styleSocket = styleSocket;
		this.parser = parser;
	}

	public Map<String, String> getReplacements() {
		ensureReplacementsInitalized();
		return replacements;
	}

	private void ensureReplacementsInitalized() {
		if (replacements == null){
			Map<String, String> styles = styleSocket.getStyles(SELECTOR);
			if (styles.containsKey(EMPIRIA_EXPRESSION_MAPPING)){
				String replacementsRaw = styles.get(EMPIRIA_EXPRESSION_MAPPING);
				replacements = parser.parse(replacementsRaw);
			} else {
				replacements = Maps.newHashMap();
			}
		}
	}
	
}
