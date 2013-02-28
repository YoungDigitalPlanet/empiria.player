package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;

public class ModulesConstantVariablesInitializer {

	private final InitialProcessingResultFactory initialProcessingResultFactory;
	private final Provider<ModulesProcessingResults> modulesProcessingResultsProvider;
	private final ModuleTodoCalculator moduleTodoCalculator;

	@Inject
	public ModulesConstantVariablesInitializer(InitialProcessingResultFactory initialProcessingResultFactory,
			Provider<ModulesProcessingResults> modulesProcessingResultsProvider, ModuleTodoCalculator moduleTodoCalculator) {
		this.initialProcessingResultFactory = initialProcessingResultFactory;
		this.modulesProcessingResultsProvider = modulesProcessingResultsProvider;
		this.moduleTodoCalculator = moduleTodoCalculator;
	}

	public ModulesProcessingResults initializeTodoVariables(Map<String, Response> responses) {
		ModulesProcessingResults modulesProcessingResults = modulesProcessingResultsProvider.get();

		for (String responseId : responses.keySet()) {
			Response response = responses.get(responseId);
			DtoModuleProcessingResult moduleProcessingResult = getProcessingResultsWithCountedTodo(response);
			modulesProcessingResults.setProcessingResultsForResponseId(responseId, moduleProcessingResult);
		}

		return modulesProcessingResults;
	}

	private DtoModuleProcessingResult getProcessingResultsWithCountedTodo(Response response) {
		int todoCountForResponse = moduleTodoCalculator.calculateTodoForResponse(response);
		DtoModuleProcessingResult moduleProcessingResult = initialProcessingResultFactory.createProcessingResultWithInitialValues();
		ConstantVariables constantVariables = moduleProcessingResult.getConstantVariables();
		constantVariables.setTodo(todoCountForResponse);
		return moduleProcessingResult;
	}

}
