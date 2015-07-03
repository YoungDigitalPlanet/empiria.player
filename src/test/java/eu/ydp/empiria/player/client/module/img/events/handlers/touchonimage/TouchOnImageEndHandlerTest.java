package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TouchOnImageEndHandlerTest {

    @InjectMocks
    private TouchOnImageEndHandler testObj;
    @Mock
    private CanvasMoveEvents canvasMoveEvents;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;

    @Test
    public void shouldRunOnMoveEnd() {
        // given

        // when
        testObj.onEnd(touchOnImageEvent);

        // then
        verify(canvasMoveEvents).onMoveEnd();
    }
}
