package eu.ydp.empiria.player.client.module.button;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public class ResetButtonModule extends ActivityButtonModule {

	@Override
	protected void invokeRequest() {
		flowRequestInvoker.invokeRequest(new FlowRequest.Reset(getCurrentGroupIdentifier()));
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
	}

	@Override
	protected String getStyleName() {
		return "qp-reset-button";
	}


}
