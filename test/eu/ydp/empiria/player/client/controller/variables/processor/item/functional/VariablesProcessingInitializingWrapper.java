package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.VariablesProcessingModulesInitializer;

import java.util.Map;

public class VariablesProcessingInitializingWrapper {

	private final VariableProcessingAdapter variableProcessingAdapter;
	private final VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;
	private boolean isInitialized = false;

	@Inject
	public VariablesProcessingInitializingWrapper(VariableProcessingAdapter variableProcessingAdapter,
			VariablesProcessingModulesInitializer variablesProcessingModulesInitializer) {
		this.variableProcessingAdapter = variableProcessingAdapter;
		this.variablesProcessingModulesInitializer = variablesProcessingModulesInitializer;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode) {
		if (!isInitialized) {
			variablesProcessingModulesInitializer.initializeVariableProcessingModules(responses, outcomes);
			isInitialized = true;
		}

		variableProcessingAdapter.processResponseVariables(responses, outcomes, processingMode, null);
	}

}
