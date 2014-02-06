package eu.ydp.empiria.player.client.module.colorfill.fill;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({ Context2d.class, ImageData.class, CanvasPixelArray.class })
public class CanvasImageDataSlowerJUnitTest {

	private CanvasImageDataSlower imageDataSlower;
	private Context2d context;
	private final int width = 123;
	private final int height = 456;
	private ImageData imageData;

	@Before
	public void setUp() throws Exception {
		context = Mockito.mock(Context2d.class);
		imageData = Mockito.mock(ImageData.class);

		when(context.getImageData(0, 0, width, height)).thenReturn(imageData);

		imageDataSlower = new CanvasImageDataSlower(context, width, height);
	}

	@Test
	public void shouldReturnColorFromImageDataOnPosition() throws Exception {
		int x = 10;
		int y = 20;
		int red = 123;
		int blue = 456;
		int green = 83;
		int alpha = 99;

		when(imageData.getRedAt(x, y)).thenReturn(red);

		when(imageData.getGreenAt(x, y)).thenReturn(green);

		when(imageData.getBlueAt(x, y)).thenReturn(blue);

		when(imageData.getAlphaAt(x, y)).thenReturn(alpha);

		ColorModel color = imageDataSlower.getRgbColor(x, y);

		assertThat(color.getRed()).isEqualTo(red);
		assertThat(color.getBlue()).isEqualTo(blue);
		assertThat(color.getGreen()).isEqualTo(green);
		assertThat(color.getAlpha()).isEqualTo(alpha);
	}

	@Test
	public void shouldSetColorOnImageDataOnPosition() throws Exception {
		int x = 10;
		int y = 20;
		int red = 123;
		int blue = 456;
		int green = 83;
		int alpha = 99;

		ColorModel color = ColorModel.createFromRgba(red, green, blue, alpha);
		imageDataSlower.setColor(color, x, y);

		verify(imageData).setRedAt(red, x, y);
		verify(imageData).setGreenAt(green, x, y);
		verify(imageData).setBlueAt(blue, x, y);
		verify(imageData).setAlphaAt(alpha, x, y);
	}

	@Test
	public void shouldPutImageDataOnFlush() throws Exception {
		imageDataSlower.flush();

		verify(context).putImageData(imageData, 0, 0);
	}

	@Test
	public void shouldKeepCorrectDimensions() throws Exception {
		assertThat(imageDataSlower.getImageWidth()).isEqualTo(width);
		assertThat(imageDataSlower.getImageHeight()).isEqualTo(height);
	}
}
