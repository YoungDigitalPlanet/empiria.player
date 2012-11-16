package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;

public class SoundProcessorManagerExtension extends InternalExtension implements DeliveryEventsListenerExtension {

	private MediaProcessorExtension processor;
	
	public void setSoundProcessorExtension(MediaProcessorExtension processor){
		this.processor = processor;
	}

	@Override
	public void init() {
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		if (processor != null)
			processor.onDeliveryEvent(flowEvent);
	}

}
