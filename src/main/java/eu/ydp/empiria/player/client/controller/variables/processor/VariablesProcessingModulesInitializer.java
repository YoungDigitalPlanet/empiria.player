package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.Map;

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

    public void initializeVariableProcessingModules(Map<String, Response> responses, Map<String, Outcome> outcomes) {

        groupedAnswersManager.initialize(responses);
        modulesVariablesProcessor.initialize(responses);
        mistakesInitializer.initialize(outcomes);
    }

}
