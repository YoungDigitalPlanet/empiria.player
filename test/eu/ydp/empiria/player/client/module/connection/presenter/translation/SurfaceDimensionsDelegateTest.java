package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(JUnitParamsRunner.class)
public class SurfaceDimensionsDelegateTest extends AbstractTestWithMocksBase {

	private static final int ITEM_LEFT_OFFSET_LEFT = 20;
	private static final int ITEM_WIDTH = 10;
	private static final int ITEM_RIGHT_OFFSET_LEFT = 50;
	private static final int SMALL_CANVAS_WIDTH = ITEM_RIGHT_OFFSET_LEFT - ITEM_LEFT_OFFSET_LEFT + ITEM_WIDTH;
	private final int VIEW_WIDTH = 80;
	private final int VIEW_HEIGHT = 100;
	private SurfaceDimensionsDelegate delegate;

	private final ConnectionItemsMockCreator creator = new ConnectionItemsMockCreator();

	@Override
	public void setUp() {
		super.setUp(SurfaceDimensionsDelegate.class, SurfaceDimensionsFinder.class);
		delegate = injector.getInstance(SurfaceDimensionsDelegate.class);
	}

	@Test
	public void findHeight() {
		// given
		HasDimensions view = new Size(VIEW_WIDTH, VIEW_HEIGHT);
		ConnectionItems items = mock(ConnectionItems.class);
		delegate.init(view, items);

		// when
		int height = delegate.getHeight();

		// then
		assertThat(height, equalTo(VIEW_HEIGHT));
	}

	@SuppressWarnings("unused")
	private Object[] parametersForFindWidth(){
		return $(
				$(2, 3, SMALL_CANVAS_WIDTH),
				$(0, 0, VIEW_WIDTH),
				$(2, 0, VIEW_WIDTH),
				$(0, 2, VIEW_WIDTH)
				);
	}

	@Test
	@Parameters
	public void findWidth(int leftCount, int rightCount, int expectedWidth) {
		// given
		HasDimensions view = new Size(VIEW_WIDTH, VIEW_HEIGHT);
		ConnectionItems items = creator.createConnectionItems(leftCount, ITEM_LEFT_OFFSET_LEFT, rightCount, ITEM_RIGHT_OFFSET_LEFT, ITEM_WIDTH);
		delegate.init(view, items);

		// when
		int width = delegate.getWidth();

		// then
		assertThat(width, equalTo(expectedWidth));
	}

}
