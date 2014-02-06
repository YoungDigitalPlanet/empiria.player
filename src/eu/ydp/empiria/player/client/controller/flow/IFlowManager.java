package eu.ydp.empiria.player.client.controller.flow;

import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public interface IFlowManager {

	void dispatchFlowRequest(IFlowRequest request);

	void dispatchFlowCommand(IFlowCommand command);

}
