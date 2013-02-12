package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;

@RunWith(JUnitParamsRunner.class)
public class CenterPositionFinderTest extends AbstractTestWithMocksBase {

	CenterPositionFinder finder;
	
	@Override
	public void setUp() {
		super.setUp(CenterPositionFinder.class);
		finder = injector.getInstance(CenterPositionFinder.class);
	}
	
	@Test
	@Parameters(method = "findCenterPosition_params")
	public void findCenterPosition(Integer itemSize, Integer containerFrom, Integer containerTo, Integer viewportFrom, Integer viewportTo, int expectedResult) {
		// given
		Range<Integer> container = Ranges.closed(containerFrom, containerTo);
		Range<Integer> viewport = Ranges.closed(viewportFrom, viewportTo);
		
		// when
		int result = finder.findCenterPosition(itemSize, container, viewport);
		
		// then
		assertThat(result, equalTo(expectedResult));
	}
	
	@SuppressWarnings("unused")
	private Object[] findCenterPosition_params(){
		return $(
				$(0, 0, 0, 0, 0, 0),
				$(10, 0, 100, 0, 20, 5),
				$(10, 0, 100, 40, 60, 45),
				$(10, 0, 100, 80, 100, 85),
				$(10, 50, 150, 0, 20, 50), // viewport above container
				$(10, 50, 150, 300, 320, 140), // viewport below container
				$(120, 50, 150, 0, 200, 50), // item larger then container
				$(120, 50, 150, 0, 20, 50), // item larger then container and viewport above
				$(120, 50, 150, 300, 320, 50) // item larger then container and viewport below
				);
	}

}
