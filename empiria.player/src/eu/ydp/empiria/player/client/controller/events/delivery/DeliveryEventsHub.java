package eu.ydp.empiria.player.client.controller.events.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventsHandler;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListner;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEventListener;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;

/**
 * Translates FlowExecutionEvents into
 * 
 * @author Rafal Rybacki
 *
 */
public class DeliveryEventsHub implements FlowProcessingEventsListener, DeliveryEventsListener, InteractionEventsSocket {

	protected List<FlowActivityEventsHandler> flowActivityEventsListeners;
	protected List<DeliveryEventsListener> deliveryEventsListeners;
	protected List<StateChangedInteractionEventListener> stateChangedInteractionEventsListeners;
	
	
	protected Map<DeliveryEventType, FlowActivityEventType> map;
	
	public DeliveryEventsHub(){
		flowActivityEventsListeners = new ArrayList<FlowActivityEventsHandler>();
		deliveryEventsListeners = new ArrayList<DeliveryEventsListener>();
		stateChangedInteractionEventsListeners = new ArrayList<StateChangedInteractionEventListener>();
		
		map = new HashMap<DeliveryEventType, FlowActivityEventType>();
		map.put(DeliveryEventType.CHECK, FlowActivityEventType.CHECK);
	}
	
	public void addDeliveryEventsListener(DeliveryEventsListener del){
		deliveryEventsListeners.add(del);
	}
	
	public void addFlowActivityEventsListener(FlowActivityEventsHandler fael){
		flowActivityEventsListeners.add(fael);
	}

	@Override
	public void addStateChangedInteractionEventsListener(StateChangedInteractionEventListener listener) {
		stateChangedInteractionEventsListeners.add(listener);
	}
	@Override
	public void removeStateChangedInteractionEventsListener(StateChangedInteractionEventListener listener) {
		stateChangedInteractionEventsListeners.remove(listener);
	}
	
	public InteractionEventsSocket getInteractionSocket(){
		return this;
	}
	
	@Override
	public void onDeliveryEvent(DeliveryEvent event) {
			
		for (FlowActivityEventType currFaet : FlowActivityEventType.values()){
			if (currFaet.toString().equals(event.getType().toString())){
				for (FlowActivityEventsHandler currFael : flowActivityEventsListeners){
					currFael.handleFlowActivityEvent(new FlowActivityEvent(currFaet));
				}
				break;
			}
		}
		
		for (DeliveryEventsListener currDel : deliveryEventsListeners){
			currDel.onDeliveryEvent(event);
		}
	}

	@Override
	public void onFlowExecutionEvent(FlowProcessingEvent event) {
			
		for (FlowActivityEventType currFaet : FlowActivityEventType.values()){
			if (currFaet.toString().equals(event.getType().toString())){
				for (FlowActivityEventsHandler currFael : flowActivityEventsListeners){
					currFael.handleFlowActivityEvent(new FlowActivityEvent(currFaet));
				}
				break;
			}
		}
			
		for (DeliveryEventType currDet : DeliveryEventType.values()){
			if (currDet.toString().equals(event.getType().toString())){
				for (DeliveryEventsListener currDel : deliveryEventsListeners){
					currDel.onDeliveryEvent(new DeliveryEvent(currDet));
				}
				break;
			}
		}
		
	}

	@Override
	public void onStateChanged(StateChangedInteractionEvent event) {
		onInteractionEvent(event);
	}

	@Override
	public void onFeedbackSound(FeedbackInteractionSoundEvent event) {
		onInteractionEvent(event);
	}

	@Override
	public void onMediaSound(MediaInteractionSoundEvent event) {
		onInteractionEvent(event);
	}
	
	protected void onInteractionEvent(InteractionEvent event){

		if (event instanceof StateChangedInteractionEvent){
			for (StateChangedInteractionEventListener listener : stateChangedInteractionEventsListeners){
				listener.onStateChanged( (StateChangedInteractionEvent)event );
			}
		}		

		for (DeliveryEventType currDet : DeliveryEventType.values()){
			if (currDet.toString().equals(event.getType().toString())){
				for (DeliveryEventsListener currDel : deliveryEventsListeners){
					DeliveryEvent currDE = new DeliveryEvent(currDet, event.getParams());
					currDel.onDeliveryEvent(currDE);
				}
			}
		}
		
	}

}
