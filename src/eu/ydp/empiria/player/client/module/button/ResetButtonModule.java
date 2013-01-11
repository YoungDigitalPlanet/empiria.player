package eu.ydp.empiria.player.client.module.button;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ResetButtonModule extends ActivityButtonModule {

	@Inject
	StyleNameConstants styleNames;

	@Override
	protected void invokeRequest() {
		flowRequestInvoker.invokeRequest(new FlowRequest.Reset(getCurrentGroupIdentifier()));
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		//
	}

	@Override
	protected String getStyleName() {
		return styleNames.QP_RESET_BUTTON();
	}


}
