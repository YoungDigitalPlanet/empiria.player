package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.util.position.Point;

@Singleton
public class RectangleChecker {

    private final static int ACCEPTABLE_ERROR_LEVEL = 15;

    public boolean isPointInLineSegmentRectangle(LineSegment lineSegment, Point point) {

        Point pointStart = lineSegment.getPointStart();
        Point pointEnd = lineSegment.getPointEnd();
        if (isValueBetweenTwoNumbers(pointStart.getX(), pointEnd.getX(), point.getX())
                && isValueBetweenTwoNumbers(pointStart.getY(), pointEnd.getY(), point.getY())) {
            return true;
        }

        return false;

    }

    private boolean isValueBetweenTwoNumbers(int firstNumber, int secondNumber, int testedValue) {
        return ((firstNumber - ACCEPTABLE_ERROR_LEVEL <= testedValue && testedValue <= secondNumber + ACCEPTABLE_ERROR_LEVEL) || (secondNumber
                - ACCEPTABLE_ERROR_LEVEL <= testedValue && testedValue <= firstNumber + ACCEPTABLE_ERROR_LEVEL));

    }
}
