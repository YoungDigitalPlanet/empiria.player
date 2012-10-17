package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionPresenter;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModule, String> {

	private ConnectionPresenter presenter;
	
	private ConnectionModuleStructure connectionStructure;	
	
	@Inject
	protected ModuleFactory moduleFactory;
	
	@Inject
	protected ConnectionModuleFactory connectionModuleFactory;	
	
	@Override
	protected void initalizeModule() {
		connectionStructure = connectionModuleFactory.getConnectionModuleStructure();
		connectionStructure.createFromXml(getModuleElement().toString());
		
		//presenter. ...		

		addListeners();
	}
	
	private void addListeners() {
		ConnectionModuleListener listener = connectionModuleFactory.getConnectionModuleListener(this);
		//listener.addEventHandler(ConnectionEventTypes.CONNECTED);
		//listener.addEventHandler(ConnectionEventTypes.DISCONNECTED);
	}	

	@Override
	public ConnectionModule getNewInstance() {		
		return moduleFactory.getConnectionModule();
	}

	@Override
	protected ActivityPresenter createPresenter() {
		// TODO to be implemented
		return null;
	}	
}
