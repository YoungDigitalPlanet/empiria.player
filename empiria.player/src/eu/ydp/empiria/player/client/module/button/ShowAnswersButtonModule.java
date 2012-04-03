package eu.ydp.empiria.player.client.module.button;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public class ShowAnswersButtonModule extends ActivityButtonModule {

	protected boolean isSelected = false;
	
	@Override
	protected void invokeRequest() {
		if (isSelected)
			flowRequestInvoker.invokeRequest(new FlowRequest.Continue(getCurrentGroupIdentifier()));
		else
			flowRequestInvoker.invokeRequest(new FlowRequest.ShowAnswers(getCurrentGroupIdentifier()));	
	}

	@Override
	protected String getStyleName() {
		return "qp-" + (isSelected?"hideanswers":"showanswers") + "-button";
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {

		if (flowEvent.getType().equals(DeliveryEventType.SHOW_ANSWERS)  &&  
				(!flowEvent.getParams().containsKey("groupIdentifier")  ||  
				 flowEvent.getParams().get("groupIdentifier") == null  ||  
				 currentGroupIsConcerned( (GroupIdentifier)flowEvent.getParams().get("groupIdentifier") ))
			){
			isSelected = true;
			updateStyleName();
		} else if ((flowEvent.getType().equals(DeliveryEventType.CONTINUE) ||
				flowEvent.getType().equals(DeliveryEventType.CHECK) ||
				flowEvent.getType().equals(DeliveryEventType.RESET)
				)  &&  
				(!flowEvent.getParams().containsKey("groupIdentifier")  || 
				 flowEvent.getParams().get("groupIdentifier") == null  ||  
				 currentGroupIsConcerned( (GroupIdentifier)flowEvent.getParams().get("groupIdentifier") ))  
				){
			isSelected = false;
			updateStyleName();
		}
	}

}
