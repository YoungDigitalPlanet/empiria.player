package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariablesProcessingModulesInitializer {

	private GroupedAnswersManager groupedAnswersManager;
	private ModulesVariablesProcessor modulesVariablesProcessor;
	
	@Inject
	public VariablesProcessingModulesInitializer(
			@PageScoped GroupedAnswersManager groupedAnswersManager, 
			@PageScoped ModulesVariablesProcessor modulesVariablesProcessor) {
		this.groupedAnswersManager = groupedAnswersManager;
		this.modulesVariablesProcessor = modulesVariablesProcessor;
	}

	public void initializeVariableProcessingModules(Map<String, Response> responses){
		groupedAnswersManager.initialize(responses);
		modulesVariablesProcessor.initialize(responses);
	}
	
}
