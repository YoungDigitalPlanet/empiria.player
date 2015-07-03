package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerAttachHandlerTest {

    @Mock
    private EventsBus eventsBus;
    @Mock
    private VideoPlayer videoPlayer;
    @InjectMocks
    private VideoPlayerAttachHandler testObj;

    @Test
    public void shouldRegisterHandlerAtFirstAttachEvent() {
        // given
        AttachEvent attachEvent = mock(AttachEvent.class);
        when(attachEvent.isAttached()).thenReturn(Boolean.TRUE);
        HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);
        when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class))).thenReturn(
                handlerRegistration);

        // when
        testObj.onAttachOrDetach(attachEvent);

        // then
        verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class));
    }

    @Test
    public void shouldRemoveHandlerAtSecondAttachEvent() {
        // given
        HandlerRegistration handlerRegistration = callFirstAttachEvent();
        AttachEvent event = mock(AttachEvent.class);
        when(event.isAttached()).thenReturn(Boolean.FALSE);

        // when
        testObj.onAttachOrDetach(event);

        // then
        verify(handlerRegistration).removeHandler();
    }

    private HandlerRegistration callFirstAttachEvent() {
        AttachEvent attachEvent = mock(AttachEvent.class);
        when(attachEvent.isAttached()).thenReturn(Boolean.TRUE);
        HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);
        when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class))).thenReturn(
                handlerRegistration);

        testObj.onAttachOrDetach(attachEvent);

        return handlerRegistration;
    }
}
