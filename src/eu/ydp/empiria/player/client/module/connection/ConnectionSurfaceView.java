package eu.ydp.empiria.player.client.module.connection;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;

import eu.ydp.empiria.player.client.util.style.StyleHelper;

public class ConnectionSurfaceView extends Composite {

	protected Canvas view = null;
	protected Context2d context2d;
	private final int height;
	private final int width;
	private final StyleHelper styleHelper;
	private final double[] coordinates = new double[4];
	private final Map<String, String> lastSetProperies = new HashMap<String, String>();

	public ConnectionSurfaceView(int width, int height, StyleHelper styleHelper) {
		this.width = width;
		this.height = height;
		this.styleHelper = styleHelper;
		view = Canvas.createIfSupported();
		view.setCoordinateSpaceWidth(width);
		view.setCoordinateSpaceHeight(height);
		view.setWidth(width + "px");
		view.setHeight(height + "px");
		context2d = view.getContext2d();
		initWidget(view);
		// ma znajdowac sie pod caloscia
		view.getElement().getStyle().setPosition(Position.ABSOLUTE);
		view.getElement().getStyle().setLeft(0, Unit.PX);
		view.getElement().getStyle().setTop(0, Unit.PX);
	}

	public void drawLine(double fromX, double fromY, double toX, double toY) {
		clear();
		coordinates[0] = fromX;
		coordinates[1] = fromY;
		coordinates[2] = toX;
		coordinates[3] = toY;

		context2d.beginPath();
		context2d.moveTo(fromX, fromY);
		context2d.lineTo(toX, toY);
		context2d.closePath();
		context2d.stroke();

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
		styleHelper.applyStyles(context2d, styles);
	}

	public void clear() {
		double[] rect = getReactToClear();
		context2d.clearRect(rect[0], rect[1], rect[2], rect[3]);
	}

	public Canvas getView() {
		return view;
	}

	public boolean isPointOnPath(int xPos, int yPos, int approximation) { // NOPMD
		ImageData imageData = context2d.getImageData(0, 0, width, height);

		int xPosition = xPos - approximation / 2;
		int yPosition = yPos - approximation / 2;
		approximation = approximation < 0 ? 0 : approximation;

		xPosition = xPosition < 0 ? 0 : xPosition;
		yPosition = yPosition < 0 ? 0 : yPosition;
		boolean returnValue = false;
		for (int x = xPosition; x <= xPosition + approximation; ++x) {
			if (imageData.getAlphaAt(x, yPosition) > 0) {
				returnValue = true;
				break;
			}
		}
		if (!returnValue) {
			for (int y = yPosition; y <= yPosition + approximation; ++y) {
				if (imageData.getAlphaAt(xPosition, y) > 0) {
					returnValue = true;
					break;
				}
			}
		}
		return returnValue;
	}

	public void applyStyles(Map<String, String> styles) {
		styleHelper.applyStyles(context2d, lastSetProperies);
		styleHelper.applyStyles(context2d, styles);
		lastSetProperies.clear();
		for (String property : styles.keySet()) {
			lastSetProperies.put(property, "");
		}
	}
}
