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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TestResetButtonModuleTest {

    private TestResetButtonModule testObj;

    @Mock
    private TestResetButtonPresenter presenter;

    @Before
    public void setUp() {
        testObj = new TestResetButtonModule(presenter);
    }

    @Test
    public void shouldGetView() {
        // given
        Widget view = mock(Widget.class);

        when(presenter.getView()).thenReturn(view);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(view);
    }

    @Test
    public void shouldLock() {
        // when
        testObj.lock(true);

        // then
        verify(presenter).lock();
    }

    @Test
    public void shouldUnlock() {
        // when
        testObj.lock(false);

        // then
        verify(presenter).unlock();
    }

    @Test
    public void shouldEnablePreviewMode() {
        // when
        testObj.enablePreviewMode();

        // then
        verify(presenter).enablePreviewMode();
    }
}
