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

package eu.ydp.empiria.player.client.controller.variables.objects.outcome;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

import java.util.Vector;

public class Outcome extends Variable {

    public static final String OUTCOME = "O";
    public static final String OLD_OUTCOME = "Outcome";

    private String interpretation;
    private double normalMaximum;

    public Outcome() {
        super();
        this.interpretation = "";
        this.normalMaximum = 0.0;

    }

    public Outcome(String identifier, Cardinality cardinality) {
        super();
        this.identifier = identifier;
        this.cardinality = cardinality;
        this.interpretation = "";
        this.normalMaximum = 0.0d;
    }

    public Outcome(String identifier, Cardinality cardinality, String value0) {
        super();
        this.identifier = identifier;
        this.cardinality = cardinality;
        values.add(value0);
        this.interpretation = "";
        this.normalMaximum = 0.0d;
    }

    public Outcome(Node responseDeclarationNode) {

        values = new Vector<String>();

        identifier = ((Element) responseDeclarationNode).getAttribute("identifier");

        cardinality = Cardinality.fromString(((Element) responseDeclarationNode).getAttribute("cardinality"));

        interpretation = "";

        normalMaximum = 0.0d;

        NodeList defaultValueNodes = ((Element) responseDeclarationNode).getElementsByTagName("defaultValue");
        if (defaultValueNodes.getLength() > 0) {
            NodeList valueNodes = defaultValueNodes.item(0).getChildNodes();
            String value;
            for (int i = 0; i < valueNodes.getLength(); i++) {
                if (valueNodes.item(i) instanceof Element && "value".equals(((Element) valueNodes.item(i)).getNodeName()) && valueNodes.item(i).hasChildNodes()) {
                    value = valueNodes.item(i).getFirstChild().getNodeValue();
                    if (value != null) {
                        values.add(value);
                    }
                }

            }
        }
        if (values.size() == 0 && cardinality == Cardinality.SINGLE) {
            values.add("0");
        }

    }

    public boolean isNotEmpty() {
        return !(values.isEmpty() || values.get(0).equals("0"));
    }

    @Override
    public JSONValue toJSON() {
        JSONArray jsonArr = new JSONArray();
        jsonArr.set(0, new JSONString(OUTCOME));
        jsonArr.set(1, new JSONString(identifier));
        jsonArr.set(2, new JSONString(cardinality.toString()));
        jsonArr.set(3, new JSONString(""));

        JSONArray valuesArr = new JSONArray();
        for (int v = 0; v < values.size(); v++) {
            valuesArr.set(v, new JSONString(values.get(v)));
        }
        jsonArr.set(4, valuesArr);
        jsonArr.set(5, new JSONString(interpretation));
        jsonArr.set(6, new JSONNumber(normalMaximum));

        return jsonArr;
    }

    @Override
    public void fromJSON(JSONValue value) {
        JSONArray jsonArr = value.isArray();

        if (jsonArr != null) {
            identifier = jsonArr.get(1).isString().stringValue();
            cardinality = Cardinality.valueOf(jsonArr.get(2).isString().stringValue());

            JSONArray jsonValues = jsonArr.get(4).isArray();
            values.clear();
            if (jsonValues != null) {
                for (int i = 0; i < jsonValues.size(); i++) {
                    values.add(jsonValues.get(i).isString().stringValue());
                }
            }

            interpretation = jsonArr.get(5).isString().stringValue();
            normalMaximum = jsonArr.get(6).isNumber().doubleValue();
        }
    }

}
