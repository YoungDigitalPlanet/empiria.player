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

package eu.ydp.empiria.player.client.controller.variables.manager;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableManagerGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private class CustomVariable extends Variable {

        public CustomVariable() {
            super();
        }

        @Override
        public JSONValue toJSON() {
            return null;
        }

        @Override
        public void fromJSON(JSONValue value) {
        }

    }

    public void testXmlProcessing() {

        Document doc = XMLParser
                .parse("<nodes><responseDeclaration identifier='RESPONSE' cardinality='multiple' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration></nodes>");

        VariableManager<CustomVariable> vm = new VariableManager<CustomVariable>(doc.getDocumentElement().getChildNodes(),

                new IVariableCreator<CustomVariable>() {

                    @Override
                    public CustomVariable createVariable(Node node) {
                        assertTrue(node.getNodeType() == Node.ELEMENT_NODE);
                        Element el = (Element) node;
                        String identifier = el.getAttribute("identifier");
                        assertEquals("RESPONSE", identifier);
                        return new CustomVariable();
                    }
                }

        );

        assertTrue(vm.getVariableIdentifiers().size() == 1);
        assertTrue(vm.getVariable("RESPONSE") instanceof CustomVariable);
    }

}
