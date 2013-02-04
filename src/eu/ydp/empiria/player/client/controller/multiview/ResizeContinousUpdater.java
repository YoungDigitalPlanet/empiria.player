package eu.ydp.empiria.player.client.controller.multiview;

import java.util.logging.Logger;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class ResizeContinousUpdater {
	
	enum ResizeTimerState {WaitingForContent, PageIsGrowing, PageStopedGrowing};

	private static final Logger LOGGER = Logger.getLogger(ResizeContinousUpdater.class.getName());
	static final int DELAY_MILLIS = 200;
	static final int IDLE_DELAY_MILLIS = 500;
	static final int REPEAT_COUNT = 20;
	static final int WAIT_STOP_GROWING_ITERATIONS = 1;
	final PlayerEvent PAGE_CONTENT_RESIZED_EVENT = new PlayerEvent(PlayerEventTypes.PAGE_CONTENT_RESIZED);
	
	
	private final PageScopeFactory pageScopeFactory;
	private final EventsBus eventsBus;

	private ResizeTimerState timerState = ResizeTimerState.WaitingForContent;
	private int previousPageHeight = 0;
	private int resizeCounter = 0;
	private int pageStopedGrowingCounter = 0;
	
	private MultiPageController pageView;
	
	
	public ResizeContinousUpdater(PageScopeFactory pageScopeFactory, EventsBus eventsBus, MultiPageController pageView) {
		this.pageScopeFactory = pageScopeFactory;
		this.eventsBus = eventsBus;
		this.pageView = pageView;
	}

	public void reset(){
		resizeCounter = 0;
		previousPageHeight = 0;
		pageStopedGrowingCounter = 0;
		timerState = ResizeTimerState.WaitingForContent;
	}
	
	public int runContinousResizeUpdateAndReturnRescheduleTime() {
		int currentVisiblePage = pageView.getCurrentVisiblePage();
		int height = pageView.getHeightForPage(currentVisiblePage);
		
		int rescheduleTime = 0;
		
		switch (timerState) {
		case WaitingForContent:
				rescheduleTime = waitForContent(height, currentVisiblePage);
			break;
		case PageIsGrowing:
				rescheduleTime = monitorPageGrowing(height, currentVisiblePage);
			break;
		case PageStopedGrowing:
				rescheduleTime = monitorPageNotStartedGrowing(height);
			break;
		default:
			LOGGER.info("Unknown timerState: "+timerState);
		}
		
		previousPageHeight = height;
		
		return rescheduleTime;
	}

	private int waitForContent(int height, int currentVisiblePage) {
		if(height > 0){
			timerState = ResizeTimerState.PageIsGrowing;
			resizePageContainer(height, currentVisiblePage);
		}
		return DELAY_MILLIS;
	}

	private int monitorPageGrowing(int height, int currentVisiblePage) {
		if(isPageGrowing(height)){
			pageStopedGrowingCounter = 0;
			resizePageContainer(height, currentVisiblePage);
		}else{
			if(pageStopedGrowingCounter >= WAIT_STOP_GROWING_ITERATIONS){
				resizePageContainer(height, currentVisiblePage);
				eventsBus.fireAsyncEvent(PAGE_CONTENT_RESIZED_EVENT, pageScopeFactory.getCurrentPageScope());
				timerState = ResizeTimerState.PageStopedGrowing;
			}
			
			pageStopedGrowingCounter++;
		}
		return DELAY_MILLIS;
	}
	
	private int monitorPageNotStartedGrowing(int height) {
		
		int rescheduleTime = 0;
		if(isPageGrowing(height)){
			timerState = ResizeTimerState.PageIsGrowing;
			pageStopedGrowingCounter = 0;
			resizeCounter = 0;
			rescheduleTime = DELAY_MILLIS;
		}else{
			if(resizeCounter < REPEAT_COUNT){
				rescheduleTime = DELAY_MILLIS;
			}else{
				rescheduleTime = IDLE_DELAY_MILLIS;
			}
			resizeCounter ++;
		}
		
		return rescheduleTime;
	}

	private void resizePageContainer(int height, int currentVisiblePage) {
		pageView.setHeight(height);
		pageView.hideProgressBarForPage(currentVisiblePage);
	}

	private boolean isPageGrowing(int height) {
		return height > previousPageHeight;
	}
	
}
