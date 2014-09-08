package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public class GlobalVariablesProvider {

	@Inject
	private GlobalVariablesProcessor globalVariablesProcessor;

	public GlobalVariables retrieveGlobalVariables(ModulesProcessingResults modulesProcessingResults, Map<String, Response> responses, IUniqueModule sender) {

		return calculateGlobalVariables(modulesProcessingResults, responses);
	}

	private GlobalVariables calculateGlobalVariables(ModulesProcessingResults modulesProcessingResults, Map<String, Response> responses) {
		Map<String, DtoModuleProcessingResult> mapOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();
		return globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses);
	}
}
