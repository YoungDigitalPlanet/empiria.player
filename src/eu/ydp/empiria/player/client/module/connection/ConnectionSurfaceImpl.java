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
	private int offsetTop;
	private int offsetLeft;

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
		view.drawLine(from.getX()-offsetLeft, from.getY()-offsetTop, to.getX()-offsetLeft, to.getY()-offsetTop);
	}

	@Override
	public void clear() {
		view.clear();
	}

	@Override
	public boolean isPointOnPath(int xPos, int yPos, int approximation) {
		return view.isPointOnPath(xPos-offsetLeft, yPos-offsetTop, approximation);
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
