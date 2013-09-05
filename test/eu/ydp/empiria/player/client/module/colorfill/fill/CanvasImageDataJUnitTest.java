package eu.ydp.empiria.player.client.module.colorfill.fill;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({ Context2d.class, ImageData.class, CanvasPixelArray.class })
@SuppressWarnings("PMD")
public class CanvasImageDataJUnitTest {

	private static final int PIXEL_VALUE = 127;
	private static final int HEIGHT = 41;
	private static final int WIDTH = 20;
	private CanvasImageData instance;
	private ImageData imageData;
	private CanvasPixelArray pixelArray;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		Context2d context2d = mock(Context2d.class);
		imageData = mock(ImageData.class);
		pixelArray = mock(CanvasPixelArray.class);
		doReturn(PIXEL_VALUE).when(pixelArray).get(anyInt());
		doReturn(pixelArray).when(imageData).getData();
		doReturn(imageData).when(context2d).getImageData(anyInt(), anyInt(), anyInt(), anyInt());
		instance = new CanvasImageData(context2d, WIDTH, HEIGHT);
	}

	@Test
	public void getImageHeight() throws Exception {
		assertThat(instance.getImageHeight()).isEqualTo(HEIGHT);
	}

	@Test
	public void getImageWidth() throws Exception {
		assertThat(instance.getImageWidth()).isEqualTo(WIDTH);
	}

	@Test
	public void getRgbColor() throws Exception {
		int x = 10;
		int y = 12;
		int position = 4 * (x + y * WIDTH);
		ColorModel rgbColor = instance.getRgbColor(x, y);
		verify(pixelArray).get(eq(position));
		verify(pixelArray).get(eq(position + 1));
		verify(pixelArray).get(eq(position + 2));
		verify(pixelArray).get(eq(position + 3));
		assertThat(rgbColor).isNotNull();
		assertThat(rgbColor.getAlpha()).isEqualTo(PIXEL_VALUE);
		assertThat(rgbColor.getRed()).isEqualTo(PIXEL_VALUE);
		assertThat(rgbColor.getBlue()).isEqualTo(PIXEL_VALUE);
		assertThat(rgbColor.getGreen()).isEqualTo(PIXEL_VALUE);

	}

	@Test
	public void setColor() throws Exception {
		int x = 10;
		int y = 12;
		int position = 4 * (x + y * WIDTH);
		ColorModel color = ColorModel.createFromRgba(127, 128, 129, 130);
		instance.setColor(color, x, y);
		verify(pixelArray).set(eq(position), eq(127));
		verify(pixelArray).set(eq(position + 1), eq(128));
		verify(pixelArray).set(eq(position + 2), eq(129));
		verify(pixelArray).set(eq(position + 3), eq(130));
	}
}
