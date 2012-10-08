package eu.ydp.empiria.player.client.controller.module.textgaps;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public class TextGapFocusOutCheck extends TextGapFocusOut{
	public void testBeforeFlowNotificationOnCheck(){
		initDeliveryEngine();
		flowRequestInvoker.invokeRequest(new FlowRequest.Check());
		assertTrue("Before flow notification on check", isPassed());
	}
}
