/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

    private final int INIT_HEIGHT = 100;
    private final int TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT = 3;

    @Before
    public void init() {
        when(pageView.getCurrentPageHeight()).thenReturn(INIT_HEIGHT);
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(currentPageScope);
    }

    @Test
    public void shouldResizePageContainer() {
        //when
        int result = testObj.runContinuousResizeUpdateAndReturnRescheduleTime();

        // then

        verify(pageView).setHeight(INIT_HEIGHT);
        verify(pageView).hideCurrentPageProgressBar();
        verify(eventsBus, never()).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
        assertThat(result).isEqualTo(ResizeContinuousUpdater.DELAY_MILLIS);
    }

    @Test
    public void shouldWaitWithFirePageGrownEvent_untilThirdExecute_withSameHeight() {
        // given
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        when(pageView.getCurrentPageHeight()).thenReturn(INIT_HEIGHT * 2);

        // when
        int result = runResizeUpdateNTimes(TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT - 1);

        // then
        verify(eventsBus, never()).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));

        assertThat(result).isEqualTo(ResizeContinuousUpdater.DELAY_MILLIS);
    }

    @Test
    public void shouldFirePageGrownEvent_afterThirdExecute_withSameHeight() {
        // given
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        int higherHeight = INIT_HEIGHT * 2;
        when(pageView.getCurrentPageHeight()).thenReturn(higherHeight);

        // when
        int result = runResizeUpdateNTimes(TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT);

        // then
        verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture(), eq(currentPageScope));
        PlayerEvent playerEvent = playerEventCaptor.getValue();
        PlayerEventTypes type = playerEvent.getType();
        assertThat(type).isEqualTo(PlayerEventTypes.PAGE_CONTENT_GROWN);

        assertThat(result).isEqualTo(ResizeContinuousUpdater.DELAY_MILLIS);
    }

    @Test
    public void shouldNotFireEvent_whenResetHasBeenExecuted() {
        // when
        runResizeUpdateNTimes(2);
        testObj.reset();
        runResizeUpdateNTimes(2);

        // then
        verify(eventsBus, never()).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
    }

    @Test
    public void shouldFirePageDecreasedEvent_afterThirdExecute_afterDecreasingHeight() {
        // given
        testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        int smallerHeight = INIT_HEIGHT / 2;
        when(pageView.getCurrentPageHeight()).thenReturn(smallerHeight);

        // when
        int result = runResizeUpdateNTimes(TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT);

        // then
        verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture(), eq(currentPageScope));
        PlayerEvent playerEvent = playerEventCaptor.getValue();
        PlayerEventTypes type = playerEvent.getType();
        assertThat(type).isEqualTo(PlayerEventTypes.PAGE_CONTENT_DECREASED);

        assertThat(result).isEqualTo(ResizeContinuousUpdater.DELAY_MILLIS);
    }

    @Test
    public void shouldFireEventOnce_whenHeightIsNotChanging() {
        // given
        int manyTimesAfterEventFired = TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT + 7;

        // when
        runResizeUpdateNTimes(manyTimesAfterEventFired);

        // then
        verify(eventsBus, times(1)).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
    }

    @Test
    public void shouldFireEventTwice_whenHeightIsChangingAgain() {
        // given
        int manyTimesAfterEventFired = TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT + 7;
        runResizeUpdateNTimes(manyTimesAfterEventFired);

        int higherHeight = INIT_HEIGHT * 2;
        when(pageView.getCurrentPageHeight()).thenReturn(higherHeight);

        // when
        runResizeUpdateNTimes(TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT);

        // then
        verify(eventsBus, times(2)).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
    }

    @Test
    public void shouldEnterIdleMode_afterCountdown_withTheSameHeight() {
        // given
        int timesToEnterIdleMode = TIMES_TO_FIRE_EVENT_WITH_SAME_HEIGHT + ResizeContinuousUpdater.REPEAT_COUNT + 1;

        // when
        int result = runResizeUpdateNTimes(timesToEnterIdleMode);

        // then
        verify(eventsBus, times(1)).fireAsyncEvent(isA(PlayerEvent.class), eq(currentPageScope));
        assertThat(result).isEqualTo(ResizeContinuousUpdater.IDLE_DELAY_MILLIS);
    }

    private int runResizeUpdateNTimes(int times) {
        for (int i = 0; i < times - 1; i++) {
            testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
        }
        return testObj.runContinuousResizeUpdateAndReturnRescheduleTime();
    }
}