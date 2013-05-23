package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.item.AnswerEvaluationSupplierProvider;
import eu.ydp.empiria.player.client.controller.item.ItemDataProvider;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.item.ItemXMLWrapper;
import eu.ydp.empiria.player.client.controller.item.PageScopedItemResponseManagerProvider;
import eu.ydp.empiria.player.client.controller.item.PageScopedItemXMLWrapperProvider;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.xml.XMLDataProvider;
import eu.ydp.empiria.player.client.gin.providers.GroupedAnswersManagerPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesProcessingResultsPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.ModulesVariablesProcessorPageScopedProvider;
import eu.ydp.empiria.player.client.gin.providers.ResponseSocketPageScopeProvider;
import eu.ydp.empiria.player.client.gin.providers.VariableProcessingAdapterPageScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ResponseSocket;
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
		
		bind(ResponseSocketPageScopeProvider.class).in(Singleton.class);
		bind(ResponseSocket.class).annotatedWith(PageScoped.class).toProvider(ResponseSocketPageScopeProvider.class);
		
		bind(VariableProcessingAdapterPageScopedProvider.class).in(Singleton.class);
		bind(VariableProcessingAdapter.class).annotatedWith(PageScoped.class).toProvider(VariableProcessingAdapterPageScopedProvider.class);

		bind(ItemDataProvider.class).in(Singleton.class);
		bind(ItemData.class).annotatedWith(PageScoped.class).toProvider(ItemDataProvider.class);

		bind(XMLDataProvider.class).in(Singleton.class);
		bind(XmlData.class).annotatedWith(PageScoped.class).toProvider(XMLDataProvider.class);

		bind(PageScopedItemResponseManagerProvider.class).in(Singleton.class);
		bind(ItemResponseManager.class).annotatedWith(PageScoped.class).toProvider(PageScopedItemResponseManagerProvider.class);

		bind(PageScopedItemXMLWrapperProvider.class).in(Singleton.class);
		bind(ItemXMLWrapper.class).annotatedWith(PageScoped.class).toProvider(PageScopedItemXMLWrapperProvider.class);	

		bind(AnswerEvaluationSupplierProvider.class).in(Singleton.class);
		bind(AnswerEvaluationSupplier.class).annotatedWith(PageScoped.class).toProvider(AnswerEvaluationSupplierProvider.class);	
	}

}
