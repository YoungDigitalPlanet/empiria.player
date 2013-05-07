package eu.ydp.empiria.player.client.module.expression;

import java.util.List;

import com.google.gwt.core.shared.GWT;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.expression.IdentifiersFromExpressionExtractor;


public class IdentifiersFromExpressionTemplateExtractorGWTTest extends AbstractEmpiriaPlayerGWTTestCase{

	public void testExtractIdentifiersFromTemplate() throws Exception {
		String template = "'resp1'-'resp2' + 23 * 63 / 10 + sqrt('R4')= 'resp3'";
		testExtractIdentifiersFromExpression(template, "resp1", "resp2", "resp3", "R4");
	}
	
	private void testExtractIdentifiersFromExpression(String expression, String ... expectedIdentifiers){
		IdentifiersFromExpressionExtractor extractor = GWT.create(IdentifiersFromExpressionExtractor.class);

		List<String> identifiers = extractor.extractResponseIdentifiersFromTemplate(expression);

		assertEquals(expectedIdentifiers.length, identifiers.size());
		for (String expectedIdentifier : expectedIdentifiers) {
			assertTrue(identifiers.contains(expectedIdentifier));
		}
	}
	
}
