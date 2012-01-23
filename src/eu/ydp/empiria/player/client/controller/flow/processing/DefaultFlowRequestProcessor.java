package eu.ydp.empiria.player.client.controller.flow.processing;

import eu.ydp.empiria.player.client.controller.flow.execution.IFlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;


public class DefaultFlowRequestProcessor implements IFlowRequestProcessor {
	
	protected IFlowCommandsExecutor flowCommandsExecutor;
	
	public DefaultFlowRequestProcessor(IFlowCommandsExecutor fce){
		flowCommandsExecutor = fce;
	}
	
	@Override
	public boolean isRequestSupported(IFlowRequest request) {
		return getCommandByRequest(request) != null;
	}

	@Override
	public void processRequest(IFlowRequest request) {
		IFlowCommand command = getCommandByRequest(request);
		flowCommandsExecutor.executeCommand(command);
	}


	public IFlowCommand getCommandByRequest(IFlowRequest request){
		
		IFlowCommand command = null;
		
		if (request instanceof FlowRequest.NavigateNextItem){
			command = new FlowCommand.NavigateNextItem();
		} else if (request instanceof FlowRequest.NavigatePreviousItem){
			command = new FlowCommand.NavigatePreviousItem();
		} else if (request instanceof FlowRequest.NavigateFirstItem){
			command = new FlowCommand.NavigateFirstItem();
		} else if (request instanceof FlowRequest.NavigateLastItem){
			command = new FlowCommand.NavigateLastItem();
		} else if (request instanceof FlowRequest.NavigateToc){
			command = new FlowCommand.NavigateToc();
		} else if (request instanceof FlowRequest.NavigateTest){
			command = new FlowCommand.NavigateTest();
		} else if (request instanceof FlowRequest.NavigateSummary){
			command = new FlowCommand.NavigateSummary();
		} else if (request instanceof FlowRequest.NavigateGotoItem){
			command = new FlowCommand.NavigateGotoItem( ((FlowRequest.NavigateGotoItem)request).getIndex() );
		} else if (request instanceof FlowRequest.NavigatePreviewItem){
			command = new FlowCommand.NavigatePreviewItem( ((FlowRequest.NavigatePreviewItem)request).getIndex() );
		} else if (request instanceof FlowRequest.Check){
			command = new FlowCommand.Check();
		} else if (request instanceof FlowRequest.Continue){
			command = new FlowCommand.Continue();
		} else if (request instanceof FlowRequest.Reset){
			command = new FlowCommand.Reset();
		} else if (request instanceof FlowRequest.ShowAnswers){
			command = new FlowCommand.ShowAnswers();
		} else if (request instanceof FlowRequest.Lock){
			command = new FlowCommand.Lock();
		} else if (request instanceof FlowRequest.Unlock){
			command = new FlowCommand.Unlock();
		} 
		
		return command;
	}
}
