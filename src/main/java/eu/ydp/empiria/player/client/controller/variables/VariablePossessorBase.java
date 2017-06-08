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

package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class VariablePossessorBase<V extends Variable> extends VariableProviderBase {

    protected Map<String, V> variables;

    public VariablePossessorBase() {
        variables = new TreeMap<>();
    }

    public V getVariable(String identifier) {
        return variables.get(identifier);
    }

    public void reset() {
        for (Variable currVariable : variables.values()) {
            currVariable.reset();
        }
    }

    @Override
    public Set<String> getVariableIdentifiers() {
        return variables.keySet();
    }

    @Override
    public Variable getVariableValue(String identifier) {
        return variables.get(identifier);
    }

    public boolean isLastAnswerSelectAction() {
        Set<String> identifiers = getVariableIdentifiers();
        for (String variableName : identifiers) {
            if (checkVariable(variableName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVariable(String variableName) {
        String lastchange = VariableName.LASTCHANGE.toString();
        if (variableName.endsWith(lastchange)) {
            V variable = variables.get(variableName);
            if (hasSelectChange(variable)) {
                return true;
            }

        }
        return false;
    }

    private boolean hasSelectChange(V variable) {
        for (String answer : variable.values) {
            if (!answer.equals("+") && answer.startsWith("+")) {
                return true;
            }
        }
        return false;
    }
}
