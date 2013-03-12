package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.VariablesProcessingModulesInitializer;

public class VariablesProcessingInitializingWrapper {

	private VariableProcessingAdapter variableProcessingAdapter;
	private VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;
	private boolean isInitialized = false;
	
	@Inject
	public VariablesProcessingInitializingWrapper(
			VariableProcessingAdapter variableProcessingAdapter,
			VariablesProcessingModulesInitializer variablesProcessingModulesInitializer) {
		this.variableProcessingAdapter = variableProcessingAdapter;
		this.variablesProcessingModulesInitializer = variablesProcessingModulesInitializer;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode){
		if(!isInitialized){
			variablesProcessingModulesInitializer.initializeVariableProcessingModules(responses);
			isInitialized = true;
		}
		
		variableProcessingAdapter.processResponseVariables(responses, outcomes, processingMode);
	}

	public List<Boolean> evaluateAnswer(Response response) {
		return variableProcessingAdapter.evaluateAnswer(response);
	}
	
}
