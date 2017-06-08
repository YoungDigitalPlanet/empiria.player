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

package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class ShowAnswersButtonModule extends AbstractActivityButtonModule implements PlayerEventHandler {

    protected boolean isSelected = false;

    @Inject
    protected EventsBus eventsBus;

    @Override
    public void initModule(Element element) {
        super.initModule(element);
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGING), this);
    }

    @Override
    protected void invokeRequest() {
        if (isSelected) {
            flowRequestInvoker.invokeRequest(new FlowRequest.Continue(getCurrentGroupIdentifier()));
        } else {
            flowRequestInvoker.invokeRequest(new FlowRequest.ShowAnswers(getCurrentGroupIdentifier()));
        }
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (isSelected && event.getType() == PlayerEventTypes.PAGE_CHANGING) {
            flowRequestInvoker.invokeRequest(new FlowRequest.Continue(getCurrentGroupIdentifier()));
        }
    }

    @Override
    protected String getStyleName() {
        return "qp-" + (isSelected ? "hideanswers" : "showanswers") + "-button";
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent flowEvent) {
        Object groupIdentifier = flowEvent.getParams().get("groupIdentifier");
        switch (flowEvent.getType()) {
            case SHOW_ANSWERS:
                if (groupIdentifier == null || currentGroupIsConcerned((GroupIdentifier) groupIdentifier)) {
                    isSelected = true;
                }
                break;
            case CONTINUE:
            case CHECK:
            case RESET:
                if (groupIdentifier == null || currentGroupIsConcerned((GroupIdentifier) groupIdentifier)) {
                    isSelected = false;
                }
                break;
            default:
                break;
        }
        updateStyleName();
    }

}
