package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleJAXBParser;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModule, ConnectionModuleModel, MatchInteractionBean> {

	@Inject
	private ConnectionModulePresenter presenter;

	@Inject
	ConnectionModuleStructure connectionStructure;

	@Inject
	protected ModuleFactory moduleFactory;

	@Inject
	private ConnectionModuleFactory connectionModuleFactory;

	@Override
	protected void initalizeModule() {

		// TODO: feedbacks
	}



	@Override
	public ConnectionModule getNewInstance() {
		return moduleFactory.getConnectionModule();
	}

	@Override
	protected ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> getPresenter() {
		return presenter;
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
