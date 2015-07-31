package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.core.shared.GWT;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

import java.util.List;

public class IdentifiersFromExpressionTemplateExtractorGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testExtractIdentifiersFromTemplate() throws Exception {
        String template = "'resp1'-'resp2' + 23 * 63 / 10 + sqrt('R4')= 'resp3'";
        testExtractIdentifiersFromExpression(template, "resp1", "resp2", "resp3", "R4");
    }

    private void testExtractIdentifiersFromExpression(String expression, String... expectedIdentifiers) {
        IdentifiersFromExpressionExtractor extractor = GWT.create(IdentifiersFromExpressionExtractor.class);

        List<String> identifiers = extractor.extractResponseIdentifiersFromTemplate(expression);

        assertEquals(expectedIdentifiers.length, identifiers.size());
        for (String expectedIdentifier : expectedIdentifiers) {
            assertTrue(identifiers.contains(expectedIdentifier));
        }
    }

}
