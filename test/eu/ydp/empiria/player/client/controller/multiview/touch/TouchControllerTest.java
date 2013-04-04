package eu.ydp.empiria.player.client.controller.multiview.touch;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.empiria.player.client.controller.multiview.IMultiPageController;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;

public class TouchControllerTest {
	@Mock
	private WindowDelegate windowDelegate;
	@Mock
	private TouchEventReader touchEventReader;
	@Mock
	private EventsBus eventsBus;

	private TouchModel touchModel;

	private TouchController testObj;

	@Before
	public void before() {
		touchModel = new TouchModel();
		MockitoAnnotations.initMocks(this);
		testObj = new TouchController(windowDelegate, touchEventReader, eventsBus, touchModel);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(windowDelegate, touchEventReader, eventsBus);
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
		assertEquals(touchModel.isTouchLock(), true);
		assertEquals(touchModel.isSwipeStarted(), false);
		assertEquals(touchModel.isTouchReservation(), false);

		InOrder inOrder = inOrder(windowDelegate, touchEventReader, eventsBus);
		inOrder.verify(touchEventReader).getScreenY(onTouchStartEvent);
		inOrder.verify(touchEventReader).getX(onTouchStartEvent);
		inOrder.verify(touchEventReader).isMoreThenOneFingerTouch(onTouchStartEvent);
		inOrder.verify(windowDelegate).getScrollTop();

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
	public void isHorizontalSwipe_isTrue() {
		touchModel.setEndX(2);
		touchModel.setStartX(0);
		touchModel.setEndY(1);
		touchModel.setStartY(0);

		assertTrue(testObj.isHorizontalSwipe());
	}

	@Test
	public void isHorizontalSwipe_isFalse() {
		touchModel.setEndX(1);
		touchModel.setStartX(0);
		touchModel.setEndY(2);
		touchModel.setStartY(0);

		assertFalse(testObj.isHorizontalSwipe());
	}

	@Test
	public void isTouchReservationTest() {
		touchModel.setTouchReservation(true);
		assertTrue(testObj.isTouchReservation());
	}

	@Test
	public void isSecondFingerAddTest_isFalse() {
		assertFalse(testObj.isSecondFingerAdd());
	}

	@Test
	public void isSecondFingerAddTest_isTrue() {
		touchModel.setStartX(100);
		assertTrue(testObj.isSecondFingerAdd());
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
		inOrder.verify(touchEventReader).getScreenY(onTouchMoveEvent);
		inOrder.verify(touchEventReader).getX(onTouchMoveEvent);
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
		assertEquals(argumentCaptor.getValue().getType(), PlayerEventTypes.PAGE_SWIPE_STARTED);

	}

	@Test
	public void isSwypeDetectedTest_isFalse() {
		assertFalse(testObj.isSwypeDetected());
	}

	@Test
	public void isSwypeDetectedTest_isFalse_EndXEqual0() {
		touchModel.setEndX(10);
		assertFalse(testObj.isSwypeDetected());
	}

	@Test
	public void isSwypeDetectedTest_isTrue() {
		touchModel.setEndX(10);
		touchModel.setLastEndX(22);
		assertTrue(testObj.isSwypeDetected());
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
		testObj.resetTouchModel();
		assertEquals(touchModel.getStartX(), endX);
		assertEquals(touchModel.getLastEndX(), endX);
		assertFalse(touchModel.isSwipeStarted());
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
		inOrder.verify(touchEventReader).getFromChangedTouchesScreenY(event);
		inOrder.verify(touchEventReader).getFromChangedTouchesX(event);
	}

	@Test
	public void setTouchReservationTest() {
		testObj.setTouchReservation(true);
		assertTrue(touchModel.isTouchReservation());
	}

	@Test
	public void isTouchLockTest() {
		touchModel.setTouchLock(true);

		assertTrue(testObj.isTouchLock());
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
	public void canSwype_isTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchReservation(false);

		assertTrue(testObj.canSwype(multiPageController));
	}

	@Test
	public void canMove_canSwypeIsFalse() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(true);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchLock(false);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_isVerticalSwipeIsTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(100);
		when(multiPageController.isAnimationRunning()).thenReturn(true);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchLock(false);

		assertFalse(testObj.canMove(multiPageController));
	}

	@Test
	public void canMove_isTouchLockTrue() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchLock(true);

		assertFalse(testObj.canMove(multiPageController));
		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void canMove_isTouchLockFalse() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(100);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchLock(true);

		assertFalse(testObj.canMove(multiPageController));
		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void canMove_true() {
		IMultiPageController multiPageController = mock(IMultiPageController.class);
		when(windowDelegate.getScrollTop()).thenReturn(0);
		when(multiPageController.isAnimationRunning()).thenReturn(false);
		when(multiPageController.isZoomed()).thenReturn(false);
		touchModel.setTouchLock(false);

		assertTrue(testObj.canMove(multiPageController));
		verify(windowDelegate).getScrollTop();
	}

	@Test
	public void isSwipeStartedTest() {
		touchModel.setSwipeStarted(true);

		assertTrue(testObj.isSwipeStarted());
	}

}
