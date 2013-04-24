package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

@RunWith(JUnitParamsRunner.class)
public class SurfacePositionFinderTest extends AbstractTestWithMocksBase {

	private static final int ITEM_WIDTH = 10;

	private static final int RIGHT_OFFSET = 50;

	private static final int LEFT_OFFSET = 20;

	private SurfacePositionFinder finder;
	
	private ConnectionItemsMockCreator creator = new ConnectionItemsMockCreator();
	
	@Override
	public void setUp() {
		super.setUp(SurfacePositionFinder.class);
		finder = injector.getInstance(SurfacePositionFinder.class);
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersForAlignSurfaceToItem(){
		return $(
				$(2, 3, LEFT_OFFSET),
				$(0, 0, 0),
				$(2, 0, LEFT_OFFSET),
				$(0, 2, 0)
				);
	}
	
	@Test
	@Parameters
	public void alignSurfaceToItem(int countLeft, int countRight, int expectedLeft) {
		// given
		ConnectionItems items = creator.createConnectionItems(countLeft, LEFT_OFFSET, countRight, RIGHT_OFFSET, ITEM_WIDTH);
		
		// when
		int left = finder.findOffsetLeft(items);
		
		// then
		assertThat(left, equalTo(expectedLeft));
	}

}
