package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import eu.ydp.empiria.player.client.util.position.Point;

public interface Tool {
	public void start(Point point);

	public void move(Point start, Point end);

	public void setUp();
}
