package eu.ydp.empiria.player.client.module.connection;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.style.StyleToPropertyMappingHelper;

/**
 * Widok polaczen
 *
 * @author plelakowski
 *
 */
public class ConnectionSurfaceImpl implements ConnectionSurface {
	private final ConnectionSurfaceView view;

	@Inject
	public ConnectionSurfaceImpl(StyleToPropertyMappingHelper styleHelper,@Assisted("width") Integer width, @Assisted("height") Integer height) {
		view = new ConnectionSurfaceView(width, height, styleHelper);

	}

	@Override
	public Widget asWidget() {
		return view;
	}

	@Override
	public void drawLine(Point from, Point to) {
		view.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
	}

	@Override
	public void clear() {
		view.clear();
	}

	@Override
	public boolean isPointOnPath(int xPos, int yPos, int approximation) {
		return view.isPointOnPath(xPos, yPos, approximation);
	}

	@Override
	public void applyStyles(Map<String, String> styles){
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
	public void setOffsetLeft(int left) {
		view.getElement().getStyle().setLeft(left, Unit.PX);
	}

}
