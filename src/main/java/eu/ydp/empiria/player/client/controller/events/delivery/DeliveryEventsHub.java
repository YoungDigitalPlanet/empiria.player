package eu.ydp.empiria.player.client.controller.events.delivery;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListener;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DeliveryEventsHub implements DeliveryEventsListener, FeedbackInteractionEventListener {

    private List<DeliveryEventsListener> deliveryEventsListeners;
    private Map<DeliveryEventType, FlowActivityEventType> map;

    public DeliveryEventsHub() {
        deliveryEventsListeners = new ArrayList<>();

        map = new HashMap<>();
        map.put(DeliveryEventType.CHECK, FlowActivityEventType.CHECK);

    }

    public void addDeliveryEventsListener(DeliveryEventsListener del) {
        deliveryEventsListeners.add(del);
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
    public void onFeedbackSound(FeedbackInteractionEvent event) {
        InteractionEventType eventType = event.getType();
        DeliveryEventType deliveryEventType = DeliveryEventType.valueOf(eventType.toString());

        for (DeliveryEventsListener currDel : deliveryEventsListeners) {
            DeliveryEvent currDE = new DeliveryEvent(deliveryEventType, event.getParams());
            currDel.onDeliveryEvent(currDE);
        }
    }
}
