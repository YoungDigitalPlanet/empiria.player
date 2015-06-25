package eu.ydp.empiria.player.client.controller.flow;

import com.google.gwt.junit.client.GWTTestCase;
import eu.ydp.empiria.player.RunOutsideTestSuite;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.external.ExternalEventDispatcher;

@RunOutsideTestSuite
public class MainFlowProcessorGWTTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	private MainFlowProcessor getMainFlowProcessor5TestPages() {
		EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
		ExternalEventDispatcher externalEventDispatcher = new ExternalEventDispatcher();
		MainFlowProcessor mfp = new MainFlowProcessor(eventsBus, externalEventDispatcher);
		mfp.setFlowOptions(new FlowOptions(false, false, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
		mfp.init(5);
		mfp.initFlow();
		return mfp;
	}

	public void testGotoFirstPage() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoFirstPage();
		assertEquals(0, mfp.getCurrentPageIndex());
	}

	public void testGotoLastPage() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoLastPage();
		assertEquals(4, mfp.getCurrentPageIndex());
	}

	public void testGotoIndex() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoPage(2);
		assertEquals(2, mfp.getCurrentPageIndex());
	}

	public void testNextPreviousPage() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		assertEquals(0, mfp.getCurrentPageIndex());
		for (int i = 1; i < 5; i++) {
			mfp.nextPage();
			assertEquals(i, mfp.getCurrentPageIndex());
		}
		for (int i = 3; i >= 0; i--) {
			mfp.previousPage();
			assertEquals(i, mfp.getCurrentPageIndex());
		}
	}

	public void testCheck() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.checkPage();
		assertEquals(true, mfp.getFlowFlagCheck());
	}

	public void testLock() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.lockPage();
		assertEquals(true, mfp.getFlowFlagLock());
	}

	public void testUnlock() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.lockPage();
		mfp.unlockPage();
		assertEquals(false, mfp.getFlowFlagLock());
	}

	public void testShowAnswers() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.showAnswersPage();
		assertEquals(true, mfp.getFlowFlagShowAnswers());
	}

	public void testContinue() {
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.showAnswersPage();
		mfp.continuePage();
		assertEquals(false, mfp.getFlowFlagShowAnswers());
		assertEquals(false, mfp.getFlowFlagCheck());
		mfp.checkPage();
		mfp.continuePage();
		assertEquals(false, mfp.getFlowFlagShowAnswers());
		assertEquals(false, mfp.getFlowFlagCheck());
	}

}
