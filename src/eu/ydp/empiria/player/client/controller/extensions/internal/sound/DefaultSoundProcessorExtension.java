package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.core.client.GWT;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;

public class DefaultSoundProcessorExtension extends InternalExtension implements SoundProcessorExtension, SoundExecutorListener {

	protected SoundExecutor soundExecutor;
	protected MediaInteractionSoundEventCallback callback;
	protected boolean muteFeedbacks = false;

	public DefaultSoundProcessorExtension(){
	}
	
	@Override
	public void init() {
		
		soundExecutor = GWT.create(SoundExecutor.class);
		soundExecutor.setSoundFinishedListener(this);
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING){
			forceStop();
		} else if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_MUTE){
			if (deliveryEvent.getParams().containsKey("mute")  &&  deliveryEvent.getParams().get("mute") instanceof Boolean){
				muteFeedbacks = (Boolean)deliveryEvent.getParams().get("mute");
			}
		}		
		else if ((deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND && !muteFeedbacks) ||  deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY){

			if (deliveryEvent.getParams().containsKey("url")  &&  deliveryEvent.getParams().get("url") instanceof String){
				String url = (String)deliveryEvent.getParams().get("url");

				soundExecutor.stop();
				
				callback = null;
				if (deliveryEvent.getParams().containsKey("callback")  &&  deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback){
					callback = ((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback"));
				}
				
				if (callback != null){
					callback.setCallforward(new MediaInteractionSoundEventCallforward() {
						
						@Override
						public void stop() {
							forceStop();
						}
					});
				}
				
				soundExecutor.play(url);
				
			}
		}
	}
	
	protected void forceStop(){
		soundExecutor.stop();		
	}

	@Override
	public void onSoundFinished() {
		if (callback != null)
			callback.onStop();
		
	}

	@Override
	public void onPlay() {

		if (callback != null){
			callback.onPlay();
		}
		
	}

}
