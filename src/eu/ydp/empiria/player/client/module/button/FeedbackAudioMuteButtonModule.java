package eu.ydp.empiria.player.client.module.button;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.feedback.FeedbackEvent;
import eu.ydp.empiria.player.client.util.events.feedback.FeedbackEventHandler;
import eu.ydp.empiria.player.client.util.events.feedback.FeedbackEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class FeedbackAudioMuteButtonModule extends AbstractActivityButtonModule implements FeedbackEventHandler, PlayerEventHandler{

	public final static String STYLE_NAME__ON = "qp-feedback-audio-mute-on-button";
	
	public final static String STYLE_NAME__OFF = "qp-feedback-audio-mute-off-button";
	
	public final static String STYLE_NAME__DISABLED = "qp-feedback-audio-mute-disabled";
	
	private final EventsBus eventBus;
	
	private boolean isMuted;
	
	private boolean isVisible;
	
	@Inject
	public FeedbackAudioMuteButtonModule(EventsBus eventBus){
		this.eventBus = eventBus;
		this.eventBus.addHandler(FeedbackEvent.getType(FeedbackEventTypes.MUTE), this);
		this.eventBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_LOADED), this);
	}
	
	@Override
	protected void invokeRequest() {
		FeedbackEvent event = new FeedbackEvent(FeedbackEventTypes.MUTE, !isMuted, null);
		eventBus.fireEvent(event);
	}

	@Override
	protected String getStyleName() {
		final StringBuilder styleName = new StringBuilder(70);
		String styleSuffix = (isMuted) ? STYLE_NAME__ON : STYLE_NAME__OFF; 
		styleName.append(styleSuffix);
		if (!isVisible) {
			styleName.append(' ').append(STYLE_NAME__DISABLED);
		}
		return styleName.toString();
	}

	protected boolean hasItemInteractiveModules() {
		boolean isVisible = false; 
		if (getModuleSocket() != null) {
			isVisible = getModuleSocket().hasInteractiveModules();						
		}	
		return isVisible;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		//handling events moved to event bus
	}

	@Override
	public void onFeedbackEvent(FeedbackEvent event) {
		isMuted = event.isMuted();
		updateStyleName();
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		isVisible = hasItemInteractiveModules();
		updateStyleName();		
	}
}
