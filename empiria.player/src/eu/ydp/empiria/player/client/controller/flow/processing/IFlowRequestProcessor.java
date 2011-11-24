package eu.ydp.empiria.player.client.controller.flow.processing;

import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public interface IFlowRequestProcessor {
	
	boolean isRequestSupported(IFlowRequest request);
	
	void processRequest(IFlowRequest request);
}
