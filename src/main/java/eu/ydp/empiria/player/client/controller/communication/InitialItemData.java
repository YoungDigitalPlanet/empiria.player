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

package eu.ydp.empiria.player.client.controller.communication;

import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import java.util.HashMap;
import java.util.Map;

public class InitialItemData {

    protected Map<String, Outcome> outcomes;

    public InitialItemData(XmlData itemData) {

        NodeList outcomeDeclarationNodes = null;

        outcomes = new HashMap<String, Outcome>();

        if (itemData != null) {
            outcomeDeclarationNodes = itemData.getDocument().getElementsByTagName("outcomeDeclaration");

            Outcome currOutcome;
            for (int i = 0; i < outcomeDeclarationNodes.getLength(); i++) {
                currOutcome = new Outcome(outcomeDeclarationNodes.item(i));
                if (currOutcome != null) {
                    outcomes.put(currOutcome.identifier, currOutcome);
                }
            }
        }
    }

    public Map<String, Outcome> getOutcomes() {
        return outcomes;
    }

}
