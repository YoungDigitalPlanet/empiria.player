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

import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

/**
 * inject in {@link ModuleScoped}
 */
public class Response extends Variable {

    public CorrectAnswers correctAnswers;
    public List<String> groups;
    public Evaluate evaluate;
    /**
     * Determines whether the module corresponding to the response variable exists in the document
     */
    private boolean initialized = false;

    /*
     * Two CountModes are stored for backward compatibility with xml content. CompilerCountMode is defined in newer content and cannot be overridden by
     * countMode defined in content CSS. When compilerCountMode is not defined in xml then countMode from CSS should be used, if defined. Otherwise default
     * value is CountMode.SINGLE.
     */
    private CountMode countMode = CountMode.SINGLE;
    private final CountMode compilerCountMode;

    private ExpressionBean expression;
    private CheckMode checkMode = CheckMode.DEFAULT;

    Response(CorrectAnswers correctAnswers, List<String> values, List<String> groups, String identifier, Evaluate evaluate, Cardinality cardinality,
             CountMode countMode, ExpressionBean expression, CheckMode checkMode, CountMode compilerCountMode) {
        this.correctAnswers = correctAnswers;
        this.values = values;
        this.groups = groups;
        this.identifier = identifier;
        this.evaluate = evaluate;
        this.cardinality = cardinality;
        this.countMode = countMode;
        this.expression = expression;
        this.checkMode = checkMode;
        this.compilerCountMode = compilerCountMode;
    }

    public String getID() {
        return identifier;
    }

    public boolean isCorrectAnswer(String key) {
        return correctAnswers.containsAnswer(key);
    }

    public String getCorrectAnswersValuesShort() {
        String output = "";
        for (int i = 0; i < correctAnswers.getAnswersCount(); i++) {
            for (String answer : correctAnswers.getResponseValue(i).getAnswers()) {
                output += answer + ";";
            }
        }
        output = output.substring(0, output.length() - 1);

        return output;
    }

    public void add(String key) {
        if (cardinality == Cardinality.SINGLE) {
            values.clear();
        }

        values.add(key);
        initialized = true;
    }

    public void remove(String key) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(key)) {
                values.remove(i);
                return;
            }
        }
    }

    public void set(List<String> keys) {
        values = keys;
        initialized = true;
    }

    public void setCountMode(CountMode mode) {
        this.countMode = mode;
    }

    public CountMode getAppropriateCountMode() {
        CountMode countMode;
        if (compilerCountMode != null) {
            countMode = compilerCountMode;
        } else {
            countMode = this.countMode;
        }

        return cardinality == Cardinality.SINGLE ? CountMode.SINGLE : countMode;
    }

    public boolean compare(List<String> test) {
        if (values.size() != test.size()) {
            return false;
        }

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).compareTo(test.get(i)) != 0) {
                return false;
            }
        }
        return true;

    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void reset() {
        values.clear();
    }

    @Override
    public String toString() {
        return "Id: " + identifier + "\n" + correctAnswers;
    }

    @Override
    public JSONValue toJSON() {
        return null;
    }

    @Override
    public void fromJSON(JSONValue value) {
    }

    public boolean isInGroup() {
        boolean isInGroup = false;
        if (groups != null) {
            isInGroup = groups.size() > 0;
        }
        return isInGroup;
    }

    public boolean isInExpression() {
        return checkMode == CheckMode.EXPRESSION;
    }

    public ExpressionBean getExpression() {
        return expression;
    }

    public void setExpression(ExpressionBean expression) {
        this.expression = expression;
    }

    public CheckMode getCheckMode() {
        return checkMode;
    }
}
