package eu.ydp.empiria.player.client.controller.flow.request;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.controller.flow.processing.IFlowRequestProcessor;

public class MainFlowRequestInvoker implements IFlowRequestInvoker {

	protected List<IFlowRequestProcessor> processors;
	
	public MainFlowRequestInvoker(){
		processors = new ArrayList<IFlowRequestProcessor>();
	}
	
	public void addCommandProcessor(IFlowRequestProcessor processor){
		processors.add(0,processor);
	}
	
	public void invokeRequest(IFlowRequest request){
		if (request == null)
			return;
		for (IFlowRequestProcessor currProcessor : processors){
			if (currProcessor.isRequestSupported(request)){
				currProcessor.processRequest(request);
				return;
			}
		}
	}
}
