package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;

public class SurfacePointTranslator {

	public Point translatePoint(int x, int y, ConnectionSurface surface){
		return translatePoint(new Point(x, y), surface);
	}
	
	public Point translatePoint(Point point, ConnectionSurface surface){
		return new Point(point.getX() - surface.getOffsetLeft(), point.getY());
	}
	
}
