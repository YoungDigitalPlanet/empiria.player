package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleViewImpl;

public class SelectionGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder()
			.build(SelectionModuleFactory.class)
				);
		
		bind(SelectionModulePresenter.class).to(SelectionModulePresenterImpl.class);
		bind(SelectionModuleView.class).to(SelectionModuleViewImpl.class);
	}
}
