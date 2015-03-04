package eu.ydp.empiria.player.client.module.img.events;

import eu.ydp.empiria.player.client.util.position.Point;

public interface CanvasMoveEvents {
	public void onMoveStart(Point point);

	public void onMoveScale(Point firstFinger, Point secondFinger);

	public void onMoveMove(Point point);

	public void onMoveEnd();
}
