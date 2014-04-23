package eu.ydp.empiria.player.client.controller.module.textgaps;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public class TextGapFocusOutCheckPageSwitchGWTTestCase extends TextGapFocusOutGWTTestCase {
	public void testBeforeFlowNotificationOnPageSwitch() {
		initDeliveryEngine();
		flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
		assertTrue("Before flow notification on nextItem", isPassed());
	}
}
