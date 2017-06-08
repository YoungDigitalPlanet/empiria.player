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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.gwt.core.client.GWT;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

public class ResponseNodeParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ResponseNodeParser responseNodeParser;

    public void testParseResponseFromNodeTest() {
        ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory = GWT.create(ResponseNodeJAXBParserFactory.class);

        responseNodeParser = new ResponseNodeParser(responseNodeJAXBParserFactory, new ResponseBeanConverter());

        Response response = responseNodeParser.parseResponseFromNode(getResponseDeclaration());

        assertEquals(response.cardinality, Cardinality.MULTIPLE);
        assertEquals(response.identifier, "CONNECTION_RESPONSE_1");
        assertEquals(response.evaluate, Evaluate.CORRECT);
        assertEquals(response.cardinality, Cardinality.MULTIPLE);
        assertEquals(response.getCheckMode(), CheckMode.EXPRESSION);
        assertEquals(3, response.correctAnswers.getAllAnswers().size());
        assertEquals(response.correctAnswers.getAllAnswers().get(0), "CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
        assertEquals(response.correctAnswers.getAllAnswers().get(1), "CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_2");
        assertEquals(response.correctAnswers.getAllAnswers().get(2), "CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");
        assertEquals(response.getID(), "CONNECTION_RESPONSE_1"); // checkMode
        assertEquals(response.getAppropriateCountMode(), CountMode.SINGLE);
        assertEquals(response.groups.get(0), "x1");
        assertEquals(response.groups.get(1), "x2");
    }

    private String getResponseDeclaration() {
        StringBuilder builder = new StringBuilder();
        builder.append("<responseDeclaration cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\" checkMode=\"expression\" evaluate =\"correct\">");
        builder.append("		<correctResponse>");
        builder.append("			<value forIndex=\"0\"  group=\"x1\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
        builder.append("			<value forIndex=\"0\"  group=\"x2\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_2</value>");
        builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
        builder.append("		</correctResponse>");
        builder.append("</responseDeclaration>");

        return builder.toString();
    }
}
