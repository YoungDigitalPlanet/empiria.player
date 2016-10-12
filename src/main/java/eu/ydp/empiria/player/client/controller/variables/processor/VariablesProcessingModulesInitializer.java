package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariablesProcessingModulesInitializer {

    private final GroupedAnswersManager groupedAnswersManager;
    private final ModulesVariablesProcessor modulesVariablesProcessor;
    private final MistakesInitializer mistakesInitializer;

    @Inject
    public VariablesProcessingModulesInitializer(@PageScoped GroupedAnswersManager groupedAnswersManager,
                                                 @PageScoped ModulesVariablesProcessor modulesVariablesProcessor, MistakesInitializer mistakesInitializer) {
        this.groupedAnswersManager = groupedAnswersManager;
        this.modulesVariablesProcessor = modulesVariablesProcessor;
        this.mistakesInitializer = mistakesInitializer;
    }

    public void initializeVariableProcessingModules(ItemResponseManager responseManager, ItemOutcomeStorageImpl outcomeManager) {

        groupedAnswersManager.initialize(responseManager);
        modulesVariablesProcessor.initialize(responseManager);
        mistakesInitializer.initialize(outcomeManager);
    }

}
