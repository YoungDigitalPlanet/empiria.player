package eu.ydp.empiria.player.client.controller.flow;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.execution.MainFlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.IFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.flow.request.MainFlowRequestInvoker;

public final class FlowManager {

	private MainFlowProcessor flowProcessor;
	private MainFlowRequestInvoker flowRequestInvoker;
	private MainFlowCommandsExecutor flowCommandsExecutor;
	
	public FlowManager(FlowProcessingEventsListener fael){
		flowRequestInvoker = new MainFlowRequestInvoker();
		flowProcessor = new MainFlowProcessor(fael);
		flowCommandsExecutor = new MainFlowCommandsExecutor(flowProcessor);
	}
	
	public void init(int itemsCount){
		flowProcessor.init(itemsCount);
	}
	
	public void initFlow(){
		flowProcessor.initFlow();
	}	
	
	public void deinitFlow(){
		flowProcessor.deinitFlow();
	}
	
	public void setFlowOptions(FlowOptions o){
		flowProcessor.setFlowOptions(o);
	}
	
	public FlowOptions getFlowOptions(){
		return flowProcessor.getFlowOptions();
	}
	
	public void addCommandProcessor(IFlowRequestProcessor processor){
		flowRequestInvoker.addRequestProcessor(processor);
	}

	public PageType getCurrentPageType(){
		return flowProcessor.getCurrentPageType();
	}
	
	public int getCurrentPageIndex(){
		return flowProcessor.getCurrentPageIndex();
	}
	
	public ActivityMode getActivityMode(){
		return flowProcessor.getActivityMode();
	}
	
	public PageReference getPageReference(){
		return flowProcessor.getPageReference();
	}

	
	public FlowCommandsExecutor getFlowCommandsExecutor(){
		return flowCommandsExecutor;
	}
	
	public FlowDataSupplier getFlowDataSupplier(){
		return flowProcessor;
	}
	
	public FlowRequestInvoker getFlowRequestInvoker(){
		return flowRequestInvoker;
	}
	
	public void invokeFlowRequest(IFlowRequest request){
		flowRequestInvoker.invokeRequest(request);
	}
	
	public void invokeFlowCommand(IFlowCommand command){
		
	}

	public void setDisplayOptions(DisplayOptions o){
		flowProcessor.setDisplayOptions(o);
	}
	
	public DisplayOptions getDisplayOptions(){
		return flowProcessor.getDisplayOptions();
	}
	
	public IFlowSocket getFlowSocket(){
		return new IFlowSocket() {
			
			@Override
			public void invokeRequest(IFlowRequest command) {
				flowRequestInvoker.invokeRequest(command);
			}
		};
	}	
}
