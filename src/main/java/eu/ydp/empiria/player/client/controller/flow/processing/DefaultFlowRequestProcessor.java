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

package eu.ydp.empiria.player.client.controller.flow.processing;

import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class DefaultFlowRequestProcessor implements IFlowRequestProcessor {

    protected FlowCommandsExecutor flowCommandsExecutor;

    public DefaultFlowRequestProcessor(FlowCommandsExecutor fce) {
        flowCommandsExecutor = fce;
    }

    @Override
    public boolean isRequestSupported(IFlowRequest request) {
        return getCommandByRequest(request) != null;
    }

    @Override
    public void processRequest(IFlowRequest request) {
        IFlowCommand command = getCommandByRequest(request);
        flowCommandsExecutor.executeCommand(command);
    }

    public IFlowCommand getCommandByRequest(IFlowRequest request) {

        IFlowCommand command = null;

        if (request instanceof FlowRequest.NavigateNextItem) {
            command = new FlowCommand.NavigateNextItem();
        } else if (request instanceof FlowRequest.NavigatePreviousItem) {
            command = new FlowCommand.NavigatePreviousItem();
        } else if (request instanceof FlowRequest.NavigateFirstItem) {
            command = new FlowCommand.NavigateFirstItem();
        } else if (request instanceof FlowRequest.NavigateLastItem) {
            command = new FlowCommand.NavigateLastItem();
        } else if (request instanceof FlowRequest.NavigateToc) {
            command = new FlowCommand.NavigateToc();
        } else if (request instanceof FlowRequest.NavigateTest) {
            command = new FlowCommand.NavigateTest();
        } else if (request instanceof FlowRequest.NavigateSummary) {
            command = new FlowCommand.NavigateSummary();
        } else if (request instanceof FlowRequest.NavigateGotoItem) {
            command = new FlowCommand.NavigateGotoItem(((FlowRequest.NavigateGotoItem) request).getIndex());
        } else if (request instanceof FlowRequest.NavigatePreviewItem) {
            command = new FlowCommand.NavigatePreviewItem(((FlowRequest.NavigatePreviewItem) request).getIndex());
        } else if (request instanceof FlowRequest.Check) {
            command = new FlowCommand.Check(((FlowRequest.Check) request).getGroupIdentifier());
        } else if (request instanceof FlowRequest.Continue) {
            command = new FlowCommand.Continue(((FlowRequest.Continue) request).getGroupIdentifier());
        } else if (request instanceof FlowRequest.Reset) {
            command = new FlowCommand.Reset(((FlowRequest.Reset) request).getGroupIdentifier());
        } else if (request instanceof FlowRequest.ShowAnswers) {
            command = new FlowCommand.ShowAnswers(((FlowRequest.ShowAnswers) request).getGroupIdentifier());
        } else if (request instanceof FlowRequest.Lock) {
            command = new FlowCommand.Lock(((FlowRequest.Lock) request).getGroupIdentifier());
        } else if (request instanceof FlowRequest.Unlock) {
            command = new FlowCommand.Unlock(((FlowRequest.Unlock) request).getGroupIdentifier());
        }

        return command;
    }
}
