package eu.ydp.empiria.player.client.module.tutor;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ActionEventGenerator {

	private final EndHandler endHandler = new EndHandler() {
		@Override
		public void onEnd() {
			onActionEnd();
		}
	};
	
	@Inject @ModuleScoped
	private ActionExecutorService executorService;
	
	@Inject @ModuleScoped
	private OutcomeDrivenActionTypeGenerator actionTypeGenerator;
	
	@Inject @ModuleScoped
	private PersonaService personaService;
	
	public void start(){
		executeDefaultAction();
	}
	
	public void stop(){
		executeDefaultAction();
	}
	
	public void stateChanged(){
		TutorPersonaProperties currentPersona = personaService.getPersonaProperties();
		if(currentPersona.isInteractive()){
			Optional<ActionType> actionType = actionTypeGenerator.findActionType();
			if (actionType.isPresent()) {
				executeAction(actionType.get());
			}
		}
	}

	public void tutorChanged(int personaIndex) {
		personaService.setCurrentPersonaIndex(personaIndex);
		executeDefaultAction();
	}
	
	private void onActionEnd() {
		executeDefaultAction();
	}

	public void executeDefaultAction() {
		executeAction(ActionType.DEFAULT);
	}

	private void executeAction(ActionType action) {
		executorService.execute(action, endHandler);
	}
}