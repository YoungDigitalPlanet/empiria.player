package eu.ydp.empiria.player.client.module.button;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionMuteEvent;

public class FeedbackAudioMuteButtonModule extends ActivityButtonModule {

	private boolean currentlyMuted;
	
	@Override
	protected void invokeRequest() {
		getInteractionEventsListener().onFeedbackSound(new FeedbackInteractionMuteEvent(!currentlyMuted));
	}

	@Override
	protected String getStyleName() {
		return "qp-feedback-audio-mute-" + ((currentlyMuted)?"on":"off") + "-button";
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if(deliveryEvent.getType() == DeliveryEventType.FEEDBACK_MUTE){
			if (deliveryEvent.getParams().containsKey("mute")  &&  deliveryEvent.getParams().get("mute") instanceof Boolean){
				currentlyMuted = (Boolean)deliveryEvent.getParams().get("mute");
				updateStyleName();
			}
		}
	}


}
