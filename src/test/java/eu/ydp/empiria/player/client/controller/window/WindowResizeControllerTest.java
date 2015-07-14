package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class WindowResizeControllerTest {

    @InjectMocks
    private WindowResizeController testObj;
    @Mock
    private WindowResizeTimer commandTimer;
    @Mock
    private WindowDelegate windowDelegate;

    @Test
    public void shouldAddResizeHandler() {
        // given

        // when

        // then
        verify(windowDelegate).addResizeHandler(testObj);
    }

    @Test
    public void shouldScheduleTimer() {
        // given
        ResizeEvent event = mock(ResizeEvent.class);
        int delayMillis = 250;

        // when
        testObj.onResize(event);

        // then
        verify(commandTimer).schedule(delayMillis);
    }
}
