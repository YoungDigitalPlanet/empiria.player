package eu.ydp.empiria.player.client.controller.multiview;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHack;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResizeContinuousUpdaterTest {

    @InjectMocks
    private ResizeContinuousUpdater testObj;
    @Mock
    private PageScopeFactory pageScopeFactory;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private MultiPageController pageView;
    @Mock
    private ForceRedrawHack redrawHack;
    @Mock
    private CurrentPageScope currentPageScope;
    @Captor
    private ArgumentCaptor<PlayerEvent> playerEventCaptor;

    @Before
    public void init() {
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(currentPageScope);
    }

    @Test
    public void shouldResizePageContainerOnce() {
        // given
        when(pageView.getCurrentPageHeight()).thenReturn(100);

        //when
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();

        // then

        verify(pageView).setHeight(100);
        verify(pageView).hideCurrentPageProgressBar();
    }

    @Test
    public void shouldFirePageGrownEvent_onThirdExecute_withSameHeight() {
        // given
        when(pageView.getCurrentPageHeight()).thenReturn(100);

        // when
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();

        // then
        verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture(), eq(currentPageScope));
        PlayerEvent playerEvent = playerEventCaptor.getValue();
        PlayerEventTypes type = playerEvent.getType();
        assertThat(type).isEqualTo(PlayerEventTypes.PAGE_CONTENT_GROWN);
    }

    @Test
    public void shouldFirePageDecreasedEvent_onThirdExecute_afterDecreasingHeight() {
        // given
        when(pageView.getCurrentPageHeight()).thenReturn(100);
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        when(pageView.getCurrentPageHeight()).thenReturn(50);

        // when
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();

        // then
        verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture(), eq(currentPageScope));
        PlayerEvent playerEvent = playerEventCaptor.getValue();
        PlayerEventTypes type = playerEvent.getType();
        assertThat(type).isEqualTo(PlayerEventTypes.PAGE_CONTENT_DECREASED);
    }

    @Test
    public void shouldFireEventOnce_whenHeightIsNotChanging() {
        // given
        when(pageView.getCurrentPageHeight()).thenReturn(100);

        // when
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();

        // then
        verify(eventsBus, times(1)).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
    }
}