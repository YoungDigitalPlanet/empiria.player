package eu.ydp.empiria.player.client.controller.module.textgaps;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public class TextGapFocusOutShowAnswers extends TextGapFocusOut {
	public void testBeforeFlowNotificationOnShowAnswers() {
		initDeliveryEngine();
		flowRequestInvoker.invokeRequest(new FlowRequest.ShowAnswers());
		System.out.println("asdasdasdasdasdasd " + isPassed());
		assertTrue("Before flow notification on showAnswers", isPassed());
	}
}
