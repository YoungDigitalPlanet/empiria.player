package eu.ydp.empiria.player.client.module.tutor;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ActionExecutorServiceImpl implements ActionExecutorService {

	private CommandFactory commandFactory;
	private TutorCommand currentCommand = null;

	@Inject
	public ActionExecutorServiceImpl(@ModuleScoped CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	@Override
	public void execute(ActionType type, TutorEndHandler handler) {
		if (currentCommand != null && !currentCommand.isFinished()) {
			currentCommand.terminate();
		}

		currentCommand = commandFactory.createCommand(type, handler);
		currentCommand.execute();
	}
}
