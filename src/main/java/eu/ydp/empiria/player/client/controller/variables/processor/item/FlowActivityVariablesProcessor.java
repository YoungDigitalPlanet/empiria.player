package eu.ydp.empiria.player.client.controller.variables.processor.item;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.List;
import java.util.Map;

public class FlowActivityVariablesProcessor {

    public static final String RESET = "RESET"; // Sum of reset clicked
    public static final String SHOW_ANSWERS = "SHOW_ANSWERS"; // Sum of ShowAnswers clicked
    public static final String CHECKS = "CHECKS"; // Sum of CheckAnswers clicked

    public void processFlowActivityVariables(Map<String, Outcome> outcomes, FlowActivityEvent event) {
        if (event != null) {
            switch (event.getType()) {
                case CHECK:
                    increaseOutcome(outcomes, CHECKS);
                    break;
                case SHOW_ANSWERS:
                    increaseOutcome(outcomes, SHOW_ANSWERS);
                    break;
                case RESET:
                    increaseOutcome(outcomes, RESET);
                    break;
                default:
                    break;
            }
        }
    }

    private void increaseOutcome(Map<String, Outcome> outcomes, String key) {
        Outcome outcome = null;
        if ((outcome = outcomes.get(key)) != null) {
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
