package eu.ydp.empiria.player.client.module.colorfill.fill;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class CanvasImageDataSlowerJUnitTest {

    private CanvasImageDataSlower imageDataSlower;
    private Context2d context;
    private final int width = 123;
    private final int height = 456;
    private ImageData imageData;

    @Before
    public void setUp() throws Exception {
        context = mock(Context2d.class);
        imageData = mock(ImageData.class);

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
