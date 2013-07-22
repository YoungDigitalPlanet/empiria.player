package eu.ydp.empiria.player.client.module.tutor;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;

public class ActionEventGenerator {

	@Inject @ModuleScoped
	private ActionExecutorService executorService;
	
	@Inject @ModuleScoped
	private OutcomeDrivenActionTypeGenerator actionTypeGenerator;
	
	private final EndHandler endHandler = new EndHandler() {
		@Override
		public void onEnd() {
			onActionEnd();
		}
	};
	
	public void start(){
		executeDefaultAction();
	}
	
	public void stop(){
		executeDefaultAction();
	}
	
	public void stateChanged(){
		Optional<ActionType> actionType = actionTypeGenerator.findActionType();
		if (actionType.isPresent()){
			executeAction(actionType.get());
		}
	}

	private void onActionEnd() {
		executeDefaultAction();
	}

	private void executeDefaultAction() {
		executeAction(ActionType.DEFAULT);
	}

	private void executeAction(ActionType action) {
		executorService.execute(action, endHandler);
	}
}
