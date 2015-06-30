package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TouchOnImageEventTest {

    @InjectMocks
    private TouchOnImageEvent testObj;
    @Mock
    private List<Point> coordinatesList;

    @Test
    public void shouldReturnPoint() {
        // given
        int sampleValue = 0;
        Point point = new Point(sampleValue, sampleValue);

        when(coordinatesList.get(anyInt())).thenReturn(point);

        // when
        Point result = testObj.getPoint(anyInt());

        // then
        assertThat(result, is(point));
    }

    @Test
    public void shouldReturnLength() {
        // given
        int length = 2;
        when(coordinatesList.size()).thenReturn(length);

        // when
        int result = testObj.getLength();

        // then
        assertThat(result, is(length));
    }
}
