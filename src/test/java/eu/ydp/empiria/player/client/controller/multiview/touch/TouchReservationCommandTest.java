package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.dom.client.NativeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TouchReservationCommandTest {

    @InjectMocks
    private TouchReservationCommand testObj;
    @Mock
    private TouchController touchController;

    @Test
    public void shouldReserveTouch() {
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);

        // when
        testObj.execute(nativeEvent);

        // then
        verify(touchController).setTouchReservation(true);
    }
}