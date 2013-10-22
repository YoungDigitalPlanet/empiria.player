package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.style.StyleToPropertyMappingHelper;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import gwt.g2d.client.graphics.Surface;
import gwt.g2d.client.graphics.canvas.Context;
import gwt.g2d.client.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConnectionSurfaceView extends Composite {

	protected Context context2d;
	protected Surface surface;
	private final StyleToPropertyMappingHelper styleHelper;
	private LineSegment lineSegment;

	private final Map<String, String> propertiesToClear = new HashMap<String, String>();
	private Map<String, String> lastSetStyles = new HashMap<String, String>();
	private final boolean IS_ANDROID_4 = UserAgentChecker.isMobileUserAgent(MobileUserAgent.ANDROID4);
	private final SurfaceCleaner surfaceCleaner = new SurfaceCleaner();

	private final LineSegmentChecker lineSegmentChecker = new LineSegmentChecker(new DistanceCalculator(), new RectangleChecker());

	public ConnectionSurfaceView(Vector2 vector, StyleToPropertyMappingHelper styleHelper) {
		this.styleHelper = styleHelper;
		surface = new Surface(vector);
		context2d = surface.getContext();
		initWidget(surface);
		// ma znajdowac sie pod caloscia
		surface.getElement().getStyle().setPosition(Position.ABSOLUTE);
		surface.getElement().getStyle().setLeft(0, Unit.PX);
		surface.getElement().getStyle().setTop(0, Unit.PX);
	}

	public void drawLine(Point start, Point end) {
		context2d.save();
		lineSegment = new LineSegment(start, end);
		clear();

		context2d.beginPath();
		context2d.moveTo(start.getX(), start.getY());
		context2d.lineTo(end.getX(), end.getY());
		context2d.stroke();
		context2d.restore();

	}

	public void updateStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context2d, styles);
	}

	public void clear() {
		surfaceCleaner.clean(lineSegment, context2d);
		applyHackForCanvasInAndroid4();
	}

	/**
	 * @see <a
	 *      href="http://code.google.com/p/android/issues/detail?id=35474#c25"
	 *      >android bug trucker</a>
	 */
	private void applyHackForCanvasInAndroid4() {
		if (IS_ANDROID_4) {
			surface.setWidth(surface.getWidth());
			applyStyles(lastSetStyles);
		}
	}

	public Widget getView() {
		return surface;
	}

	public boolean isPointOnPath(Point point) {
		return lineSegmentChecker.isLineSegmentNearPoint(lineSegment, point);
	}

	public void applyStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context2d, propertiesToClear);
		styleHelper.applyStyles((JavaScriptObject) context2d, styles);
		lastSetStyles = styles;
		propertiesToClear.clear();
		for (String property : styles.keySet()) {
			propertiesToClear.put(property, "");
		}
	}
}
