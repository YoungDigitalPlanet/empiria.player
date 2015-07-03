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
