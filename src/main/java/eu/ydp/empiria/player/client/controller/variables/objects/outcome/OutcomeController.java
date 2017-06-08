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

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OutcomeController {

    private static final Logger LOGGER = Logger.getLogger(OutcomeController.class.getName());

    public final static String MISTAKE_SUFIX = "-" + VariableName.MISTAKES.toString();

    public Map<String, Integer> getAllMistakes(ItemOutcomeStorageImpl outcomeManager) {
        Map<String, Integer> result = new HashMap<String, Integer>();

        for (String key : outcomeManager.getVariableIdentifiers()) {
            if(key != null && key.endsWith(MISTAKE_SUFIX)) {
                Outcome outcome = outcomeManager.getVariable(key);
                addMistake(result, key, outcome);
            }
        }

        return result;
    }

    private void addMistake(Map<String, Integer> result, String key, Outcome outcome) {
        String resonseIdFormKey = getResonseIdFormKey(key);
        Integer mistakeNumber = getValueFromOutcome(outcome);
        result.put(resonseIdFormKey, mistakeNumber);
    }

    private String getResonseIdFormKey(String key) {
        return key.substring(0, key.length() - MISTAKE_SUFIX.length());
    }

    private Integer getValueFromOutcome(Outcome outcome) {
        Integer result = 0;

        if (outcome == null || outcome.values == null || outcome.values.size() == 0) {
            LOGGER.warning("Outcome.values can't be null or empty");
            return result;
        }

        try {
            result = Integer.valueOf(outcome.values.get(0));
        } catch (NumberFormatException e) {
            LOGGER.warning("Outcome.values for mistakes should be integer type");
        }

        return result;
    }

}
