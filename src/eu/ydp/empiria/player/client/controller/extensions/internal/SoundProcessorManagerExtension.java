package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;

public class SoundProcessorManagerExtension extends InternalExtension implements DeliveryEventsListenerExtension {

	private SoundProcessorExtension processor;
	
	public void setSoundProcessorExtension(SoundProcessorExtension processor){
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
