package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;

public class DefaultSoundProcessorExtension extends InternalExtension implements DeliveryEventsListenerExtension {


	@Override
	public void init() {
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND){

			if (deliveryEvent.getParams().containsKey("url")  &&  deliveryEvent.getParams().get("url") instanceof String){
				String url = (String)deliveryEvent.getParams().get("url");
				
				SoundController ctrl = new SoundController();
				Sound sound = ctrl.createSound(Sound.MIME_TYPE_AUDIO_MPEG, url);
				sound.play();
			}
		}
	}

}
