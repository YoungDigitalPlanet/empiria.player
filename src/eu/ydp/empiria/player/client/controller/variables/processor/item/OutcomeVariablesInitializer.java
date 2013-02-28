package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONECHANGES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONEHISTORY;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.PREVIOUS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.CHECKS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.RESET;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.SHOW_ANSWERS;

import java.util.Iterator;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class OutcomeVariablesInitializer {

	public void initializeOutcomeVariables(Map<String, Response> responses, Map<String, Outcome> outcomes) {

		ensureVariable(outcomes, new Outcome(DONE, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(TODO, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(ERRORS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(DONEHISTORY, Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome(DONECHANGES, Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome(LASTMISTAKEN, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(CHECKS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(SHOW_ANSWERS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(RESET, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(MISTAKES, Cardinality.SINGLE, BaseType.INTEGER, "0"));

		if (responses.keySet().size() > 0) {

			Iterator<String> responseKeys = responses.keySet().iterator();

			while (responseKeys.hasNext()) {
				Response currResp = responses.get(responseKeys.next());
				String cri = currResp.identifier;
				String prefix = cri + "-";

				ensureVariable(outcomes, new Outcome(prefix + DONE, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + TODO, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + ERRORS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + LASTMISTAKEN, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + MISTAKES, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				
				ensureVariable(outcomes, new Outcome(prefix + LASTCHANGE, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + PREVIOUS, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + DONEHISTORY, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + DONECHANGES, Cardinality.MULTIPLE, BaseType.INTEGER));

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
