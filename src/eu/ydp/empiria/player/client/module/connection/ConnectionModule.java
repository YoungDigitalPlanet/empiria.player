package eu.ydp.empiria.player.client.module.connection;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleJAXBParser;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModule, ConnectionModuleModel, MatchInteractionBean> {

	@Inject
	ConnectionModuleStructure connectionStructure;
	
	@Inject
	private ConnectionModuleFactory connectionModuleFactory;

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
	protected ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> getPresenter() {
		return null;
	}

	@Override
	protected ConnectionModuleModel getResponseModel() {
		return connectionModuleFactory.getConnectionModuleModel(getResponse(), this);
	}

	@Override
	protected AbstractModuleStructure<MatchInteractionBean, ConnectionModuleJAXBParser> getStructure() {
		return connectionStructure;
	}

}
