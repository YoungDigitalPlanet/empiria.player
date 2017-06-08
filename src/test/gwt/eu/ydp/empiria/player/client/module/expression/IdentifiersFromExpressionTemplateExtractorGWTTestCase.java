/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
