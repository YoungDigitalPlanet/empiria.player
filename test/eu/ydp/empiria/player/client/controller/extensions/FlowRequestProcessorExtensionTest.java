package eu.ydp.empiria.player.client.controller.extensions;

import com.google.gwt.junit.client.GWTTestCase;

import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;

import eu.ydp.empiria.player.client.controller.extensions.FlowCommandsSocketUserExtensionTest.MockFlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;

public class FlowRequestProcessorExtensionTest extends ExtensionTestBase {

	protected IFlowCommand[] cmds = {new FlowCommand.NavigateFirstItem()};
	protected int counter = 0;

	protected DeliveryEngine de;
	protected FlowRequestInvoker fri;
	protected FlowCommandsExecutor fce;
	protected FlowDataSupplier fds;
	
	public void testFlow(){
		de = initDeliveryEngine(new MockFlowRequestProcessorExtension());

		assertEquals(PageType.TOC, fds.getCurrentPageType());
		
		fri.invokeRequest(new FlowRequest.NavigateFirstItem());
		assertEquals(PageType.TEST, fds.getCurrentPageType());
		assertEquals(0, fds.getCurrentPageIndex());
		
	}
	
	protected void processRequestCheck(IFlowRequest request){
		if (counter < cmds.length){
			fce.executeCommand(cmds[counter]);
			counter++;
		}
	}
	

	protected class MockFlowRequestProcessorExtension extends InternalExtension implements FlowRequestProcessorExtension, FlowRequestSocketUserExtension, 
		FlowCommandsSocketUserExtension, FlowDataSocketUserExtension{

		@Override
		public void init() {
			
		}
		@Override
		public boolean isRequestSupported(IFlowRequest request) {			
			return true;
		}

		@Override
		public void processRequest(IFlowRequest request) {
			processRequestCheck(request);
		}
		@Override
		public void setFlowRequestsInvoker(FlowRequestInvoker invoker) {
			fri = invoker;
		}
		@Override
		public void setFlowCommandsExecutor(FlowCommandsExecutor executor) {
			fce = executor;
		}
		@Override
		public void setFlowDataSupplier(FlowDataSupplier supplier) {
			fds = supplier;
		}
		
	}
	
}
