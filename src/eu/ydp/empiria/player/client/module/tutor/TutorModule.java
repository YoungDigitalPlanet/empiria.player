package eu.ydp.empiria.player.client.module.tutor;

import static eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventHandler;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TutorModule extends SimpleModuleBase {

	@Inject private EventsBus eventsBus;
	@Inject private PageScopeFactory eventScopeFactory;
	@Inject @ModuleScoped private TutorPresenter presenter;
	@Inject @ModuleScoped private TutorView view;
	@Inject	@ModuleScoped private ActionEventGenerator eventGenerator;
	@Inject	@ModuleScoped private TutorConfig config;

	private final PlayerEventHandler testPageLoadedHandler = new PlayerEventHandler() {

		@Override
		public void onPlayerEvent(PlayerEvent event) {
			eventGenerator.start();
		}
	};
	private final PlayerEventHandler pageUnloadedhandler = new PlayerEventHandler() {

		@Override
		public void onPlayerEvent(PlayerEvent event) {
			eventGenerator.stop();
		}
	};
	private final StateChangeEventHandler stateChangedHandler = new StateChangeEventHandler() {

		@Override
		public void onStateChange(StateChangeEvent event) {
			StateChangedInteractionEvent scie = event.getValue();
			if(scie.isReset()){
				eventGenerator.executeDefaultAction();
			} else if(scie.isUserInteract()){
				eventGenerator.stateChanged();
			}
		}
	};
	private final TutorEventHandler tutorEventHandler = new TutorEventHandler() {

		@Override
		public void onTutorChanged(TutorEvent event) {
			eventGenerator.tutorChanged(event.getPersonaIndex());
		}
	};

	@Override
	protected void initModule(Element element) {
		addHandlers();
		init();
	}

	private void init() {
		presenter.init();
	}

	private void addHandlers() {
		final CurrentPageScope modulePageScope = eventScopeFactory.getCurrentPageScope();
		eventsBus.addHandler(StateChangeEvent.getType(OUTCOME_STATE_CHANGED), stateChangedHandler, modulePageScope);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED), pageUnloadedhandler, modulePageScope);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), testPageLoadedHandler, modulePageScope);
		eventsBus.addHandler(TutorEvent.getType(TutorEventTypes.TUTOR_CHANGED), tutorEventHandler, modulePageScope);
	}

	@Override
	public Widget getView() {
		return view.asWidget();
	}
}