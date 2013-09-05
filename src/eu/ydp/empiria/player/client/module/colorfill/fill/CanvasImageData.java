package eu.ydp.empiria.player.client.module.colorfill.fill;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;

import eu.ydp.empiria.player.client.color.ColorModel;

@SuppressWarnings("PMD")
public class CanvasImageData implements ICanvasImageData{

	private final Context2d context;
	private final int height;
	private final int width;
	private final ImageData imageData;
	//private static final int OFFSET_RED = 0;
	private static final int OFFSET_GREEN = 1;
	private static final int OFFSET_BLUE = 2;
	private static final int OFFSET_ALPHA = 3;
	private final CanvasPixelArray pixelArray;

	public CanvasImageData(final Context2d context, final int width, final int height) {
		this.context = context;
		this.width = width;
		this.height = height;
		imageData = context.getImageData(0, 0, width, height);
		pixelArray = imageData.getData();
	}

	@Override
	public int getImageHeight() {
		return height;
	}

	@Override
	public int getImageWidth() {
		return width;
	}

	@Override
	public ColorModel getRgbColor(final int x, final int y) {
		int position = 4 * (x + y * width);
		return ColorModel.createFromRgba(pixelArray.get(position),
							pixelArray.get(position + OFFSET_GREEN),
							pixelArray.get(position + OFFSET_BLUE),
							pixelArray.get(position	+ OFFSET_ALPHA));

	}

	@Override
	public void setColor(final ColorModel color, final int x, final int y) {
		int position = 4 * (x + y * width);
		pixelArray.set(position, color.getRed());
		pixelArray.set(position + OFFSET_GREEN, color.getGreen());
		pixelArray.set(position + OFFSET_BLUE, color.getBlue());
		pixelArray.set(position + OFFSET_ALPHA, color.getAlpha());
	}

	@Override
	public void flush() {
		context.putImageData(imageData, 0, 0);
	}
}
