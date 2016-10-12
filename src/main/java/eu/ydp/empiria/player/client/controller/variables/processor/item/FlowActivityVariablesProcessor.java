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
        Outcome outcome;
        if ((outcome = outcomeManager.getVariable(key)) != null) {
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
