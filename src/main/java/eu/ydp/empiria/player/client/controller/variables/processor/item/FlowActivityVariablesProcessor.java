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

package eu.ydp.empiria.player.client.controller.variables.processor.item;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

import java.util.List;

public class FlowActivityVariablesProcessor {

    public static final String RESET = "RESET"; // Sum of reset clicked
    public static final String SHOW_ANSWERS = "SHOW_ANSWERS"; // Sum of ShowAnswers clicked
    public static final String CHECKS = "CHECKS"; // Sum of CheckAnswers clicked

    public void processFlowActivityVariables(ItemOutcomeStorageImpl outcomeManager, FlowActivityEvent event) {
        if (event != null) {
            switch (event.getType()) {
                case CHECK:
                    increaseOutcome(outcomeManager, CHECKS);
                    break;
                case SHOW_ANSWERS:
                    increaseOutcome(outcomeManager, SHOW_ANSWERS);
                    break;
                case RESET:
                    increaseOutcome(outcomeManager, RESET);
                    break;
                default:
                    break;
            }
        }
    }

    private void increaseOutcome(ItemOutcomeStorageImpl outcomeManager, String key) {
        if (outcomeManager.hasOutcome(key)) {
            Outcome outcome = outcomeManager.getVariable(key);

            int value = 0;
            List<String> values = outcome.values;
            if (!values.isEmpty()) {
                value = Integer.parseInt(values.get(0));
            }
            value++;
            values.clear();
            values.add(String.valueOf(value));
        }
    }

}
