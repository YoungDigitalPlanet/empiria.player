package eu.ydp.empiria.player.client.module.colorfill.fill;

import eu.ydp.empiria.player.client.module.colorfill.fill.stack.JsPoint;
import eu.ydp.empiria.player.client.module.colorfill.fill.stack.JsStack;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;

@SuppressWarnings("PMD")
public class FloodFillScanLine {
	private JsStack queue;
	private final CanvasImageData imageData;
	private final ContourDetector contourDetector;
	private final ColorModel color;
	private final int width;
	private final int height;

	public FloodFillScanLine(final CanvasImageData imageData, final ContourDetector contourDetector, final ColorModel color) {
		this.imageData = imageData;
		this.width = imageData.getImageWidth();
		this.height = imageData.getImageHeight();
		this.contourDetector = contourDetector;
		this.color = color;
	}

	private boolean isPixelSet(final int x, final int y) {
		ColorModel rgbColor = imageData.getRgbColor(x, y);
		return contourDetector.isContourColor(rgbColor) || rgbColor.equalsWithoutAlpha(color);
	}

	private void pushToQueue(final int x, final int y) {
		if (!isPixelSet(x, y)) {
			queue.push(JsPoint.newPoint(x, y));
		}
	}

	public void fillArea(final int x, final int y) {
		queue = JsStack.newJsStack();
		pushToQueue(x, y);
		fill();
	}

	private void fill() {
		int x,y;
		boolean spanLeft, spanRight;
		while (!queue.isEmpty()) {
			JsPoint p = queue.pop();
			x = p.getX();
			y = p.getY();

			int y1 = y;
			while (y1 >= 0 && !isPixelSet(x, y1)) {
				y1--;
			}

			y1++;
			spanLeft = spanRight = false;
			while (y1 < height && !isPixelSet(x, y1)) {
				setPixelColor(x, y1);
				boolean filled = false;
				if (x > 0) {
					int currentCol = x - 1;
					filled = isPixelSet(currentCol, y1);
					if (!spanLeft && !filled) {
						pushToQueue(currentCol, y1);
						spanLeft = true;
					} else if (spanLeft && filled) {
						spanLeft = false;
					}
				}
				if (x < width - 1) {
					int currentCol = x + 1;
					filled = isPixelSet(currentCol, y1);
					if (!spanRight && !filled) {
						pushToQueue(currentCol, y1);
						spanRight = true;
					} else if (spanRight && filled) {
						spanRight = false;
					}
				}
				y1++;
			}
		}
	}

	private void setPixelColor(final int x, final int y) {
		imageData.setColor(color, x, y);
	}

}
