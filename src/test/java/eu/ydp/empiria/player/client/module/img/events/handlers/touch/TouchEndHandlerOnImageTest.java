package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchEndEvent;
import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TouchEndHandlerOnImageTest {

    @InjectMocks
    private TouchEndHandlerOnImage testObj;
    @Mock
    private TouchToImageEvent touchToImageEvent;
    @Mock
    private TouchOnImageEndHandler touchOnImageEndHandler;
    @Mock
    private TouchEndEvent event;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;

    @Test
    public void shouldRunOnEnd() {
        // given
        when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

        // when
        testObj.onTouchEnd(event);

        // then
        verify(touchOnImageEndHandler).onEnd(touchOnImageEvent);
        verify(event).preventDefault();
    }

}
