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
