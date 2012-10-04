package eu.ydp.empiria.player.client.controller.flow.processing.commands;

public interface IFlowCommand {
	
	String getName();

	void execute(FlowCommandsListener listener);
}
