package eu.ydp.empiria.player.client.controller.events.delivery;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.*;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Translates FlowExecutionEvents into
 *
 * @author Rafal Rybacki
 */
public class DeliveryEventsHub implements DeliveryEventsListener, InteractionEventsSocket {

    private List<DeliveryEventsListener> deliveryEventsListeners;
    private List<StateChangedInteractionEventListener> stateChangedInteractionEventsListeners;
    private Map<DeliveryEventType, FlowActivityEventType> map;

    @Inject
    public DeliveryEventsHub() {
        deliveryEventsListeners = new ArrayList<>();
        stateChangedInteractionEventsListeners = new ArrayList<>();

        map = new HashMap<>();
        map.put(DeliveryEventType.CHECK, FlowActivityEventType.CHECK);

    }

    public void addDeliveryEventsListener(DeliveryEventsListener del) {
        deliveryEventsListeners.add(del);
    }

    @Override
    public void addStateChangedInteractionEventsListener(StateChangedInteractionEventListener listener) {
        stateChangedInteractionEventsListeners.add(listener);
    }

    @Override
    public void removeStateChangedInteractionEventsListener(StateChangedInteractionEventListener listener) {
        stateChangedInteractionEventsListeners.remove(listener);
    }

    public InteractionEventsSocket getInteractionSocket() {
        return this;
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent event) {
        for (DeliveryEventsListener currDel : deliveryEventsListeners) {
            currDel.onDeliveryEvent(event);
        }
    }

    public void onFlowProcessingEvent(FlowProcessingEvent event) {
        for (DeliveryEventsListener currDel : deliveryEventsListeners) {
            DeliveryEvent currDe = DeliveryEvent.fromFlowProcessingEvent(event);
            if (currDe != null) {
                currDel.onDeliveryEvent(currDe);
            }
        }
    }

    @Override
    public void onStateChanged(StateChangedInteractionEvent event) {
        onInteractionEvent(event);
    }

    @Override
    public void onFeedbackSound(FeedbackInteractionEvent event) {
        onInteractionEvent(event);
    }

    @Override
    public void onMediaSound(MediaInteractionSoundEvent event) {
        onInteractionEvent(event);
    }

    protected void onInteractionEvent(InteractionEvent event) {

        if (event instanceof StateChangedInteractionEvent) {
            for (StateChangedInteractionEventListener listener : stateChangedInteractionEventsListeners) {
                listener.onStateChanged((StateChangedInteractionEvent) event);
            }
        }

        for (DeliveryEventType currDet : DeliveryEventType.values()) {
            if (currDet.toString().equals(event.getType().toString())) {
                for (DeliveryEventsListener currDel : deliveryEventsListeners) {
                    DeliveryEvent currDE = new DeliveryEvent(currDet, event.getParams());
                    currDel.onDeliveryEvent(currDE);
                }
            }
        }

    }

}
