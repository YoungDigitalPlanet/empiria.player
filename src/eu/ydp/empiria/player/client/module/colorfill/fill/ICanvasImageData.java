package eu.ydp.empiria.player.client.module.colorfill.fill;

import eu.ydp.empiria.player.client.color.ColorModel;

public interface ICanvasImageData {

	int getImageHeight();

	int getImageWidth();

	ColorModel getRgbColor(int x, int y);

	void setColor(ColorModel color, int x, int y);

	void flush();

}
