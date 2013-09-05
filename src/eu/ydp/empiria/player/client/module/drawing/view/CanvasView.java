package eu.ydp.empiria.player.client.module.drawing.view;

import eu.ydp.gwtutil.client.util.geom.Size;

public interface CanvasView extends DrawCanvas {
	 void setBackground(String url);
	 void setSize(Size size);
}
