package eu.ydp.empiria.player.client.module.connection;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
//import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModule, String, MatchInteractionBean> {

	@Inject
	ConnectionModuleStructure connectionStructure;

	@Inject
	protected ModuleFactory moduleFactory;
	
	@Override
	protected void initalizeModule() {
		connectionStructure.createFromXml(getModuleElement().toString());
		
		// TODO: presenter
		// TODO: listeners
	}
	
	@Override
	public ConnectionModule getNewInstance() {
		return moduleFactory.getConnectionModule();
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		// TODO to be implemented
	}

	@Override
	protected ActivityPresenter<String, MatchInteractionBean> getPresenter() {
		return null;
	}

	@Override
	protected AbstractResponseModel<String> getResponseModel() {
		return null;
	}

	@Override
	protected AbstractModuleStructure<MatchInteractionBean> getStructure() {
		return connectionStructure;
	}

}
