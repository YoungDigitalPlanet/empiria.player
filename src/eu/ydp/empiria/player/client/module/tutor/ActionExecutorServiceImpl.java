package eu.ydp.empiria.player.client.module.tutor;

import com.google.inject.Inject;

public class ActionExecutorServiceImpl implements ActionExecutorService {

	private CommandFactory commandFactory;
	private TutorCommand currentCommand = null;
	
	@Inject
	public ActionExecutorServiceImpl(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	@Override
	public void execute(ActionType type, EndHandler handler) {
		if (currentCommand != null && !currentCommand.isFinished()) {
			currentCommand.terminate();
		}

		currentCommand = commandFactory.createCommand(type, handler);
		currentCommand.execute();
	}

	@Override
	public void terminate() {
		if (!currentCommand.isFinished()) {
			currentCommand.terminate();
		}
	}

}
