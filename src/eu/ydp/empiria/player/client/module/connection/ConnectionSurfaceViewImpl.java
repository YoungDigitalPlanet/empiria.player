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
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ConnectionSurfaceViewImpl extends Composite implements ConnectionSurfaceView {

	private final Context context;
	private final Surface surface;
	private final StyleToPropertyMappingHelper styleHelper;
	private final SurfaceCleaner surfaceCleaner;
	private final LineSegmentChecker lineSegmentChecker;
	private final Map<String, String> propertiesToClear = new HashMap<String, String>();

	private final boolean IS_ANDROID_4 = UserAgentChecker.isMobileUserAgent(MobileUserAgent.ANDROID4);

	private Map<String, String> lastSetStyles = new HashMap<String, String>();
	private LineSegment lineSegment;

	@Inject
	public ConnectionSurfaceViewImpl(@Assisted Vector2 vector, StyleToPropertyMappingHelper styleHelper, LineSegmentChecker lineSegmentChecker,
			SurfaceCleaner surfaceCleaner) {
		this.styleHelper = styleHelper;
		this.lineSegmentChecker = lineSegmentChecker;
		this.surfaceCleaner = surfaceCleaner;
		this.surface = new Surface(vector);
		this.context = surface.getContext();
		initWidget(surface);
		// ma znajdowac sie pod caloscia
		setStylesForSurface();
	}

	private void setStylesForSurface() {
		surface.getElement().getStyle().setPosition(Position.ABSOLUTE);
		surface.getElement().getStyle().setLeft(0, Unit.PX);
		surface.getElement().getStyle().setTop(0, Unit.PX);
	}

	@Override
	public void drawLine(Point start, Point end) {
		context.save();
		lineSegment = new LineSegment(start, end);
		clear();

		context.beginPath();
		context.moveTo(start.getX(), start.getY());
		context.lineTo(end.getX(), end.getY());
		context.stroke();
		context.restore();
	}

	public void updateStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context, styles);
	}

	@Override
	public void clear() {
		surfaceCleaner.clean(lineSegment, context);
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

	@Override
	public boolean isPointOnPath(Point point) {
		return lineSegmentChecker.isLineSegmentNearPoint(lineSegment, point);
	}

	@Override
	public void applyStyles(Map<String, String> styles) {
		styleHelper.applyStyles((JavaScriptObject) context, propertiesToClear);
		styleHelper.applyStyles((JavaScriptObject) context, styles);
		lastSetStyles = styles;
		propertiesToClear.clear();
		for (String property : styles.keySet()) {
			propertiesToClear.put(property, "");
		}
	}

	@Override
	public void setOffsetLeft(int offsetLeft) {
		getElement().getStyle().setLeft(offsetLeft, Unit.PX);
	}

	@Override
	public int getOffsetLeft() {
		return getElement().getOffsetLeft();
	}

	@Override
	public void setOffsetTop(int offsetTop) {
		getElement().getStyle().setTop(offsetTop, Unit.PX);
	}
}
