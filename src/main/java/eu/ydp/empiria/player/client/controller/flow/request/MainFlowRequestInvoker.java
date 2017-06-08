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
