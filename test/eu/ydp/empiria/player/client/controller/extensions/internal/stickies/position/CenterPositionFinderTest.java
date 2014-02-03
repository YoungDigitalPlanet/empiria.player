package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder.Axis;
import eu.ydp.gwtutil.client.geom.Rectangle;

@RunWith(JUnitParamsRunner.class)
public class CenterPositionFinderTest {

	private CenterPositionFinder finder;
	private RangeCreator rangeCreator;
	private ViewportHelper viewportHelper;
	private WidgetSizeHelper sizeHelper;

	@Before
	public void setUp() {
		rangeCreator = Mockito.mock(RangeCreator.class);
		viewportHelper = Mockito.mock(ViewportHelper.class);
		sizeHelper = Mockito.mock(WidgetSizeHelper.class);
		finder = new CenterPositionFinder(rangeCreator, viewportHelper, sizeHelper);
	}

	@Test
	@Parameters(method = "findCenterPosition_params")
	public void findCenterPosition(Integer itemSize, Integer containerFrom, Integer containerTo, Integer viewportFrom, Integer viewportTo, int expectedResult) {
		// given
		Rectangle viewport = new Rectangle();
		when(viewportHelper.getViewport()).thenReturn(viewport);

		Range<Integer> viewportRange = Ranges.closed(viewportFrom, viewportTo);
		when(rangeCreator.getRangeForAxis(viewport, Axis.HORIZONTAL)).thenReturn(viewportRange);

		Rectangle playerContainerRectangle = new Rectangle();
		when(sizeHelper.getPlayerContainerRectangle()).thenReturn(playerContainerRectangle);

		Range<Integer> container = Ranges.closed(containerFrom, containerTo);
		when(rangeCreator.getRangeForAxis(playerContainerRectangle, Axis.HORIZONTAL)).thenReturn(container);

		int parentAbsoluteCoord = 10;

		// when
		int result = finder.getCenterPosition(itemSize, parentAbsoluteCoord, Axis.HORIZONTAL);

		// then
		assertThat(result, equalTo(expectedResult - parentAbsoluteCoord));
	}

	@SuppressWarnings("unused")
	private Object[] findCenterPosition_params() {
		return $($(0, 0, 0, 0, 0, 0), $(10, 0, 100, 0, 20, 5), $(10, 0, 100, 40, 60, 45), $(10, 0, 100, 80, 100, 85), $(10, 50, 150, 0, 20, 50), // viewport
																																					// above
																																					// container
				$(10, 50, 150, 300, 320, 140), // viewport below container
				$(120, 50, 150, 0, 200, 50), // item larger then container
				$(120, 50, 150, 0, 20, 50), // item larger then container and viewport above
				$(120, 50, 150, 300, 320, 50) // item larger then container and viewport below
		);
	}

}
