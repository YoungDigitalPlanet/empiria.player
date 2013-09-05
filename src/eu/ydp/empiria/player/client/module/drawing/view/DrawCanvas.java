package eu.ydp.empiria.player.client.module.drawing.view;

import com.mathplayer.player.geom.Color;

import eu.ydp.empiria.player.client.util.position.Point;

public interface DrawCanvas {
	 void drawPoint( Point point, Color color);
	 void drawLine( Point startPoint , Point endPoint, Color color);
	 void erasePoint(Point point);
	 void eraseLine(Point startPoint , Point endPoint );
	 void clear();
}
