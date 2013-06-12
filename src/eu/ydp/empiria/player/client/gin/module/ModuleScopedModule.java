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
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
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
	
		bindOrdering();
		bindColorfill();
		bindChoice();
	}

	private void bindOrdering() {
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

	private void bindColorfill() {
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>(){}).in(Singleton.class);
		bind(ColorfillInteractionView.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>(){}).in(Singleton.class);
		bind(ColorfillInteractionModuleModel.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>(){}).in(Singleton.class);
		bind(ColorfillInteractionPresenter.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>(){}));
	}

	private void bindChoice() {
		bind(new TypeLiteral<ModuleScopedProvider<ChoiceModuleView>>(){}).in(Singleton.class);
		bind(ChoiceModuleView.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ChoiceModuleView>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ChoiceModulePresenter>>(){}).in(Singleton.class);
		bind(ChoiceModulePresenter.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ChoiceModulePresenter>>(){}));
		
		bind(new TypeLiteral<ModuleScopedProvider<ChoiceModuleModel>>(){}).in(Singleton.class);
		bind(ChoiceModuleModel.class)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(new TypeLiteral<ModuleScopedProvider<ChoiceModuleModel>>(){}));
	}
}
