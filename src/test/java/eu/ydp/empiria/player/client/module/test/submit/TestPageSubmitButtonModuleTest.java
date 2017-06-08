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

package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TestPageSubmitButtonModuleTest {

    @InjectMocks
    private TestPageSubmitButtonModule testObj;

    @Mock
    private TestPageSubmitButtonPresenter presenter;

    @Test
    public void shouldReturnWidget() {
        // given
        Widget widget = mock(Widget.class);
        when(presenter.getView()).thenReturn(widget);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result, is(widget));
    }

    @Test
    public void shouldLockPresenter() {
        // given

        // when
        testObj.lock(true);

        // then
        verify(presenter).lock();
    }

    @Test
    public void shouldUnlockPresenter() {
        // given

        // when
        testObj.lock(false);

        // then
        verify(presenter).unlock();
    }

    @Test
    public void shouldEnableTestSubmittedMode() {
        // given

        // when
        testObj.enableTestSubmittedMode();
        // then
        verify(presenter).enableTestSubmittedMode();
    }

    @Test
    public void shouldDisableTestSubmittedMode() {
        // given

        // when
        testObj.disableTestSubmittedMode();

        // then
        verify(presenter).disableTestSubmittedMode();
    }

    @Test
    public void shouldEnablePreviewMode() {
        // given

        // when
        testObj.enablePreviewMode();
        // then
        verify(presenter).enablePreviewMode();
    }
}
