package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.core.client.GWT;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;

public class DefaultSoundProcessorExtension extends InternalExtension implements DeliveryEventsListenerExtension, SoundExecutorListener {

	protected SoundExecutor soundExecutor;
	protected MediaInteractionSoundEventCallback callback;

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
		}
		if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND  ||  deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY){

			if (deliveryEvent.getParams().containsKey("url")  &&  deliveryEvent.getParams().get("url") instanceof String){
				String url = (String)deliveryEvent.getParams().get("url");

				soundExecutor.stop();
				
				callback = null;
				if (deliveryEvent.getParams().containsKey("callback")  &&  deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback){
					callback = ((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback"));
				}
				
				callback.setCallforward(new MediaInteractionSoundEventCallforward() {
					
					@Override
					public void stop() {
						forceStop();
					}
				});
				
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
			callback.setCallforward(new MediaInteractionSoundEventCallforward() {
				@Override
				public void stop() {
					forceStop();
				}
			});
		}
		
	}

}
