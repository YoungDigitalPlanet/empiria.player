package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

import java.util.Map;

public class GlobalVariablesProvider {

    @Inject
    private GlobalVariablesProcessor globalVariablesProcessor;

    public GlobalVariables retrieveGlobalVariables(ModulesProcessingResults modulesProcessingResults, Map<String, Response> responses) {

        return calculateGlobalVariables(modulesProcessingResults, responses);
    }

    private GlobalVariables calculateGlobalVariables(ModulesProcessingResults modulesProcessingResults, Map<String, Response> responses) {
        Map<String, DtoModuleProcessingResult> mapOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();
        return globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses);
    }
}
