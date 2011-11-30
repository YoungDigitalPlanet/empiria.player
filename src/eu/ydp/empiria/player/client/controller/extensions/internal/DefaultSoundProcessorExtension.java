package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.Sound.LoadState;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;

public class DefaultSoundProcessorExtension extends InternalExtension implements DeliveryEventsListenerExtension {

	protected Sound currSound;
	protected SoundHandler currSoundHandler;
	protected boolean playing;

	public DefaultSoundProcessorExtension(){
		playing = false;
	}
	
	@Override
	public void init() {
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING){
			if (playing)
				forceStop();
		}
		if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND  ||  deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY){

			if (deliveryEvent.getParams().containsKey("url")  &&  deliveryEvent.getParams().get("url") instanceof String){
				String url = (String)deliveryEvent.getParams().get("url");

				MediaInteractionSoundEventCallback callback = null;
				if (deliveryEvent.getParams().containsKey("callback")  &&  deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback){
					callback = ((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback"));
				}
				final MediaInteractionSoundEventCallback callback2 = callback;
				
				if (playing)
					forceStop();
				
				SoundController ctrl = new SoundController();
				currSound = ctrl.createSound(Sound.MIME_TYPE_AUDIO_MPEG, url);				
						
				currSoundHandler = new SoundHandler() {
					
					@Override
					public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
						if (event.getLoadState() == LoadState.LOAD_STATE_SUPPORTED_AND_READY){
							if (callback2 != null){
								playing = true;
								callback2.onPlay();
								callback2.setCallforward(new MediaInteractionSoundEventCallforward() {
									@Override
									public void stop() {
										forceStop();
									}
								});
							}
						}
					}
					
					@Override
					public void onPlaybackComplete(PlaybackCompleteEvent event) {
						if (callback2 != null)
							callback2.onStop();
						playing = false;
					}
				};
				
				currSound.addEventHandler(currSoundHandler);
				currSound.play();
				
			}
		}
	}
	
	protected void forceStop(){
		if (currSound != null)
			currSound.stop();
		if (currSoundHandler != null)
			currSoundHandler.onPlaybackComplete(null);
		
	}

}
