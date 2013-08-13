package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.CHECKS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.RESET;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.SHOW_ANSWERS;

import java.util.Iterator;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public class OutcomeVariablesInitializer {

	public void initializeOutcomeVariables(Map<String, Response> responses, Map<String, Outcome> outcomes) {

		ensureVariable(outcomes, new Outcome(VariableName.DONE.toString(), Cardinality.SINGLE,"0"));
		ensureVariable(outcomes, new Outcome(VariableName.TODO.toString(), Cardinality.SINGLE, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.ERRORS.toString(), Cardinality.SINGLE, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.LASTMISTAKEN.toString(), Cardinality.SINGLE, LastMistaken.NONE.toString()));
		ensureVariable(outcomes, new Outcome(CHECKS, Cardinality.SINGLE, "0"));
		ensureVariable(outcomes, new Outcome(SHOW_ANSWERS, Cardinality.SINGLE, "0"));
		ensureVariable(outcomes, new Outcome(RESET, Cardinality.SINGLE, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.MISTAKES.toString(), Cardinality.SINGLE, "0"));

		if (responses.keySet().size() > 0) {

			Iterator<String> responseKeys = responses.keySet().iterator();

			while (responseKeys.hasNext()) {
				Response currResp = responses.get(responseKeys.next());
				String cri = currResp.identifier;
				String prefix = cri + "-";

				ensureVariable(outcomes, new Outcome(prefix + VariableName.DONE, Cardinality.SINGLE, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.TODO, Cardinality.SINGLE, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.ERRORS, Cardinality.SINGLE, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.LASTMISTAKEN, Cardinality.SINGLE, LastMistaken.NONE.toString()));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.MISTAKES, Cardinality.SINGLE, "0"));

				ensureVariable(outcomes, new Outcome(prefix + VariableName.LASTCHANGE, Cardinality.MULTIPLE));

			}
		}

	}

	private void ensureVariable(Map<String, Outcome> outcomes, Outcome variable) {
		Outcome prevValue = outcomes.get(variable.identifier);
		if (prevValue == null) {
			outcomes.put(variable.identifier, variable);
		} else if (variable.values.size() > 0 && prevValue.values.size() == 0) {
			prevValue.values.addAll(variable.values);
		}
	}
}
