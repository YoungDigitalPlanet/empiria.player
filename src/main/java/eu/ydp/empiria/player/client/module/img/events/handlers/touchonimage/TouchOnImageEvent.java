package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.util.position.Point;

import java.util.List;

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
