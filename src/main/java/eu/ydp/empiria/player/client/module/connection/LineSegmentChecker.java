package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.util.position.Point;

@Singleton
public class LineSegmentChecker {

    private final static double ACCEPTABLE_ERROR_LEVEL = 15.0;

    private final DistanceCalculator distanceCalculator;
    private final RectangleChecker rectangleChecker;

    @Inject
    public LineSegmentChecker(DistanceCalculator distanceCalculator, RectangleChecker rectangleChecker) {
        this.distanceCalculator = distanceCalculator;
        this.rectangleChecker = rectangleChecker;
    }

    public boolean isLineSegmentNearPoint(LineSegment lineSegment, Point point) {
        if (!rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point)) {
            return false;
        }

        double distance = distanceCalculator.calculateDistanceBetween(lineSegment, point);

        return distance < ACCEPTABLE_ERROR_LEVEL;
    }
}
