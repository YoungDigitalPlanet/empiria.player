package eu.ydp.empiria.player.client.controller.multiview;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.controller.multiview.ResizeContinuousUpdater.ResizeTimerState;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHack;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

public class ResizeContinuousUpdaterJUnitTest {

	private ResizeContinuousUpdater resizeContinuousUpdater;
	private final ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
	private PageScopeFactory pageScopeFactory;
	private ForceRedrawHack forceRedrawHack;
	private EventsBus eventsBus;
	private MultiPageController pageView;
	private final Integer currentVisiblePage = 3;

	@Before
	public void setUp() throws Exception {
		pageView = Mockito.mock(MultiPageController.class);
		eventsBus = Mockito.mock(EventsBus.class);
		pageScopeFactory = Mockito.mock(PageScopeFactory.class);
		forceRedrawHack = mock(ForceRedrawHack.class);
		resizeContinuousUpdater = new ResizeContinuousUpdater(pageScopeFactory, eventsBus, pageView, forceRedrawHack);
	}

	@Test
	public void testWaitForContentAndKeepWaiting() throws Exception {
		int pageHeight = 0;
		expectPageViewInteractions(pageHeight);

		setTimerState(ResizeTimerState.WAITING_FOR_CONTENT, 0, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.WAITING_FOR_CONTENT, 0, 0, 0);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
	}

	@Test
	public void testWaitForContentAndChangeToPageIsGrowing() throws Exception {
		int pageHeight = 123;
		expectPageViewInteractions(pageHeight);

		setTimerState(ResizeTimerState.WAITING_FOR_CONTENT, 0, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_IS_GROWING, pageHeight, 0, 0);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
		verifyPageContainerResizeCalls(pageHeight);
	}

	@Test
	public void testPageIsStillGrowing() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 30;
		expectPageViewInteractions(newPageHeight);

		setTimerState(ResizeTimerState.PAGE_IS_GROWING, previousPageHeight, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_IS_GROWING, newPageHeight, 0, 0);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
		verifyPageContainerResizeCalls(newPageHeight);
		verify(forceRedrawHack).redraw();
	}

	@Test
	public void testPageWasGrowingAndStopped() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 123;
		expectPageViewInteractions(newPageHeight);

		setTimerState(ResizeTimerState.PAGE_IS_GROWING, previousPageHeight, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_IS_GROWING, newPageHeight, 0, 1);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
		verify(forceRedrawHack).redraw();
	}

	@Test
	public void testPageWasGrowingAndStopped_shouldChangeStateToPageStopedGrowing() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 123;
		expectPageViewInteractions(newPageHeight);
		setTimerState(ResizeTimerState.PAGE_IS_GROWING, previousPageHeight, 0, ResizeContinuousUpdater.WAIT_STOP_GROWING_ITERATIONS);

		CurrentPageScope currentPageScope = Mockito.mock(CurrentPageScope.class);
		when(pageScopeFactory.getCurrentPageScope()).thenReturn(currentPageScope);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_STOPED_GROWING, newPageHeight, 0, ResizeContinuousUpdater.WAIT_STOP_GROWING_ITERATIONS + 1);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
		verifyPageResizedEventCorrectlyThrown(currentPageScope);
		verify(forceRedrawHack).redraw();
	}

	@Test
	public void testPageWasNotGrowingAndStartedGrowing() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 30;
		expectPageViewInteractions(newPageHeight);
		setTimerState(ResizeTimerState.PAGE_STOPED_GROWING, previousPageHeight, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_IS_GROWING, newPageHeight, 0, 0);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
	}

	@Test
	public void testPageWasNotGrowingAndStillNotGrowing_schouldScheduleShorterTime() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 123;
		expectPageViewInteractions(newPageHeight);
		setTimerState(ResizeTimerState.PAGE_STOPED_GROWING, previousPageHeight, 0, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_STOPED_GROWING, newPageHeight, 1, 0);
		assertEquals(ResizeContinuousUpdater.DELAY_MILLIS, rescheduleTime);
	}

	@Test
	public void testPageWasNotGrowingAndStillNotGrowingForLongTime_schouldScheduleIdleTime() throws Exception {
		int newPageHeight = 123;
		int previousPageHeight = 123;
		expectPageViewInteractions(newPageHeight);
		setTimerState(ResizeTimerState.PAGE_STOPED_GROWING, previousPageHeight, ResizeContinuousUpdater.REPEAT_COUNT, 0);

		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		validateTimerState(ResizeTimerState.PAGE_STOPED_GROWING, newPageHeight, ResizeContinuousUpdater.REPEAT_COUNT + 1, 0);
		assertEquals(ResizeContinuousUpdater.IDLE_DELAY_MILLIS, rescheduleTime);
	}

	@Test
	public void testResetFunction() throws Exception {
		setTimerState(ResizeTimerState.PAGE_STOPED_GROWING, 132, 123, 123);

		resizeContinuousUpdater.reset();

		validateTimerState(ResizeTimerState.WAITING_FOR_CONTENT, 0, 0, 0);
	}

	private void verifyPageResizedEventCorrectlyThrown(CurrentPageScope currentPageScope) {
		verify(pageScopeFactory).getCurrentPageScope();
		verify(eventsBus).fireAsyncEvent(resizeContinuousUpdater.PAGE_CONTENT_RESIZED_EVENT, currentPageScope);
	}

	private void verifyPageContainerResizeCalls(int pageHeight) {
		Mockito.verify(pageView).setHeight(pageHeight);
		Mockito.verify(pageView).hideProgressBarForPage(currentVisiblePage);
	}

	private void expectPageViewInteractions(int pageHeight) {
		when(pageView.getCurrentVisiblePage()).thenReturn(currentVisiblePage);

		when(pageView.getHeightForPage(currentVisiblePage)).thenReturn(pageHeight);
	}

	@After
	public void tearDown() throws Exception {
	}

	private void setTimerState(ResizeContinuousUpdater.ResizeTimerState timerState, int previousPageHeight, int resizeCounter, int pageStopedGrowingCounter) {
		try {
			reflectionsUtils.setValueInObjectOnField("timerState", resizeContinuousUpdater, timerState);
			reflectionsUtils.setValueInObjectOnField("previousPageHeight", resizeContinuousUpdater, previousPageHeight);
			reflectionsUtils.setValueInObjectOnField("resizeCounter", resizeContinuousUpdater, resizeCounter);
			reflectionsUtils.setValueInObjectOnField("pageStopedGrowingCounter", resizeContinuousUpdater, pageStopedGrowingCounter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void validateTimerState(ResizeContinuousUpdater.ResizeTimerState timerState, int previousPageHeight, int resizeCounter, int pageStopedGrowingCounter)
			throws Exception {
		ResizeContinuousUpdater.ResizeTimerState currentTimerState = (ResizeTimerState) reflectionsUtils.getValueFromFiledInObject("timerState",
				resizeContinuousUpdater);
		int currentPreviousPageHeight = (Integer) reflectionsUtils.getValueFromFiledInObject("previousPageHeight", resizeContinuousUpdater);
		int currentResizeCounter = (Integer) reflectionsUtils.getValueFromFiledInObject("resizeCounter", resizeContinuousUpdater);
		int currentPageStopedGrowingCounter = (Integer) reflectionsUtils.getValueFromFiledInObject("pageStopedGrowingCounter", resizeContinuousUpdater);

		assertEquals(timerState, currentTimerState);
		assertEquals(previousPageHeight, currentPreviousPageHeight);
		assertEquals(resizeCounter, currentResizeCounter);
		assertEquals(pageStopedGrowingCounter, currentPageStopedGrowingCounter);
	}

}
