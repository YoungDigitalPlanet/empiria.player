package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RectangleCheckerTest {

    @InjectMocks
    private RectangleChecker rectangleChecker;

    @Test
    public void isPointInLineSegmentRectangleTest_shouldReturnTrue() {

        // given
        LineSegment lineSegment = new LineSegment(new Point(30, 30), new Point(0, 0));

        for (int i = -15; i < 46; i++) {
            // when

            Point point = new Point(i, i);
            boolean result = rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point);

            // then

            assertTrue(result);

        }

    }

    @Test
    public void isPointInLineSegmentRectangleTest_shouldReturnFalse_wrongXLowerLimit() {

        // given
        LineSegment lineSegment = new LineSegment(new Point(30, 30), new Point(0, 0));

        for (int i = -46; i < -15; i++) {
            // when

            Point point = new Point(i, 5);
            boolean result = rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point);

            // then

            assertFalse(result);

        }
    }

    @Test
    public void isPointInLineSegmentRectangleTest_shouldReturnFalse_wrongXUpperLimit() {

        // given
        LineSegment lineSegment = new LineSegment(new Point(30, 30), new Point(0, 0));

        for (int i = 46; i < 55; i++) {
            // when

            Point point = new Point(i, 5);
            boolean result = rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point);

            // then

            assertFalse(result);

        }
    }

    @Test
    public void isPointInLineSegmentRectangleTest_shouldReturnFalse_wrongYLowerLimit() {

        // given
        LineSegment lineSegment = new LineSegment(new Point(30, 30), new Point(0, 0));

        for (int i = -46; i < -15; i++) {
            // when

            Point point = new Point(5, i);
            boolean result = rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point);

            // then

            assertFalse(result);

        }
    }

    @Test
    public void isPointInLineSegmentRectangleTest_shouldReturnFalse_wrongYUpperLimit() {

        // given
        LineSegment lineSegment = new LineSegment(new Point(30, 30), new Point(0, 0));

        for (int i = 46; i < 55; i++) {
            // when

            Point point = new Point(5, i);
            boolean result = rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point);

            // then

            assertFalse(result);

        }
    }
}
