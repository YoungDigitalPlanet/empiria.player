package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.VariablesProcessingModulesInitializer;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

import java.util.HashMap;
import java.util.Map;

public class VariablesProcessingInitializingWrapper {

    private final VariableProcessingAdapter variableProcessingAdapter;
    private final VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;
    private final ResponsesMapBuilder responsesMapBuilder;
    private boolean isInitialized = false;

    @Inject
    public VariablesProcessingInitializingWrapper(VariableProcessingAdapter variableProcessingAdapter,
                                                  VariablesProcessingModulesInitializer variablesProcessingModulesInitializer) {
        this.variableProcessingAdapter = variableProcessingAdapter;
        this.variablesProcessingModulesInitializer = variablesProcessingModulesInitializer;
        this.responsesMapBuilder = new ResponsesMapBuilder();
    }

    public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode) {
        ItemOutcomeStorageImpl outcomeStorage = prepareOutcomeStorate(outcomes);
        ItemResponseManager responseManager = responsesMapBuilder.buildResponseManager(responses);

        if (!isInitialized) {
            variablesProcessingModulesInitializer.initializeVariableProcessingModules(responseManager, outcomeStorage);
            isInitialized = true;
        }

        variableProcessingAdapter.processResponseVariables(responseManager, outcomeStorage, processingMode);
        getOutcomes(outcomeStorage, outcomes);
    }

    private ItemOutcomeStorageImpl prepareOutcomeStorate(Map<String, Outcome> outcomes) {
        ItemOutcomeStorageImpl outcomeStorage = new ItemOutcomeStorageImpl();
        outcomeStorage.importFromMap(outcomes);

        return outcomeStorage;
    }

    private void getOutcomes(ItemOutcomeStorageImpl outcomeStorage, Map<String, Outcome> outcomes) {
        for (String id : outcomeStorage.getVariableIdentifiers()) {
            outcomes.put(id, outcomeStorage.getVariable(id));
        }
    }
}
