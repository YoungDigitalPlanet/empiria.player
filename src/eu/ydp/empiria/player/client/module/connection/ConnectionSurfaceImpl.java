package eu.ydp.empiria.player.client.module.connection;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.style.StyleHelper;

/**
 * Widok polaczen
 *
 * @author plelakowski
 *
 */
public class ConnectionSurfaceImpl implements ConnectionSurface {
	private final ConnectionSurfaceView view;

	@Inject
	public ConnectionSurfaceImpl(StyleHelper styleHelper,@Assisted int width, @Assisted int height) {
		view = new ConnectionSurfaceView(width, height, styleHelper);

	}

	@Override
	public Widget asWidget() {
		return view;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.ConnectionSurfce#drawLine(double, double, double, double)
	 */
	@Override
	public void drawLine(double fromX, double fromY, double toX, double toY) {
		view.drawLine(fromX, fromY, toX, toY);
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.ConnectionSurfce#clear()
	 */
	@Override
	public void clear() {
		view.clear();
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.ConnectionSurfce#isPointOnPath(int, int, int)
	 */
	@Override
	public boolean isPointOnPath(int xPos, int yPos, int approximation) {
		return view.isPointOnPath(xPos, yPos, approximation);
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.ConnectionSurfce#applyStyles(java.util.Map)
	 */
	@Override
	public void applyStyles(Map<String, String> styles){
		view.applyStyles(styles);
	}

	@Override
	public void removeFromParent() {
		view.removeFromParent();
	}

}
