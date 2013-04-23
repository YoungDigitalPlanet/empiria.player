package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.gin.providers.GroupedAnswersManagerPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesProcessingResultsPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesVariablesProcessorPageScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class PageScopedModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(GroupedAnswersManagerPageScopeProvider.class).in(Singleton.class);
		bind(GroupedAnswersManager.class).annotatedWith(PageScoped.class).toProvider(GroupedAnswersManagerPageScopeProvider.class);

		bind(ModulesVariablesProcessorPageScopedProvider.class).in(Singleton.class);
		bind(ModulesVariablesProcessor.class).annotatedWith(PageScoped.class).toProvider(ModulesVariablesProcessorPageScopedProvider.class);

		bind(ModulesProcessingResultsPageScopeProvider.class).in(Singleton.class);
		bind(ModulesProcessingResults.class).annotatedWith(PageScoped.class).toProvider(ModulesProcessingResultsPageScopeProvider.class);

	}

}
