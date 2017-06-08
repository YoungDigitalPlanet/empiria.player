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

package eu.ydp.empiria.player.client.controller.variables.objects;

import com.google.gwt.json.client.JSONValue;
import eu.ydp.gwtutil.client.regex.RegexMatcher;

import java.util.List;
import java.util.Vector;

public abstract class Variable {

    protected RegexMatcher regexMatcher = new RegexMatcher();

    public Variable() {
        values = new Vector<String>();
        identifier = "";
        cardinality = Cardinality.SINGLE;
    }

    public String identifier;

    public Cardinality cardinality;

    public List<String> values;

    public void reset() {
        values.clear();
    }

    public String getValuesShort() {

        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < values.size(); i++) {
            output.append(escapeCSV(values.get(i)));
            if (i < values.size() - 1) {
                output.append(";");
            }
        }
        return output.toString();
    }

    protected String escapeCSV(String value) {
        if (value.contains("'") || value.contains(";")) {
            value = value.replace("'", "''");
            value = "'" + value + "'";
        }
        return value;
    }

    public boolean compareValues(String[] testValues) {
        Vector<String> vec = new Vector<String>();
        for (int i = 0; i < testValues.length; i++) {
            vec.add(testValues[i]);
        }
        return compareValues(vec);
    }

    public boolean compareValues(Vector<String> testValues) {

        if (values.size() != testValues.size()) {
            return false;
        }

        boolean contains;

        for (String ref : values) {
            contains = false;
            for (String test : testValues) {
                if (ref.compareTo(test) == 0) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    public boolean matchFirstValue(String[] testValues) {
        String value = this.values.get(0);
        boolean result = false;
        for (String testValue : testValues) {
            if (regexMatcher.matches(value, "^(" + testValue + ")$")) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Variable [regexMatcher=").append(regexMatcher).append(", identifier=").append(identifier).append(", cardinality=").append(cardinality)
                .append(", values=").append(values).append("]");
        return builder.toString();
    }

    public abstract JSONValue toJSON();

    public abstract void fromJSON(JSONValue value);
}
