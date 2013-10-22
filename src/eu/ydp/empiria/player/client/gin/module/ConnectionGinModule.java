package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceImpl;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModuleViewImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModuleViewImplHandlers;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionSurfacesManager;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionViewVertical;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder()
					.implement(ConnectionItem.class, ConnectionItemImpl.class)
					.implement(ConnectionSurface.class, ConnectionSurfaceImpl.class)
					.implement(ConnectionView.class, ConnectionViewVertical.class)
					.build(ConnectionModuleFactory.class));
		install(new GinFactoryModuleBuilder().build(ConnectionItemsFactory.class));
		bind(ConnectionModulePresenter.class).to(ConnectionModulePresenterImpl.class);
		bind(ConnectionView.class).to(ConnectionViewVertical.class);
		bind(ConnectionSurfacesManager.class);
		bind(ConnectionModuleStructure.class);
		bind(ConnectionModuleViewImplHandlers.class);
		bind(MultiplePairModuleView.class).to(ConnectionModuleViewImpl.class);
	}

}
