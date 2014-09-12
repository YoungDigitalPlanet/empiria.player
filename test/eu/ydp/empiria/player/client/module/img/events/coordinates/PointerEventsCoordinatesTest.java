package eu.ydp.empiria.player.client.module.img.events.coordinates;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(MockitoJUnitRunner.class)
public class PointerEventsCoordinatesTest {

	@InjectMocks
	private PointerEventsCoordinates testObj;
	@Mock
	private PointerEvent<? extends EventHandler> pointerEvent;

	private final int id = 1;

	@Before
	public void setup() {
		when(pointerEvent.getPointerId()).thenReturn(id);
	}

	@Test
	public void shouldAddEvent_ifIsNotFirstTouch() {
		// given
		int x = 1;
		int y = 1;
		when(pointerEvent.getClientX()).thenReturn(x);
		when(pointerEvent.getClientY()).thenReturn(y);

		Point assumedPoint = new Point(x, y);

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(assumedPoint, testObj.getPoint(0));
	}

	@Test
	public void shouldAddEvent_ifIsNotFirstTouchAnd_ifAlreadyContainsEvent() {
		// given
		int x = 1;
		int y = 1;
		when(pointerEvent.getClientX()).thenReturn(x);
		when(pointerEvent.getClientY()).thenReturn(y);
		testObj.addEvent(pointerEvent);

		Point assumedPoint = new Point(x, y);

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(assumedPoint, testObj.getPoint(0));
	}

	@Test
	public void shouldAddEvent_ifIsNotFirstTouchAnd_ifAlreadyContainsEvent_ifIsPrimary() {
		// given
		int x = 1;
		int y = 1;
		when(pointerEvent.getClientX()).thenReturn(x);
		when(pointerEvent.getClientY()).thenReturn(y);
		when(pointerEvent.isPrimary()).thenReturn(true);
		testObj.addEvent(pointerEvent);

		Point assumedPoint = new Point(x, y);

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(assumedPoint, testObj.getPoint(0));
	}

	@Test
	public void shouldAddEvent_ifIsFirstTouch() {
		// given
		int x = 1;
		int y = 1;
		when(pointerEvent.getClientX()).thenReturn(x);
		when(pointerEvent.getClientY()).thenReturn(y);
		when(pointerEvent.isPrimary()).thenReturn(true);

		Point assumedPoint = new Point(x, y);

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(assumedPoint, testObj.getPoint(0));
	}

	@Test
	public void shouldReturnLength() {
		// given

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(1, testObj.getLength());
	}

	@Test
	public void shouldRemoveEvent() {
		// given

		// when
		testObj.addEvent(pointerEvent);
		testObj.removeEvent(pointerEvent);

		// then
		assertEquals(0, testObj.getLength());
	}

	@Test
	public void shouldReturnTouchOnImageEvent() {
		// given

		// when
		TouchOnImageEvent result = testObj.getTouchOnImageEvent();

		// then
		assertNotNull(result);
	}
}
