package eu.ydp.empiria.player.client.controller.flow;

import com.google.gwt.junit.client.GWTTestCase;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;

public class MainFlowProcessorTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}
	
	private FlowProcessingEvent lastEvent;
	
	private MainFlowProcessor getMainFlowProcessor5TestPages(){
		MainFlowProcessor mfp = new MainFlowProcessor(new FlowProcessingEventsListener() {
			
			@Override
			public void onFlowProcessingEvent(FlowProcessingEvent event) {
				lastEvent = event;
			}
		});
		
		mfp.setFlowOptions(new FlowOptions(false, false, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
		mfp.init(5);
		mfp.initFlow();
		return mfp;		
	}

	public void testGotoFirstPage(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoFirstPage();
		assertEquals(0, mfp.getCurrentPageIndex());
		assertEquals(FlowProcessingEventType.PAGE_LOADED, lastEvent.getType());
	}

	public void testGotoLastPage(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoLastPage();
		assertEquals(4, mfp.getCurrentPageIndex());		
		assertEquals(FlowProcessingEventType.PAGE_LOADED, lastEvent.getType());
	}

	public void testGotoIndex(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.gotoPage(2);
		assertEquals(2, mfp.getCurrentPageIndex());		
		assertEquals(FlowProcessingEventType.PAGE_LOADED, lastEvent.getType());
	}

	public void testNextPreviousPage(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		assertEquals(0, mfp.getCurrentPageIndex());
		for (int i = 1 ; i < 5 ; i ++){			
			mfp.nextPage();
			assertEquals(i, mfp.getCurrentPageIndex());
			assertEquals(FlowProcessingEventType.PAGE_LOADED, lastEvent.getType());
		}
		for (int i = 3 ; i >= 0 ; i --){			
			mfp.previousPage();
			assertEquals(i, mfp.getCurrentPageIndex());
			assertEquals(FlowProcessingEventType.PAGE_LOADED, lastEvent.getType());
		}				
	}

	public void testCheck(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.checkPage();
		assertEquals(true, mfp.getFlowFlagCheck());		
	}

	public void testLock(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.lockPage();
		assertEquals(true, mfp.getFlowFlagLock());		
	}
	
	public void testUnlock(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.lockPage();
		mfp.unlockPage();
		assertEquals(false, mfp.getFlowFlagLock());		
	}

	public void testShowAnswers(){
		MainFlowProcessor mfp = getMainFlowProcessor5TestPages();
		mfp.showAnswersPage();
		assertEquals(true, mfp.getFlowFlagShowAnswers());		
	}
	
	public void testContinue(){
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
