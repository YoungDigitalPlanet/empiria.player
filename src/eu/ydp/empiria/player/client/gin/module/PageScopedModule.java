package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.item.ItemDataProvider;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.item.PageScopedItemResponseManagerProvider;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.xml.XMLDataProvider;
import eu.ydp.empiria.player.client.gin.providers.GroupedAnswersManagerPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesProcessingResultsPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesVariablesProcessorPageScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class PageScopedModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(GroupedAnswersManagerPageScopeProvider.class).in(Singleton.class);
		bind(GroupedAnswersManager.class).annotatedWith(PageScoped.class).toProvider(GroupedAnswersManagerPageScopeProvider.class);

		bind(ModulesVariablesProcessorPageScopedProvider.class).in(Singleton.class);
		bind(ModulesVariablesProcessor.class).annotatedWith(PageScoped.class).toProvider(ModulesVariablesProcessorPageScopedProvider.class);

		bind(ModulesProcessingResultsPageScopeProvider.class).in(Singleton.class);
		bind(ModulesProcessingResults.class).annotatedWith(PageScoped.class).toProvider(ModulesProcessingResultsPageScopeProvider.class);

		bind(ItemDataProvider.class).in(Singleton.class);
		bind(ItemData.class).annotatedWith(PageScoped.class).toProvider(ItemDataProvider.class);

		bind(XMLDataProvider.class).in(Singleton.class);
		bind(XmlData.class).annotatedWith(PageScoped.class).toProvider(XMLDataProvider.class);

		bind(PageScopedItemResponseManagerProvider.class).in(Singleton.class);
		bind(ItemResponseManager.class).annotatedWith(PageScoped.class).toProvider(PageScopedItemResponseManagerProvider.class);



	}

}
