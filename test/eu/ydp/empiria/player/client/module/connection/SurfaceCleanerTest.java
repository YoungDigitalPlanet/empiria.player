package eu.ydp.empiria.player.client.module.connection;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.util.position.Point;
import gwt.g2d.client.graphics.canvas.Context;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceCleanerTest {

	private final MocksCollector mocksCollector = new MocksCollector();
	@InjectMocks
	private SurfaceCleaner testObj;
	@Mock
	private Context context;

	@After
	public void tearDown() {
		verifyNoMoreInteractions(mocksCollector.getMocks());
	}

	@Test
	public void testReateRectangleForClean() {
		// given
		LineSegment lineSegment = new LineSegment(new Point(10, 20), new Point(0, 5));

		// when
		testObj.clean(lineSegment, context);

		// then

		verify(context).clearRect(-25, -20, 60, 65);
	}

	@Test
	public void testReateRectangleForClean_lineSegmentIsNull() {
		// when
		testObj.clean(null, context);

	}
}
