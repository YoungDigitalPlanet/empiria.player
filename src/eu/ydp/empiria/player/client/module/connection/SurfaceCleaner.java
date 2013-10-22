package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;
import gwt.g2d.client.graphics.canvas.Context;

public class SurfaceCleaner {

	private static final int BORDER_WIDTH = 25;
	private static final int BORDER_COUNT = 2;

	public void clean(LineSegment lineSegment, Context context) {
		if (lineSegment == null) {
			return;
		}
		Point pointStart = lineSegment.getPointStart();
		Point pointEnd = lineSegment.getPointEnd();

		int x = getLowerNumberWithBorderWidth(pointStart.getX(), pointEnd.getX());
		int y = getLowerNumberWithBorderWidth(pointStart.getY(), pointEnd.getY());

		int width = getAbsFromDifferenceAndAddBorder(pointStart.getX(), pointEnd.getX());
		int height = getAbsFromDifferenceAndAddBorder(pointStart.getY(), pointEnd.getY());

		context.clearRect(x, y, width, height);
	}

	private int getAbsFromDifferenceAndAddBorder(int first, int second) {
		return Math.abs(first - second) + (BORDER_WIDTH * BORDER_COUNT);
	}

	private int getLowerNumberWithBorderWidth(int first, int second) {
		return first > second ? second - BORDER_WIDTH : first - BORDER_WIDTH;
	}
}
