package eu.ydp.empiria.player.client.module.drawing.view;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

public interface DrawCanvas {
	 void drawPoint( Point point, ColorModel color);
	 void drawLine( Point startPoint , Point endPoint, ColorModel color);
	 void erasePoint(Point point);
	 void eraseLine(Point startPoint , Point endPoint );
	 void clear();
}
