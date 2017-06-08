/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
