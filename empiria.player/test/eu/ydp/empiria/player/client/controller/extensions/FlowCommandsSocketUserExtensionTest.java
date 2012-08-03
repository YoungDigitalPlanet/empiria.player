package eu.ydp.empiria.player.client.controller.extensions;

import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;

public class FlowCommandsSocketUserExtensionTest extends ExtensionTestBase {

	protected DeliveryEngine de;
	protected FlowCommandsExecutor fce;
	protected FlowDataSupplier fds;

	public void testNavigation(){
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

	public void testActivity(){
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

	public void testActivityAndNavigation(){
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

	protected class MockFlowCommandsSocketUserExtension extends InternalExtension implements FlowCommandsSocketUserExtension, FlowDataSocketUserExtension{

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
