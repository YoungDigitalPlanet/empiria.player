package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageEventTest {

	private TouchOnImageEvent testObj;

	@Test
	public void should_return_Point() {
		// given
		List<Point> coordinatesList = mock(List.class);
		int sampleValue = 0;
		Point point = new Point(sampleValue, sampleValue);

		when(coordinatesList.get(anyInt())).thenReturn(point);

		testObj = new TouchOnImageEvent(coordinatesList);

		// when
		Point result = testObj.getPoint(anyInt());

		// then
		assertThat(result, is(point));
	}

	@Test
	public void should_return_Length() {
		// given
		List<Point> coordinatesList = mock(List.class);
		int length = 2;

		when(coordinatesList.size()).thenReturn(length);

		testObj = new TouchOnImageEvent(coordinatesList);

		// when
		int result = testObj.getLength();

		// then
		assertThat(result, is(length));
	}
}
