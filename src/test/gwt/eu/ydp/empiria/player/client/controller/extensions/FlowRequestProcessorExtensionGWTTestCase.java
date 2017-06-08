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

package eu.ydp.empiria.player.client.controller.extensions;

import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class FlowRequestProcessorExtensionGWTTestCase extends ExtensionGWTTestCase {

    protected IFlowCommand[] cmdsNavigation = {new FlowCommand.NavigateFirstItem(), new FlowCommand.NavigateNextItem(), new FlowCommand.NavigateSummary(),
            new FlowCommand.NavigateToc()};

    protected IFlowCommand[] cmdsActivity = {new FlowCommand.NavigateFirstItem(), new FlowCommand.Check(), new FlowCommand.Continue(),
            new FlowCommand.ShowAnswers(), new FlowCommand.Continue(), new FlowCommand.Check(), new FlowCommand.ShowAnswers(), new FlowCommand.Check(),
            new FlowCommand.ShowAnswers(), new FlowCommand.Reset(), new FlowCommand.Check(), new FlowCommand.Reset()};

    protected IFlowCommand[] cmds;

    protected int counter = 0;

    protected DeliveryEngine de;
    protected FlowRequestInvoker fri;
    protected FlowCommandsExecutor fce;
    protected FlowDataSupplier fds;

    public void testNavigation() {
        cmds = cmdsNavigation;
        de = initDeliveryEngine(new MockFlowRequestProcessorExtension());

        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fri.invokeRequest(new FlowRequest.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        fri.invokeRequest(new FlowRequest.NavigateNextItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(1, fds.getCurrentPageIndex());

        fri.invokeRequest(new FlowRequest.NavigateSummary());
        assertEquals(PageType.SUMMARY, fds.getCurrentPageType());

        fri.invokeRequest(new FlowRequest.NavigateToc());
        assertEquals(PageType.TOC, fds.getCurrentPageType());

        assertEquals(cmdsNavigation.length, counter);

    }

    public void testActivity() {
        cmds = cmdsActivity;
        de = initDeliveryEngine(new MockFlowRequestProcessorExtension());

        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fri.invokeRequest(new FlowRequest.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        fri.invokeRequest(new FlowRequest.Check());
        assertTrue(fds.getFlowFlagCheck());

        fri.invokeRequest(new FlowRequest.Continue());
        assertFalse(fds.getFlowFlagCheck());

        fri.invokeRequest(new FlowRequest.ShowAnswers());
        assertTrue(fds.getFlowFlagShowAnswers());

        fri.invokeRequest(new FlowRequest.Continue());
        assertFalse(fds.getFlowFlagShowAnswers());

        fri.invokeRequest(new FlowRequest.Check());
        assertTrue(fds.getFlowFlagCheck());

        fri.invokeRequest(new FlowRequest.ShowAnswers());
        assertFalse(fds.getFlowFlagCheck());
        assertTrue(fds.getFlowFlagShowAnswers());

        fri.invokeRequest(new FlowRequest.Check());
        assertFalse(fds.getFlowFlagShowAnswers());
        assertTrue(fds.getFlowFlagCheck());

        fri.invokeRequest(new FlowRequest.ShowAnswers());
        assertFalse(fds.getFlowFlagCheck());
        assertTrue(fds.getFlowFlagShowAnswers());

        fri.invokeRequest(new FlowRequest.Reset());
        assertFalse(fds.getFlowFlagCheck());
        assertFalse(fds.getFlowFlagShowAnswers());

        fri.invokeRequest(new FlowRequest.Check());
        assertFalse(fds.getFlowFlagShowAnswers());
        assertTrue(fds.getFlowFlagCheck());

        fri.invokeRequest(new FlowRequest.Reset());
        assertFalse(fds.getFlowFlagCheck());
        assertFalse(fds.getFlowFlagShowAnswers());

        assertEquals(cmdsActivity.length, counter);
    }

    protected void processRequestCheck(IFlowRequest request) {
        if (counter < cmds.length) {
            assertEquals(cmds[counter].getName(), request.getName());
            fce.executeCommand(cmds[counter]);
            counter++;
        }
    }

    protected class MockFlowRequestProcessorExtension extends InternalExtension implements FlowRequestProcessorExtension, FlowRequestSocketUserExtension,
            FlowCommandsSocketUserExtension, FlowDataSocketUserExtension {

        @Override
        public void init() {

        }

        @Override
        public boolean isRequestSupported(IFlowRequest request) {
            return true;
        }

        @Override
        public void processRequest(IFlowRequest request) {
            processRequestCheck(request);
        }

        @Override
        public void setFlowRequestsInvoker(FlowRequestInvoker invoker) {
            fri = invoker;
        }

        @Override
        public void setFlowCommandsExecutor(FlowCommandsExecutor executor) {
            fce = executor;
        }

        @Override
        public void setFlowDataSupplier(FlowDataSupplier supplier) {
            fds = supplier;
        }

    }

}
