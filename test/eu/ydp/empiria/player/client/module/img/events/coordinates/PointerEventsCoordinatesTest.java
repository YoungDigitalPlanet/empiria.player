package eu.ydp.empiria.player.client.module.img.events.coordinates;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerEvent;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(GwtMockitoTestRunner.class)
public class PointerEventsCoordinatesTest {

	@InjectMocks
	private PointerEventsCoordinates testObj;

	private PointerEvent<?> pointerEvent;

	private final long id = 1;

	@Before
	public void setup() {
		pointerEvent = mock(PointerEvent.class);
		when(pointerEvent.getPointerId()).thenReturn(id);
	}

	@Test
	public void should_addEvent() {
		// given
		int x = 1;
		int y = 1;
		when(pointerEvent.getClientX()).thenReturn(x);
		when(pointerEvent.getClientY()).thenReturn(y);

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(new Point(x, y), testObj.getPoint(0));
	}

	@Test
	public void should_return_length() {
		// given

		// when
		testObj.addEvent(pointerEvent);

		// then
		assertEquals(1, testObj.getLength());
	}

	@Test
	public void should_removeEvent() {
		// given

		// when
		testObj.addEvent(pointerEvent);
		testObj.removeEvent(pointerEvent);

		// then
		assertEquals(0, testObj.getLength());
	}

	@Test
	public void should_return_TouchOnImageEvent() {
		// given

		// when
		TouchOnImageEvent result = testObj.getTouchOnImageEvent();

		// then
		assertNotNull(result);
	}
}
