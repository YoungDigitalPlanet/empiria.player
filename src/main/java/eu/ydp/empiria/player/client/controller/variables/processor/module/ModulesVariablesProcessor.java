package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.List;

public class ModulesVariablesProcessor {

    private final ResponseChangesFinder responseChangesFinder;

    private final ModulesConstantVariablesInitializer constantVariablesInitializer;
    private final ModulesProcessingResults processingResults;
    private final ResponseVariablesProcessor responseVariablesProcessor;

    @Inject
    public ModulesVariablesProcessor(ResponseChangesFinder responseChangesFinder, ModulesConstantVariablesInitializer constantVariablesInitializer,
                                     ResponseVariablesProcessor responseVariablesProcessor, @PageScoped ModulesProcessingResults processingResults) {
        this.responseChangesFinder = responseChangesFinder;
        this.constantVariablesInitializer = constantVariablesInitializer;
        this.responseVariablesProcessor = responseVariablesProcessor;
        this.processingResults = processingResults;
    }

    public void initialize(ItemResponseManager responseManager) {
        constantVariablesInitializer.initializeTodoVariables(responseManager, processingResults);
    }

    public ModulesProcessingResults processVariablesForResponses(VariableManager<Response> responseManager, ProcessingMode processingMode) {
        List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responseManager);
        processVariablesForResponses(processedResponses, processingMode);
        return processingResults;
    }

    private void processVariablesForResponses(List<DtoProcessedResponse> changedResponses, ProcessingMode processingMode) {
        for (DtoProcessedResponse processedResponse : changedResponses) {
            processVariablesForResponse(processingMode, processedResponse);
        }
    }

    private void processVariablesForResponse(ProcessingMode processingMode, DtoProcessedResponse processedResponse) {
        if (shouldProcessResponse(processedResponse))
            responseVariablesProcessor.processChangedResponse(processedResponse, processingMode);
        else
            resetVariablesOfLastInteraction(processedResponse);
    }

    private boolean shouldProcessResponse(DtoProcessedResponse processedResponse) {
        boolean shouldProcessResponse = false;
        Response response = processedResponse.getCurrentResponse();

        if (processedResponse.containChanges()) {
            shouldProcessResponse = true;
        } else if (response.isInGroup()) {
            shouldProcessResponse = true;
        } else if (response.isInExpression()) {
            shouldProcessResponse = true;
        }
        return shouldProcessResponse;
    }

    private void resetVariablesOfLastInteraction(DtoProcessedResponse processedResponse) {
        DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
        UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
        responseVariablesProcessor.resetLastUserInteractionVariables(userInteractionVariables);
    }
}
