package eu.ydp.empiria.player.client.controller.extensions;

import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.FlowRequestProcessorExtensionTest.MockFlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.InteractionEventSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public class InteractionEventSocketUserExtensionTest extends ExtensionTestBase {

	protected String test = "";
	protected int counter = 0;

	protected DeliveryEngine de;
	protected InteractionEventsListener iel;

	public void testStateChangedEvent(){
		de = initDeliveryEngine(new MockInteractionStateChangedEventSocketUserExtension(), false);
		test = "testStateChangedEvent";
		iel.onStateChanged(new StateChangedInteractionEvent(true, new IUniqueModule() {

			@Override
			public String getIdentifier() {
				return "RESPONSE1";
			}
			@Override
			public HasParent getParentModule() {
				return null;
			}
		}));
		assertEquals(1, counter);
	}

	public void testFeedbackInteractionSoundEvent(){
		de = initDeliveryEngine(new MockInteractionSoundEventSocketUserExtension(), false);
		test = "testFeedbackInteractionSoundEvent";
		iel.onFeedbackSound(new FeedbackInteractionSoundEvent("http://www.ydp.eu./xxx.mp3"));
		assertEquals(1, counter);
	}

	public void testMediaInteractionSoundEvent(){
		de = initDeliveryEngine(new MockInteractionSoundEventSocketUserExtension(), false);
		test = "testMediaInteractionSoundEvent";
		iel.onMediaSound(new MediaInteractionSoundEvent("http://www.ydp.eu./xxx.mp3", new MediaInteractionSoundEventCallback() {

			@Override
			public void setCallforward(MediaInteractionSoundEventCallforward callforward) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStop() {
				counter++;
			}

			@Override
			public void onPlay() {
				counter++;
			}
		}));
		assertEquals(3, counter);
	}

	protected void onDeliveryEventMain(DeliveryEvent deliveryEvent){
		if (test.equals("testStateChangedEvent")){
			assertEquals(DeliveryEventType.STATE_CHANGED, deliveryEvent.getType());
			assertEquals(2, deliveryEvent.getParams().size());
			assertTrue(deliveryEvent.getParams().containsKey("userInteract"));
			assertEquals(deliveryEvent.getParams().get("userInteract"), Boolean.TRUE);
			assertTrue(deliveryEvent.getParams().containsKey("sender"));
			assertTrue(deliveryEvent.getParams().get("sender") instanceof IUniqueModule);
			assertEquals(((IUniqueModule)deliveryEvent.getParams().get("sender")).getIdentifier(), "RESPONSE1");
			counter = 1;
		} else if (test.equals("testFeedbackInteractionSoundEvent")){
			assertEquals(DeliveryEventType.FEEDBACK_SOUND, deliveryEvent.getType());
			assertEquals(1, deliveryEvent.getParams().size());
			assertTrue(deliveryEvent.getParams().containsKey("url"));
			assertEquals(deliveryEvent.getParams().get("url"), "http://www.ydp.eu./xxx.mp3");
			counter = 1;
		} else if (test.equals("testMediaInteractionSoundEvent")){
			assertEquals(DeliveryEventType.MEDIA_SOUND_PLAY, deliveryEvent.getType());
			assertEquals(2, deliveryEvent.getParams().size());
			assertTrue(deliveryEvent.getParams().containsKey("url"));
			assertEquals(deliveryEvent.getParams().get("url"), "http://www.ydp.eu./xxx.mp3");
			assertTrue(deliveryEvent.getParams().containsKey("callback"));
			assertTrue(deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback);
			((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback")).onPlay();
			((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback")).onStop();
			counter++;
		}
	}


	protected class MockInteractionEventSocketUserExtension extends InternalExtension implements DeliveryEventsListener, InteractionEventSocketUserExtension{

		@Override
		public void init() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
			onDeliveryEventMain(deliveryEvent);
		}

		@Override
		public void setInteractionEventsListener(InteractionEventsListener listener) {
			iel = listener;
		}

	}

	protected class MockInteractionStateChangedEventSocketUserExtension extends MockInteractionEventSocketUserExtension implements DeliveryEventsListenerExtension{

	}

	protected class MockInteractionSoundEventSocketUserExtension extends MockInteractionEventSocketUserExtension implements SoundProcessorExtension{

	}
}
