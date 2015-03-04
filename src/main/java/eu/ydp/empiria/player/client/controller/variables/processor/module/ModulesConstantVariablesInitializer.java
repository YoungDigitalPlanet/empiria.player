package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;

public class ModulesConstantVariablesInitializer {

	private final ModuleTodoCalculator moduleTodoCalculator;

	@Inject
	public ModulesConstantVariablesInitializer(ModuleTodoCalculator moduleTodoCalculator) {
		this.moduleTodoCalculator = moduleTodoCalculator;
	}

	public void initializeTodoVariables(Map<String, Response> responses, ModulesProcessingResults modulesProcessingResults) {
		for (String responseId : responses.keySet()) {
			Response response = responses.get(responseId);
			DtoModuleProcessingResult moduleProcessingResult = modulesProcessingResults.getProcessingResultsForResponseId(responseId);

			initializeTodoCount(response, moduleProcessingResult);
		}
	}

	private void initializeTodoCount(Response response, DtoModuleProcessingResult moduleProcessingResult) {
		int todoCountForResponse = moduleTodoCalculator.calculateTodoForResponse(response);
		ConstantVariables constantVariables = moduleProcessingResult.getConstantVariables();
		constantVariables.setTodo(todoCountForResponse);
	}

}
