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

package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class FlowRequestFactory {

    public IFlowRequest create(JSONArray state, Integer initialItemIndex) {
        IFlowRequest flowRequest = null;

        if (initialItemIndex != null) {
            flowRequest = new FlowRequest.NavigateGotoItem(initialItemIndex);
        } else if (state != null) {

            JSONValue firstState = state.get(0);
            if (firstState.isNumber() != null) {
                flowRequest = getFlowRequestFromNumber(firstState);
            } else if (firstState.isString() != null) {
                flowRequest = getFlowRequestFromString(firstState);
            }
        }
        return flowRequest;
    }

    private IFlowRequest getFlowRequestFromString(JSONValue firstState) {
        IFlowRequest flowRequest = null;
        String firstStateString = firstState.isString().stringValue();

        if (firstStateString.equals(PageType.TOC.toString())) {
            flowRequest = new FlowRequest.NavigateToc();
        } else if (firstStateString.equals(PageType.SUMMARY.toString())) {
            flowRequest = new FlowRequest.NavigateSummary();
        }
        return flowRequest;
    }

    private IFlowRequest getFlowRequestFromNumber(JSONValue firstState) {
        int itemIndex = (int) firstState.isNumber().doubleValue();
        return new FlowRequest.NavigateGotoItem(itemIndex);
    }
}
