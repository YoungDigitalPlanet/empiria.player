package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(JUnitParamsRunner.class)
public class SurfacePointTranslatorTest extends AbstractTestWithMocksBase {

	private SurfacePointTranslator translator;
	
	@Override
	public void setUp() {
		super.setUp(SurfacePointTranslator.class);
		translator = injector.getInstance(SurfacePointTranslator.class);
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersForTranslatePoint(){
		return $(
				$(50, 30, 20),
				$(50, 50, 0),
				$(30, 50, -20),
				$(30, 0, 30),
				$(0, 30, -30)
				);
	}

	@Test
	@Parameters
	public void translatePoint(int left, int surfaceLeft, int expectedLeft){
		// given
		int TOP = 100;
		Point point = new Point(left, TOP);
		ConnectionSurface surface = mock(ConnectionSurface.class);
		stub(surface.getOffsetLeft()).toReturn(surfaceLeft);
		
		// when
		Point result = translator.translatePoint(point, surface);
		
		// then
		assertThat(result.getX(), equalTo(expectedLeft));
		assertThat(result.getY(), equalTo(TOP));
	}

	@Test
	@Parameters(method = "parametersForTranslatePoint")
	public void translatePoint_xy(int left, int surfaceLeft, int expectedLeft){
		// given
		int TOP = 100;
		ConnectionSurface surface = mock(ConnectionSurface.class);
		stub(surface.getOffsetLeft()).toReturn(surfaceLeft);
		
		// when
		Point result = translator.translatePoint(left, TOP, surface);
		
		// then
		assertThat(result.getX(), equalTo(expectedLeft));
		assertThat(result.getY(), equalTo(TOP));
	}
	
}
