package eu.ydp.empiria.player.client.module.colorfill.fill;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;

public class CanvasImageDataSlower implements ICanvasImageData{

	private final Context2d context;
	private final int height;
	private final int width;
	private final ImageData imageData;

	public CanvasImageDataSlower(final Context2d context, final int width, final int height) {
		this.context = context;
		this.width = width;
		this.height = height;
		imageData = context.getImageData(0, 0, width, height);
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
		ColorModel rgba = ColorModel.createFromRgba(imageData.getRedAt(x, y),
							imageData.getGreenAt(x, y),
							imageData.getBlueAt(x, y),
							imageData.getAlphaAt(x, y));

		return rgba;
	}

	@Override
	public void setColor(final ColorModel color, final int x, final int y) {
		imageData.setRedAt(color.getRed(), x, y);
		imageData.setGreenAt(color.getGreen(), x, y);
		imageData.setBlueAt(color.getBlue(), x, y);
		imageData.setAlphaAt(color.getAlpha(), x, y);
	}

	@Override
	public void flush() {
		context.putImageData(imageData, 0, 0);
	}
}
