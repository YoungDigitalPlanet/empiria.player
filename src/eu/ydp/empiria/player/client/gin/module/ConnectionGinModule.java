package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionGinModule extends AbstractGinModule {
	
	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder().build(ConnectionModuleFactory.class));
				
		bind(ConnectionModulePresenter.class).to(ConnectionModulePresenterImpl.class);		
		bind(ConnectionModuleStructure.class);
		//bind(MultiplePairModuleView.class).to(MultiplePairModuleViewImpl.class)
	}

}
