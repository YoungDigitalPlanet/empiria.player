package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.stub;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Range;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder.Axis;

@RunWith(JUnitParamsRunner.class)
public class StickieViewPositionFinderTest extends AbstractTestWithMocksBase {

	StickieViewPositionFinder finder;
	
	@Override
	public void setUp() {
		super.setUp(StickieViewPositionFinder.class);
		finder = injector.getInstance(StickieViewPositionFinder.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getCenterPosition() {
		// given
		final Integer VALUE = 140;
		final int SIZE = 10;
		final int ABSOLUTE = 20;
		stub(finder.positionFinder.findCenterPosition(anyInt(), any(Range.class), any(Range.class))).toReturn(VALUE);
		
		// when
		int result = finder.getCenterPosition(SIZE, ABSOLUTE, Axis.HORIZONTAL);
		
		// then
		assertThat(result, equalTo(VALUE - ABSOLUTE));
	}
	
	@Test
	@Parameters(method = "refinePosition_params")
	public void refinePosition(int coord, int size, int min, int max, int expectedResult){
		// when
		int result = finder.refinePosition(coord, size, min, max);
		
		// then
		assertThat(result, equalTo(expectedResult));
	}
	
	@SuppressWarnings("unused")
	private Object[] refinePosition_params(){
		return $(
				$(0, 0, 0, 0, 0),
				$(0, 10, 0, 20, 0), // item fits
				$(5, 10, 0, 20, 5),
				$(10, 10, 0, 20, 10),
				$(0, 10, 5, 20, 5), // item sticks out of min
				$(0, 10, 50, 100, 50), // item below min
				$(50, 10, 0, 40, 30), // item sticks out of max
				$(50, 10, 0, 20, 10) // item above max
				);
	}
}
