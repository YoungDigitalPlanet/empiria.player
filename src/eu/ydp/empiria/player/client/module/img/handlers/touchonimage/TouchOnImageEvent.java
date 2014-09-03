package eu.ydp.empiria.player.client.module.img.handlers.touchonimage;

import java.util.List;

import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageEvent {
	private final List<Point> coordinatesList;

	public TouchOnImageEvent(List<Point> coordinatesList) {
		this.coordinatesList = coordinatesList;
	}

	public Point getPoint(int index) {
		return coordinatesList.get(index);
	}

	public int getLength() {
		return coordinatesList.size();
	}
}
