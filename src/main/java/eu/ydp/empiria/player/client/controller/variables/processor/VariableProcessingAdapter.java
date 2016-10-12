package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariableProcessingAdapter {

    private final ModulesVariablesProcessor modulesVariablesProcessor;
    private final AnswerEvaluationSupplier answerEvaluationProvider;
    private final ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade;
    private final GlobalVariablesProvider globalVariablesProvider;

    @Inject
    public VariableProcessingAdapter(@PageScoped ModulesVariablesProcessor modulesVariablesProcessor,
                                     @PageScoped AnswerEvaluationSupplier answerEvaluationProvider, ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade,
                                     GlobalVariablesProvider globalVariablesProvider) {
        this.modulesVariablesProcessor = modulesVariablesProcessor;
        this.mapConverterFacade = mapConverterFacade;
        this.answerEvaluationProvider = answerEvaluationProvider;
        this.globalVariablesProvider = globalVariablesProvider;
    }

    public void processResponseVariables(VariableManager<Response> responseManager, ItemOutcomeStorageImpl outcomeManager, ProcessingMode processingMode) {
        ModulesProcessingResults modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responseManager, processingMode);
        GlobalVariables globalVariables = globalVariablesProvider.retrieveGlobalVariables(modulesProcessingResults, responseManager);
        mapConverterFacade.convert(outcomeManager, modulesProcessingResults, globalVariables);
        answerEvaluationProvider.updateModulesProcessingResults(modulesProcessingResults);
    }
}
