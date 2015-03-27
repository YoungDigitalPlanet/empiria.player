package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.dom.client.NativeEvent;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.multiview.IMultiPageController;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.TouchEventReader;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TouchControllerTest {
	@Mock
	private WindowDelegate windowDelegate;
	@Mock
	private TouchEventReader touchEventReader;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private RootPanelDelegate rootPanelDelegate;
	@Mock
	private PlayerWorkModeService playerWorkModeService;

	private TouchModel touchModel;

	private TouchController testObj;

	@Before
	public void before() {
		touchModel = new TouchModel();
		MockitoAnnotations.initMocks(this);
		testObj = new TouchController(windowDelegate, touchEventReader, eventsBus, touchModel, rootPanelDelegate, playerWorkModeService);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(windowDelegate, touchEventReader, eventsBus, rootPanelDelegate);
	}

	@Test
	public void updateOnTouchStartTest() {
		assertFalse(touchModel.isVerticalSwipeDetected());
		testObj.setVerticalSwipeDetected(true);
		assertTrue(touchModel.isVerticalSwipeDetected());
	}

	@Test
	public void updateOnTouchStart() {
		// given
		NativeEvent onTouchStartEvent = mock(NativeEvent.class);
		int y = 100;
		when(touchEventReader.getScreenY(onTouchStartEvent)).thenReturn(y);
		int x = 200;
		when(touchEventReader.getX(onTouchStartEvent)).thenReturn(x);
		when(touchEventReader.isMoreThenOneFingerTouch(onTouchStartEvent)).thenReturn(true);
		int scrollTop = 40;
		when(windowDelegate.getScrollTop()).thenReturn(scrollTop);
		// when
		testObj.updateOnTouchStart(onTouchStartEvent);
		// then
		assertEquals(touchModel.getStartScrollTopPossition(), scrollTop);
		assertEquals(touchModel.getStartX(), x);
		assertEquals(touchModel.getStartY(), y);
		assertEquals(touchModel.getLastEndX(), x);
		assertEquals(touchModel.getEndX(), -1);
		assertEquals(touchModel.isMultiTouch(), true);
		assertEquals(touchModel.isSwipeStarted(), false);
		assertEquals(touchModel.isTouchReservation(), false);
		assertEquals(touchModel.isVerticalSwipeDetected(), false);

		InOrder inOrder = inOrder(windowDelegate, touchEventReader, eventsBus);
		inOrder.verify(touchEventReader)
			   .getScreenY(onTouchStartEvent);
		inOrder.verify(touchEventReader)
			   .getX(onTouchStartEvent);
		inOrder.verify(touchEventReader)
			   .isMoreThenOneFingerTouch(onTouchStartEvent);
		inOrder.verify(windowDelegate)
			   .getScrollTop();

	}

	@Test
	public void getSwypePercentLengthTest() {
		touchModel.setLastEndX(50);
		touchModel.setEndX(100);
		when(rootPanelDelegate.getOffsetWidth()).thenReturn(200);

		assertTrue(25.0f == testObj.getSwypePercentLength());

		verify(rootPanelDelegate).getOffsetWidth();
	}

	@Test
	public void canSwitchPageTest_endXNotCorrect() {
		touchModel.setEndX(-1);

		assertFalse(testObj.canSwitchPage());
	}

	@Test
	public void canSwitchPageTest_isNotCorrectSwypeAngle() {
		touchModel.setEndX(10);
		touchModel.setStartX(20);
		touchModel.setEndY(2);
		touchModel.setStartY(10);

		assertFalse(testObj.canSwitchPage());
	}

	@Test
	public void canSwitchPageTest_isNotCorrectSwypeWidth() {
		touchModel.setEndX(10);
		touchModel.setStartX(20);
		touchModel.setEndY(2);
		touchModel.setStartY(3);
		when(windowDelegate.getClientWidth()).thenReturn(400);

		assertFalse(testObj.canSwitchPage());
		verify(windowDelegate).getClientWidth();
	}

	@Test
	public void canSwitchPageTest_isTestModeEnabled() {
		touchModel.setEndX(10);
		touchModel.setStartX(111);
		touchModel.setEndY(2);
		touchModel.setStartY(15);
		when(windowDelegate.getClientWidth()).thenReturn(400);
		testObj.enableTestMode();

		assertFalse(testObj.canSwitchPage());
		verify(windowDelegate).getClientWidth();
	}

	@Test
	public void canSwitchPageTest_isTrue() {
		touchModel.setEndX(10);
		touchModel.setStartX(111);
		touchModel.setEndY(2);
		touchModel.setStartY(15);
		when(windowDelegate.getClientWidth()).thenReturn(400);

		assertTrue(testObj.canSwitchPage());
		verify(windowDelegate).getClientWidth();
	}

	@Test
	public void isReadyToStartAnimation_isHorizontalSwipe_isTrue_and_isVerticalSwipeDetected_isFalse() {
		touchModel.setEndX(2);
		touchModel.setStartX(0);
		touchModel.setEndY(1);
		touchModel.setStartY(0);
		touchModel.setVerticalSwipeDetected(false);

		assertTrue(testObj.isReadyToStartAnimation());
	}

	@Test
	public void isReadyToStartAnimation_isHorizontalSwipe_isTrue_and_isVerticalSwipeDetected_isTrue() {
		touchModel.setEndX(2);
		touchModel.setStartX(0);
		touchModel.setEndY(1);
		touchModel.setStartY(0);
		touchModel.setVerticalSwipeDetected(true);

		assertFalse(testObj.isReadyToStartAnimation());
	}

	@Test
	public void isReadyToStartAnnimation_isHorizontalSwipe_isFalse() {
		touchModel.setEndX(1);
		touchModel.setStartX(0);
		touchModel.setEndY(2);
		touchModel.setStartY(0);

		assertFalse(testObj.isReadyToStartAnimation());
	}

	@Test
	public void isTouchReservationTest() {
		touchModel.setTouchReservation(true);
		assertTrue(testObj.isTouchReservation());
	}

	@Test
	public void isSecondFingerAddTest_isFalse() {
		assertFalse(testObj.isSwypeStarted());
	}

	@Test
	public void isSecondFingerAddTest_isTrue() {
		touchModel.setStartX(100);
		assertTrue(testObj.isSwypeStarted());
	}

	@Test
	public void updateEndPointTest() {
		NativeEvent onTouchMoveEvent = mock(NativeEvent.class);
		int y = 100;
		when(touchEventReader.getScreenY(onTouchMoveEvent)).thenReturn(y);
		int x = 200;
		when(touchEventReader.getX(onTouchMoveEvent)).thenReturn(x);
		testObj.updateEndPoint(onTouchMoveEvent);

		assertEquals(touchModel.getEndX(), x);
		assertEquals(touchModel.getEndY(), y);

		InOrder inOrder = inOrder(windowDelegate, touchEventReader, eventsBus);
		inOrder.verify(touchEventReader)
			   .getScreenY(onTouchMoveEvent);
		inOrder.verify(touchEventReader)
			   .getX(onTouchMoveEvent);
	}

	@Test
	public void updateAfterSwypeDetectedTest_isNotSwipeStarted() {
		int endX = 15;
		touchModel.setEndX(endX);
		touchModel.setSwipeStarted(true);

		testObj.updateAfterSwypeDetected();

		assertEquals(touchModel.getLastEndX(), endX);
		assertEquals(touchModel.isSwipeStarted(), true);

	}

	@Test
	public void updateAfterSwypeDetectedTest_isSwipeStarted() {
		int endX = 15;
		touchModel.setEndX(endX);
		touchModel.setSwipeStarted(false);

		testObj.updateAfterSwypeDetected();

		assertEquals(touchModel.getLastEndX(), endX);
		assertEquals(touchModel.isSwipeStarted(), true);

		ArgumentCaptor<PlayerEvent> argumentCaptor = ArgumentCaptor.forClass(PlayerEvent.class);

		verify(eventsBus).fireEvent(argumentCaptor.capture());
		assertEquals(argumentCaptor.getValue()
								   .getType(), PlayerEventTypes.PAGE_SWIPE_STARTED);

	}

	@Test
	public void isSwipeRight_right() {
		touchModel.setLastEndX(22);
		assertTrue(testObj.isSwipeRight());
	}

	@Test
	public void isSwipeRight_left() {
		touchModel.setLastEndX(-22);
		assertFalse(testObj.isSwipeRight());
	}

	@Test
	public void resetTouchModelTest() {
		int endX = 100;
		touchModel.setEndX(endX);
		touchModel.setSwipeStarted(true);
		testObj.resetTouchModel();
		assertEquals(touchModel.getStartX(), endX);
		assertEquals(touchModel.getLastEndX(), endX);
		assertTrue(touchModel.isSwipeStarted());
		assertFalse(touchModel.isTouchReservation());
	}

	@Test
	public void updateOnTouchEndTest() {
		NativeEvent event = mock(NativeEvent.class);
		int y = 100;
		when(touchEventReader.getFromChangedTouchesScreenY(event)).thenReturn(y);
		int x = 200;
		when(touchEventReader.getFromChangedTouchesX(event)).thenReturn(x);
		testObj.updateOnTouchEnd(event);

		assertEquals(touchModel.getEndX(), x);
		assertEquals(touchModel.getEndY(), y);

		InOrder inOrder = inOrder(windowDelegate, touchEventReader, eventsBus);
		inOrder.verify(touchEventReader)
			   .getFromChangedTouchesScreenY(event);
		inOrder.verify(touchEventReader)
			   .getFromChangedTouchesX(event);
	}

	@Test
	public void setTouchReservationTest() {
		testObj.setTouchReservation(true);
		assertTrue(touchModel.isTouchReservation());
	}

	@Test
	public void getDirectionTest_left() {
		touchModel.setEndX(22);
		assertEquals(NavigationButtonDirection.PREVIOUS, testObj.getDirection());
	}

	@Test
	public void getDirectionTest_right() {
		touchModel.setEndX(-22);
		assertEquals(NavigationButtonDirection.NEXT, testObj.getDirection());
	}

	@Test
	public void getDirectionTest_null() {
		assertNull(testObj.getDirection());
	}

	@Test
	public void canSwype_isZoomedTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);

		when(multiPageController.isZoomed()).thenReturn(true);

		assertFalse(testObj.canSwype(multiPageController));
	}

	@Test
	public void canSwype_isAnimationRunningTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);

		when(multiPageController.isZoomed()).thenReturn(false);
		when(multiPageController.isAnimationRunning()).thenReturn(true);

		assertFalse(testObj.canSwype(multiPageController));
	}

	@Test
	public void canSwype_isTouchReservationTest() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);

		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchReservation(true);

		assertFalse(testObj.canSwype(multiPageController));
	}

	@Test
	public void canSwype_isSwypeLockTest() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);

		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setSwypeLock(true);

		assertFalse(testObj.canSwype(multiPageController));
	}

	@Test
	public void canSwype_isTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchReservation(false);

		assertTrue(testObj.canSwype(multiPageController));
	}

	@Test
	public void canMove_isZoomedTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(100);
		when(multiPageController.isAnimationRunning()).thenReturn(true);
		when(multiPageController.isZoomed()).thenReturn(true);
		touchModel.setMultiTouch(false);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_canIsAnimationRunningTrueTest() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(true);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setMultiTouch(false);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_canIsTouchReservationTrueTest() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setMultiTouch(false);
		touchModel.setTouchReservation(true);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_isSwypeLockTrueTest() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(100);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchReservation(false);

		touchModel.setSwypeLock(true);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_isVerticalSwipeIsTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(100);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setMultiTouch(false);

		assertFalse(testObj.canMove(multiPageController));

		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void setSwypeStartedTest() {
		testObj.setSwypeStarted(true);

		assertTrue(touchModel.isSwipeStarted());
	}

	@Test
	public void canMove_isMultiTouchTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setMultiTouch(true);

		assertFalse(testObj.canMove(multiPageController));
		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void canMove_true() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setMultiTouch(false);

		assertTrue(testObj.canMove(multiPageController));
		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void isSwipeStartedTest() {
		touchModel.setSwipeStarted(true);

		assertTrue(testObj.isSwipeStarted());
	}

	@Test
	public void setSwypeLockTest() {
		testObj.setSwypeLock(true);

		assertTrue(touchModel.isSwypeLock());
	}

}
