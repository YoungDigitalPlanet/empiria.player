package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DistanceCalculatorTest {

    @InjectMocks
    private DistanceCalculator testObj;

    @Test
    public void testCalculateDistanceBetween_shouldReturn0() {
        LineSegment lineSegment = new LineSegment(new Point(0, 0), new Point(4, 4));
        Point point = new Point(1, 1);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(0d, result, 0);
    }

    @Test
    public void testCalculateDistanceBetween_shouldReturn2() {

        LineSegment lineSegment = new LineSegment(new Point(0, 0), new Point(4, 0));
        Point point = new Point(1, 2);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(2d, result, 0);
    }

    @Test
    public void testCalculateDistanceBetween_shouldReturn1() {

        LineSegment lineSegment = new LineSegment(new Point(0, 5), new Point(0, 1));
        Point point = new Point(1, 8);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(1d, result, 0);
    }

}
