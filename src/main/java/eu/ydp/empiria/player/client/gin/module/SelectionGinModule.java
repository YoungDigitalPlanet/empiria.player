package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.selection.view.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class SelectionGinModule extends EmpiriaModule {

    @Override
    protected void configure() {
        bind(SelectionModulePresenter.class).to(SelectionModulePresenterImpl.class);
        bind(SelectionModuleView.class).to(SelectionModuleViewImpl.class);
        bind(SelectionElementGenerator.class).to(SelectionGridElementGeneratorImpl.class);
        bind(SelectionElementPositionGenerator.class).to(SelectionGridElementGeneratorImpl.class);

        bindModuleScoped(SelectionModuleModel.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleModel>>() {
        });
        bindModuleScoped(SelectionModuleView.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleView>>() {
        });
        bindModuleScoped(SelectionViewBuilder.class, new TypeLiteral<ModuleScopedProvider<SelectionViewBuilder>>() {
        });

        install(new GinFactoryModuleBuilder().build(SelectionModuleFactory.class));
    }
}
