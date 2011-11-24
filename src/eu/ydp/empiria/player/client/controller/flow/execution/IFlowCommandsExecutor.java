package eu.ydp.empiria.player.client.controller.flow.execution;

import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;

public interface IFlowCommandsExecutor {

	void executeCommand(IFlowCommand command);
}
