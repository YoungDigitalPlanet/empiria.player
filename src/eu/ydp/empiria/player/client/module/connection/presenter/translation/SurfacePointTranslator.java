package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;

public class SurfacePointTranslator {
	
	public Point translatePoint(Point point, ConnectionSurface surface){
		return translatePoint(point, surface.getOffsetLeft());
	}

	public Point translatePoint(Point point, int offsetLeft) {
		return new Point(point.getX() - offsetLeft, point.getY());
	}
	
}
