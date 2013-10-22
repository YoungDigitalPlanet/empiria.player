package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.style.StyleToPropertyMappingHelper;
import gwt.g2d.client.math.Vector2;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Widok polaczen
 * 
 * @author plelakowski
 * 
 */
public class ConnectionSurfaceImpl implements ConnectionSurface {
	private final ConnectionSurfaceView view;
	private int offsetTop;
	private int offsetLeft;

	@Inject
	public ConnectionSurfaceImpl(@Assisted Vector2 vector, StyleToPropertyMappingHelper styleHelper) {
		view = new ConnectionSurfaceView(vector, styleHelper);

	}

	@Override
	public Widget asWidget() {
		return view;
	}

	@Override
	public void drawLine(Point from, Point to) {
		Point relativeStart = getRelativePoint(from);
		Point relativeEnd = getRelativePoint(to);
		view.drawLine(relativeStart, relativeEnd);
	}

	private Point getRelativePoint(Point point) {
		return new Point(point.getX() - offsetLeft, point.getY() - offsetTop);
	}

	@Override
	public void clear() {
		view.clear();
	}

	@Override
	public boolean isPointOnPath(Point point) {
		return view.isPointOnPath(point);
	}

	@Override
	public void applyStyles(Map<String, String> styles) {
		view.applyStyles(styles);
	}

	@Override
	public void removeFromParent() {
		view.removeFromParent();
	}

	@Override
	public int getOffsetLeft() {
		return view.getElement().getOffsetLeft();
	}

	@Override
	public void setOffsetLeft(int offsetLeft) {
		this.offsetLeft = offsetLeft;
		view.getElement().getStyle().setLeft(offsetLeft, Unit.PX);
	}

	@Override
	public void setOffsetTop(int offsetTop) {
		this.offsetTop = offsetTop;
		view.getElement().getStyle().setTop(offsetTop, Unit.PX);
	}

}
