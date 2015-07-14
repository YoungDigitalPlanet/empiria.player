package eu.ydp.empiria.player.client.util.position;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("PMD")
public class PointJUnitTest {

    private Point point;

    @Test
    public void testPoint() {
        point = new Point(20, 30);
        assertEquals(20, point.getX());
        assertEquals(30, point.getY());

        point = new Point(-1, -70);
        assertEquals(-1, point.getX());
        assertEquals(-70, point.getY());

    }

}
