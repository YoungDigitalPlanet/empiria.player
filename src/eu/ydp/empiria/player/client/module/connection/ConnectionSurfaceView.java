package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.style.StyleHelper;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.Context;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConnectionSurfaceView extends Composite {

	// protected Context view = null;
	protected Context context2d;
	protected Surface surface;
	private final StyleHelper styleHelper;
	private final double[] coordinates = new double[4];
	private final Map<String, String> lastSetProperies = new HashMap<String, String>();

	public ConnectionSurfaceView(int width, int height, StyleHelper styleHelper) {
		this.styleHelper = styleHelper;
		surface = new Surface(width, height);
		context2d = surface.getContext();
		initWidget(surface);
		// ma znajdowac sie pod caloscia
		surface.getElement().getStyle().setPosition(Position.ABSOLUTE);
		surface.getElement().getStyle().setLeft(0, Unit.PX);
		surface.getElement().getStyle().setTop(0, Unit.PX);
	}

	public void drawLine(double fromX, double fromY, double toX, double toY) {
		context2d.save();
		clear();
		coordinates[0] = fromX;
		coordinates[1] = fromY;
		coordinates[2] = toX;
		coordinates[3] = toY;
		context2d.beginPath();
		context2d.moveTo(fromX, fromY);
		context2d.lineTo(toX, toY);
		context2d.stroke();
		context2d.restore();

	}

	public double[] getReactToClear() {
		double[] rect = new double[4];
		rect[0] = coordinates[0] > coordinates[2] ? coordinates[2] - 25 : coordinates[0] - 25;
		rect[1] = coordinates[1] > coordinates[3] ? coordinates[3] - 25 : coordinates[1] - 25;
		rect[2] = Math.abs(coordinates[0] - coordinates[2]) + 50;
		rect[3] = Math.abs(coordinates[1] - coordinates[3]) + 50;
		return rect;
	}

	public void updateStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context2d, styles);
	}

	public void clear() {
		double[] rect = getReactToClear();
		context2d.clearRect(rect[0], rect[1], rect[2], rect[3]);
	}

	public Widget getView() {
		return surface;
	}

	public boolean isPointOnPath(int xPos, int yPos, int approximation) { // NOPMD
		return ptLineDist(coordinates[0], coordinates[1], coordinates[2], coordinates[3], xPos, yPos) < approximation / 2;
	}

	public void applyStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context2d, lastSetProperies);
		styleHelper.applyStyles((JavaScriptObject) context2d, styles);
		lastSetProperies.clear();
		for (String property : styles.keySet()) {
			lastSetProperies.put(property, "");
		}
	}

	/**
	 * Returns the square of the distance from a point to a line. The distance
	 * measured is the distance between the specified point and the closest
	 * point on the infinitely-extended line defined by the specified
	 * coordinates. If the specified point intersects the line, this method
	 * returns 0.0.
	 *
	 * @param x1
	 *            the X coordinate of the start point of the specified line
	 * @param y1
	 *            the Y coordinate of the start point of the specified line
	 * @param x2
	 *            the X coordinate of the end point of the specified line
	 * @param y2
	 *            the Y coordinate of the end point of the specified line
	 * @param px
	 *            the X coordinate of the specified point being measured against
	 *            the specified line
	 * @param py
	 *            the Y coordinate of the specified point being measured against
	 *            the specified line
	 * @return a double value that is the square of the distance from the
	 *         specified point to the specified line.
	 * @see #ptSegDistSq(double, double, double, double, double, double)
	 * @since 1.2
	 */
	public double ptLineDistSq(double x1, double y1, double x2, double y2, double px, double py) {//NOPMD
		// Adjust vectors relative to x1,y1
		// x2,y2 becomes relative vector from x1,y1 to end of segment
		x2 -= x1;
		y2 -= y1;
		// px,py becomes relative vector from x1,y1 to test point
		px -= x1;
		py -= y1;
		double dotprod = px * x2 + py * y2;
		// dotprod is the length of the px,py vector
		// projected on the x1,y1=>x2,y2 vector times the
		// length of the x1,y1=>x2,y2 vector
		double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
		// Distance to line is now the length of the relative point
		// vector minus the length of its projection onto the line
		double lenSq = px * px + py * py - projlenSq;
		if (lenSq < 0) {
			lenSq = 0;
		}
		return lenSq;
	}

	/**
	 * Returns the distance from a point to a line. The distance measured is the
	 * distance between the specified point and the closest point on the
	 * infinitely-extended line defined by the specified coordinates. If the
	 * specified point intersects the line, this method returns 0.0.
	 *
	 * @param x1
	 *            the X coordinate of the start point of the specified line
	 * @param y1
	 *            the Y coordinate of the start point of the specified line
	 * @param x2
	 *            the X coordinate of the end point of the specified line
	 * @param y2
	 *            the Y coordinate of the end point of the specified line
	 * @param px
	 *            the X coordinate of the specified point being measured against
	 *            the specified line
	 * @param py
	 *            the Y coordinate of the specified point being measured against
	 *            the specified line
	 * @return a double value that is the distance from the specified point to
	 *         the specified line.
	 * @see #ptSegDist(double, double, double, double, double, double)
	 * @since 1.2
	 */
	public double ptLineDist(double x1, double y1, double x2, double y2, double px, double py) {//NOPMD
		return Math.sqrt(ptLineDistSq(x1, y1, x2, y2, px, py));
	}
}
