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

package eu.ydp.empiria.player.client.module.test.reset;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEvent;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TestResetButtonPresenterTest {

    private TestResetButtonPresenter testObj;

    @Mock
    private TestResetButtonView testResetButtonView;
    @Mock
    private FlowManager flowManager;
    @Mock
    private PlayerWorkModeService playerWorkModeService;
    @Mock
    private EventsBus eventBus;

    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    @Before
    public void setUp() {
        testObj = new TestResetButtonPresenter(testResetButtonView, flowManager, playerWorkModeService, eventBus);
    }

    @Test
    public void shouldGetView() {
        // given
        Widget widget = mock(Widget.class);
        when(testResetButtonView.asWidget()).thenReturn(widget);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(widget);
    }

    @Test
    public void shouldLock() {
        // when
        testObj.lock();

        // then
        testResetButtonView.lock();
    }

    @Test
    public void shouldUnlock() {
        // when
        testObj.unlock();

        // then
        testResetButtonView.unlock();
    }

    @Test
    public void shouldNavigateToFirstItem_ifNoLockWasCalled() {
        // given
        NativeEvent event = mock(NativeEvent.class);

        verify(testResetButtonView).addHandler(commandCaptor.capture());
        Command command = commandCaptor.getValue();

        // when
        command.execute(event);

        // then
        verifyActionsOnResetClick();
    }

    @Test
    public void shouldNavigateToFirstItem_ifLockUnlockWasCalled() {
        // given
        NativeEvent event = mock(NativeEvent.class);
        testObj.lock();

        verify(testResetButtonView).addHandler(commandCaptor.capture());
        Command command = commandCaptor.getValue();

        testObj.unlock();

        // when
        command.execute(event);

        // then
        verifyActionsOnResetClick();
    }

    @Test
    public void shouldNotNavigateToFirstItem() {
        // given
        NativeEvent event = mock(NativeEvent.class);
        testObj.lock();

        verify(testResetButtonView).addHandler(commandCaptor.capture());
        Command command = commandCaptor.getValue();

        // when
        command.execute(event);

        // then
        verifyZeroInteractions(flowManager);
        verifyZeroInteractions(eventBus);
    }

    @Test
    public void shouldEnablePreviewMode() {
        // when
        testObj.enablePreviewMode();

        // then
        verify(testResetButtonView).lock();
        verify(testResetButtonView).enablePreviewMode();
    }

    private void verifyActionsOnResetClick() {
        verify(flowManager).invokeFlowRequest(isA(FlowRequest.NavigateFirstItem.class));
        verify(playerWorkModeService).tryToUpdateWorkMode(PlayerWorkMode.TEST);
        verify(eventBus).fireEvent(isA(LessonResetEvent.class));
    }
}
