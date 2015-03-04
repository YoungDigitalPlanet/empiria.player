package eu.ydp.empiria.player.client.module.colorfill.fill;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class BlackColorContourDetector implements ContourDetector {

	@Override
	public boolean isContourColor(final ColorModel rgbColor) {
		return rgbColor.getBlue() == 0 && rgbColor.getGreen() == 0 && rgbColor.getRed() == 0 && rgbColor.getAlpha() > 250;
	}

}
