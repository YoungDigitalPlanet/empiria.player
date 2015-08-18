package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.presenter.*;
import eu.ydp.empiria.player.client.module.selection.view.*;

public class SelectionGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SelectionModulePresenter.class).to(SelectionModulePresenterImpl.class);
        bind(SelectionModuleView.class).to(SelectionModuleViewImpl.class);
        bind(SelectionElementGenerator.class).to(SelectionGridElementGeneratorImpl.class);
        bind(SelectionElementPositionGenerator.class).to(SelectionGridElementGeneratorImpl.class);
        install(new GinFactoryModuleBuilder().build(SelectionModuleFactory.class));
    }
}
