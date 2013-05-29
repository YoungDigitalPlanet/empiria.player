package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.xml.client.Element;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ResponseModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.XmlElementModuleScopedProvider;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class ModuleScopedModule extends AbstractGinModule{

	@Override
	protected void configure() {
		bind(ModuleScopeStack.class).in(Singleton.class);
		
		bind(Element.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(XmlElementModuleScopedProvider.class);
		
		bind(Response.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(ResponseModuleScopedProvider.class);
		
		bind(new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>(){}).in(Singleton.class);
		bind(OrderingItemsDao.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>(){}).in(Singleton.class);
		bind(OrderInteractionModuleModel.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>(){}));

		bind(new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>(){}).in(Singleton.class);
		bind(OrderInteractionView.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>(){}));
	}

}
