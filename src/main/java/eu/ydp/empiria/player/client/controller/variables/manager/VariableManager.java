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

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableManager<V extends Variable> extends VariablePossessorBase<V> {

    public VariableManager(NodeList responseDeclarationNodes, IVariableCreator<V> variableCreator) {
        Node node;
        String currIdentifier;

        if (responseDeclarationNodes != null) {
            for (int i = 0; i < responseDeclarationNodes.getLength(); i++) {
                node = responseDeclarationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    currIdentifier = node.getAttributes().getNamedItem("identifier").getNodeValue();
                    V var = variableCreator.createVariable(node);
                    if (var != null) {
                        variables.put(currIdentifier, var);
                    }
                }
            }
        }
    }

}
