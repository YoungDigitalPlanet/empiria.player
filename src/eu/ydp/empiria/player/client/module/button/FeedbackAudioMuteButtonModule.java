package eu.ydp.empiria.player.client.module.button;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionMuteEvent;

public class FeedbackAudioMuteButtonModule extends ActivityButtonModule {

	public final static String STYLE_NAME__ON = "qp-feedback-audio-mute-on-button";
	public final static String STYLE_NAME__OFF = "qp-feedback-audio-mute-off-button";
	public final static String STYLE_NAME__DISABLED = "qp-feedback-audio-mute-disabled";
	
	private boolean currentlyMuted;
	private boolean isVisible;
	
	@Override
	protected void invokeRequest() {
		getInteractionEventsListener().onFeedbackSound(new FeedbackInteractionMuteEvent(!currentlyMuted));
	}

	@Override
	protected String getStyleName() {
		final StringBuilder styleName = new StringBuilder(70);
		styleName.append((currentlyMuted) ? STYLE_NAME__ON : STYLE_NAME__OFF);
		if (!isVisible) {
			styleName.append(' ').append(STYLE_NAME__DISABLED);
		}
		return styleName.toString();
	}

	protected boolean hasItemInteractiveModules() {
		boolean isVisible = false; 
		if (getModuleSocket() != null) {
				isVisible = (getModuleSocket().hasInteractiveModules());						
		}	
		return isVisible;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if(deliveryEvent.getType() == DeliveryEventType.FEEDBACK_MUTE){
			if (deliveryEvent.getParams().containsKey("mute")  &&  deliveryEvent.getParams().get("mute") instanceof Boolean){
				currentlyMuted = (Boolean)deliveryEvent.getParams().get("mute");
				updateStyleName();
			}
		}
		
		if (deliveryEvent.getType() == DeliveryEventType.TEST_PAGE_LOADED) {
			isVisible = hasItemInteractiveModules();
			updateStyleName();
		}
		 
	}


}
