package eu.ydp.empiria.player.client.controller.flow;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.execution.MainFlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.IFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.MainFlowRequestInvoker;

public final class FlowManager {

    private final MainFlowProcessor flowProcessor;
    private final MainFlowRequestInvoker mainFlowRequestInvoker;
    private final MainFlowCommandsExecutor flowCommandsExecutor;

    @Inject
    public FlowManager(MainFlowProcessor flowProcessor, MainFlowRequestInvoker mainFlowRequestInvoker, MainFlowCommandsExecutor flowCommandsExecutor) {
        this.flowProcessor = flowProcessor;
        this.mainFlowRequestInvoker = mainFlowRequestInvoker;
        this.flowCommandsExecutor = flowCommandsExecutor;
    }

    public void init(int itemsCount) {
        flowProcessor.init(itemsCount);
    }

    public void initFlow() {
        flowProcessor.initFlow();
    }

    public void deinitFlow() {
        flowProcessor.deinitFlow();
    }

    public void setFlowOptions(FlowOptions oprions) {
        flowProcessor.setFlowOptions(oprions);
    }

    public void addCommandProcessor(IFlowRequestProcessor processor) {
        mainFlowRequestInvoker.addRequestProcessor(processor);
    }

    public PageType getCurrentPageType() {
        return flowProcessor.getCurrentPageType();
    }

    public int getCurrentPageIndex() {
        return flowProcessor.getCurrentPageIndex();
    }

    public PageReference getPageReference() {
        return flowProcessor.getPageReference();
    }

    public FlowCommandsExecutor getFlowCommandsExecutor() {
        return flowCommandsExecutor;
    }

    public FlowDataSupplier getFlowDataSupplier() {
        return flowProcessor;
    }

    public FlowRequestInvoker getFlowRequestInvoker() {
        return mainFlowRequestInvoker;
    }

    public void invokeFlowRequest(IFlowRequest request) {
        mainFlowRequestInvoker.invokeRequest(request);
    }

    public void setDisplayOptions(DisplayOptions options) {
        flowProcessor.setDisplayOptions(options);
    }

    public DisplayOptions getDisplayOptions() {
        return flowProcessor.getDisplayOptions();
    }

    public IFlowSocket getFlowSocket() {
        return new IFlowSocket() {

            @Override
            public void invokeRequest(IFlowRequest command) {
                mainFlowRequestInvoker.invokeRequest(command);
            }
        };
    }

    public JSONValue getState() {
        JSONValue state;
        if (getCurrentPageType() == PageType.TEST) {
            state = new JSONNumber(getCurrentPageIndex());
        } else if (getCurrentPageType() == PageType.TOC || getCurrentPageType() == PageType.SUMMARY) {
            state = new JSONString(getCurrentPageType().toString());
        } else {
            state = JSONNull.getInstance();
        }
        return state;
    }
}
