package eu.ydp.empiria.player.client.module.connection;

import java.util.logging.Logger;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleJAXBParser;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.StateController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.json.YJsonValue;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModule, ConnectionModuleModel, MatchInteractionBean> {

	private static final Logger LOGGER = Logger.getLogger(ConnectionModule.class.getName());

	@Inject
	private ConnectionModulePresenter presenter;

	@Inject
	private ConnectionModuleStructure connectionStructure;

	@Inject
	protected Provider<ConnectionModule> moduleFactory;

	@Inject
	private ConnectionModuleFactory connectionModuleFactory;

	@Inject
	private EventsBus eventsBus;
	@Inject
	private StateController stateController;

	private ConnectionModuleModel connectionModel;

	@Override
	protected void initalizeModule() {
		connectionModel = connectionModuleFactory.getConnectionModuleModel(getResponse(), this);
		connectionModel.setResponseSocket(getModuleSocket());
		getResponse().setCountMode(getCountMode());
	}

	@Override
	public ConnectionModule getNewInstance() {
		return moduleFactory.get();
	}

	@Override
	protected ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> getPresenter() {
		return presenter;
	}

	@Override
	protected ConnectionModuleModel getResponseModel() {
		return connectionModel;
	}

	@Override
	protected AbstractModuleStructure<MatchInteractionBean, ConnectionModuleJAXBParser> getStructure() {
		return connectionStructure;
	}

	@Override
	public void setState(JSONArray stateAndStructure) {

		LOGGER.info("Enter set state function");

		clearModel();

		YJsonValue convertedStateAndStructure = stateController.updateStateAndStructureVersion(stateAndStructure);
		JSONArray newState = stateController.getResponse(convertedStateAndStructure);

		getResponseModel().setState(newState);

		PlayerEventHandler pageContentResizedEventHandler = new PlayerEventHandler() {
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				LOGGER.info("Executing page content resized event handler");
				presenter.showAnswers(ShowAnswersType.USER);
				fireStateChanged(false, false);
			}
		};
		eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CONTENT_RESIZED), pageContentResizedEventHandler, new CurrentPageScope());
		LOGGER.info("Added page content resized event handler");
	}
}
