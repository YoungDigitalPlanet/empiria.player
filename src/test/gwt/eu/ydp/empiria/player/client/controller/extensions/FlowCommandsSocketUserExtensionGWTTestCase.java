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
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;

public class FlowCommandsSocketUserExtensionGWTTestCase extends ExtensionGWTTestCase {

    protected DeliveryEngine de;
    protected FlowCommandsExecutor fce;
    protected FlowDataSupplier fds;

    public void testNavigation() {
        de = initDeliveryEngine(new MockFlowCommandsSocketUserExtension());

        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fce.executeCommand(new FlowCommand.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        fce.executeCommand(new FlowCommand.NavigateNextItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(1, fds.getCurrentPageIndex());

        fce.executeCommand(new FlowCommand.NavigateSummary());
        assertEquals(PageType.SUMMARY, fds.getCurrentPageType());

        fce.executeCommand(new FlowCommand.NavigateToc());
        assertEquals(PageType.TOC, fds.getCurrentPageType());

    }

    public void testActivity() {
        de = initDeliveryEngine(new MockFlowCommandsSocketUserExtension());

        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fce.executeCommand(new FlowCommand.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        fce.executeCommand(new FlowCommand.Check());
        assertTrue(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.Continue());
        assertFalse(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.ShowAnswers());
        assertTrue(fds.getFlowFlagShowAnswers());

        fce.executeCommand(new FlowCommand.Continue());
        assertFalse(fds.getFlowFlagShowAnswers());

        fce.executeCommand(new FlowCommand.Check());
        assertTrue(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.ShowAnswers());
        assertFalse(fds.getFlowFlagCheck());
        assertTrue(fds.getFlowFlagShowAnswers());

        fce.executeCommand(new FlowCommand.Check());
        assertFalse(fds.getFlowFlagShowAnswers());
        assertTrue(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.ShowAnswers());
        assertFalse(fds.getFlowFlagCheck());
        assertTrue(fds.getFlowFlagShowAnswers());

        fce.executeCommand(new FlowCommand.Reset());
        assertFalse(fds.getFlowFlagCheck());
        assertFalse(fds.getFlowFlagShowAnswers());

        fce.executeCommand(new FlowCommand.Check());
        assertFalse(fds.getFlowFlagShowAnswers());
        assertTrue(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.Reset());
        assertFalse(fds.getFlowFlagCheck());
        assertFalse(fds.getFlowFlagShowAnswers());

    }

    public void testActivityAndNavigation() {
        de = initDeliveryEngine(new MockFlowCommandsSocketUserExtension());

        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fce.executeCommand(new FlowCommand.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        fce.executeCommand(new FlowCommand.Check());
        assertTrue(fds.getFlowFlagCheck());

        fce.executeCommand(new FlowCommand.NavigateToc());
        assertEquals(PageType.TOC, fds.getCurrentPageType());

        fce.executeCommand(new FlowCommand.NavigateFirstItem());
        assertEquals(PageType.TEST, fds.getCurrentPageType());
        assertEquals(0, fds.getCurrentPageIndex());

        assertFalse(fds.getFlowFlagCheck());

    }

    protected class MockFlowCommandsSocketUserExtension extends InternalExtension implements FlowCommandsSocketUserExtension, FlowDataSocketUserExtension {

        @Override
        public void init() {

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
