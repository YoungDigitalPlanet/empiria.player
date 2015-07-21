package eu.ydp.empiria.player.client.controller.flow.request;

import eu.ydp.empiria.player.client.controller.flow.processing.IFlowRequestProcessor;

import java.util.ArrayList;
import java.util.List;

public class MainFlowRequestInvoker implements FlowRequestInvoker {

    protected List<IFlowRequestProcessor> processors;

    public MainFlowRequestInvoker() {
        processors = new ArrayList<>();
    }

    public void addRequestProcessor(IFlowRequestProcessor processor) {
        processors.add(0, processor);
    }

    @Override
    public void invokeRequest(IFlowRequest request) {
        if (request == null)
            return;
        for (IFlowRequestProcessor currProcessor : processors) {
            if (currProcessor.isRequestSupported(request)) {
                currProcessor.processRequest(request);
                return;
            }
        }
    }
}
