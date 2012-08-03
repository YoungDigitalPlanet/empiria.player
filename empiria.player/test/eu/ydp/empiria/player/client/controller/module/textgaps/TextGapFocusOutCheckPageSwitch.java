package eu.ydp.empiria.player.client.controller.module.textgaps;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public class TextGapFocusOutCheckPageSwitch extends TextGapFocusOutTest {
	public void testBeforeFlowNotificationOnPageSwitch(){
		initDeliveryEngine();
		flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
		assertTrue("Before flow notification on nextItem", isPassed());
	}
}
