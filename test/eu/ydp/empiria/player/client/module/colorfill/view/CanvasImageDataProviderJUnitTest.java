package eu.ydp.empiria.player.client.module.colorfill.view;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageDataSlower;
import eu.ydp.empiria.player.client.module.colorfill.fill.ICanvasImageData;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({ Context2d.class, ImageData.class, Canvas.class })
public class CanvasImageDataProviderJUnitTest {

	private CanvasImageDataProvider canvasImageDataProvider;
	private UserAgentUtil userAgentUtil;
	private final CanvasImageView canvasStubView = Mockito.mock(CanvasImageView.class);

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void setUp() throws Exception {
		userAgentUtil = Mockito.mock(UserAgentUtil.class);
		canvasImageDataProvider = new CanvasImageDataProvider(userAgentUtil);

		Canvas canvas = Mockito.mock(Canvas.class);
		when(canvasStubView.getCanvas()).thenReturn(canvas);

		Context2d context = Mockito.mock(Context2d.class);
		when(canvas.getContext2d()).thenReturn(context);

		ImageData imageData = Mockito.mock(ImageData.class);
		when(context.getImageData(0, 0, 0, 0)).thenReturn(imageData);
	}

	@Test
	public void shouldReturnSlowerImplemntationOfCanvasImageDataWhenOnInternetExplorer() throws Exception {
		when(userAgentUtil.isIE()).thenReturn(true);

		ICanvasImageData imageData = canvasImageDataProvider.getCanvasImageData(canvasStubView);

		assertTrue(imageData instanceof CanvasImageDataSlower);
	}

	@Test
	public void shouldReturnFasterImplemntationOfCanvasImageDataWhenSomethingElseThanInternetExplorer() throws Exception {
		when(userAgentUtil.isIE()).thenReturn(false);

		ICanvasImageData imageData = canvasImageDataProvider.getCanvasImageData(canvasStubView);

		assertTrue(imageData instanceof CanvasImageData);
	}
}
