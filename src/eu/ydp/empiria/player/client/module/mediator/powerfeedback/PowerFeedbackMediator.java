package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import static eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;

public class PowerFeedbackMediator {

	private PowerFeedbackTutorClient tutor;
	private PowerFeedbackBonusClient bonus;
	private final EndHandler endHandler = new EndHandler() {

		@Override
		public void onEnd() {
			executeBonus();
		}
	};
	private final PlayerEventHandler testPageLoadedHandler = new PlayerEventHandler() {

		@Override
		public void onPlayerEvent(PlayerEvent event) {
			initClients();
		}
	};
	private final StateChangeEventHandler stateChangedHandler = new StateChangeEventHandler() {

		@Override
		public void onStateChange(StateChangeEvent event) {
			StateChangedInteractionEvent scie = event.getValue();
			if (scie.isReset()) {
				resetClients();
			} else if (scie.isUserInteract()) {
				stateChanged();
			}
		}

	};
	private final PlayerEventHandler pageUnloadedhandler = new PlayerEventHandler() {

		@Override
		public void onPlayerEvent(PlayerEvent event) {
			terminate();
		}
	};

	@Inject
	public PowerFeedbackMediator(EventsBus eventsBus, PageScopeFactory pageScopeFactory, PowerFeedbackTutorClient defaultTutorClient, PowerFeedbackBonusClient defaultBonusClient) {
		this.tutor = defaultTutorClient;
		this.bonus = defaultBonusClient;
		final CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), testPageLoadedHandler, currentPageScope);
		eventsBus.addHandler(StateChangeEvent.getType(OUTCOME_STATE_CHANGED), stateChangedHandler, currentPageScope);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED), pageUnloadedhandler, currentPageScope);
	}

	public void registerTutor(PowerFeedbackTutorClient client) {
		tutor = client;
	}

	public void registerBonus(PowerFeedbackBonusClient client) {
		bonus = client;
	}

	private void stateChanged() {
		tutor.processUserInteraction(endHandler);
	}

	private void executeBonus() {
		bonus.processUserInteraction();
	}

	private void resetClients() {
		tutor.resetPowerFeedback();
		bonus.resetPowerFeedback();
	}

	private void terminate() {
		tutor.terminatePowerFeedback();
		bonus.terminatePowerFeedback();
	}

	private void initClients() {
		tutor.initPowerFeedbackClient();
	}
}
