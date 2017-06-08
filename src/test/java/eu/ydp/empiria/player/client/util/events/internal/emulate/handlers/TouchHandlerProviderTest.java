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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class TouchHandlerProviderTest {

    @InjectMocks
    private TouchHandlerProvider testObj;

    @Mock
    private PointerHandlersInitializer pointerHandlersInitializer;

    @Mock
    private TouchHandlersInitializer touchHandlersInitializer;

    @Mock
    private UserAgentUtil userAgentUtil;

    @Test
    public void shouldReturnPointerInitializer_ifIE() {
        // given
        when(userAgentUtil.isIE()).thenReturn(true);

        // when
        ITouchHandlerInitializer result = testObj.getTouchHandlersInitializer();

        // then
        assertThat(result, instanceOf(PointerHandlersInitializer.class));
    }

    @Test
    public void shouldReturnTouchInitializer_ifNotIE() {
        // given
        when(userAgentUtil.isIE()).thenReturn(false);

        // when
        ITouchHandlerInitializer result = testObj.getTouchHandlersInitializer();

        // then
        assertThat(result, instanceOf(TouchHandlersInitializer.class));
    }
}
