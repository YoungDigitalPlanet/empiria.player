package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.Iterator;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.CHECKS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.RESET;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.SHOW_ANSWERS;

public class OutcomeVariablesInitializer {

	public void initializeOutcomeVariables(Map<String, Response> responses, Map<String, Outcome> outcomes) {

		ensureVariable(outcomes, new Outcome(VariableName.DONE.toString(), Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.TODO.toString(), Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.ERRORS.toString(), Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.LASTMISTAKEN.toString(), Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(CHECKS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(SHOW_ANSWERS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(RESET, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(VariableName.MISTAKES.toString(), Cardinality.SINGLE, BaseType.INTEGER, "0"));

		if (responses.keySet().size() > 0) {

			Iterator<String> responseKeys = responses.keySet().iterator();

			while (responseKeys.hasNext()) {
				Response currResp = responses.get(responseKeys.next());
				String cri = currResp.identifier;
				String prefix = cri + "-";

				ensureVariable(outcomes, new Outcome(prefix + VariableName.DONE, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.TODO, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.ERRORS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.LASTMISTAKEN, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + VariableName.MISTAKES, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				
				ensureVariable(outcomes, new Outcome(prefix + VariableName.LASTCHANGE, Cardinality.MULTIPLE, BaseType.INTEGER));

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
