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

package eu.ydp.empiria.player.client.module.img.events.handlers;

import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TouchHandlerOnImageProviderTest {

    @InjectMocks
    private TouchHandlerOnImageProvider testObj;
    @Mock
    private PointerHandlersOnImageInitializer pointerHandlersOnImageInitializer;
    @Mock
    private TouchHandlersOnImageInitializer touchHandlersInitializer;
    @Mock
    private UserAgentUtil userAgentUtil;

    @Test
    public void shouldReturnPointerInitializer_ifIE() {
        // given
        when(userAgentUtil.isIE()).thenReturn(true);

        // when
        ITouchHandlerOnImageInitializer result = testObj.get();

        // then
        assertThat(result, instanceOf(PointerHandlersOnImageInitializer.class));
    }

    @Test
    public void shouldReturnTouchInitializer_ifNotIE() {
        // given
        when(userAgentUtil.isIE()).thenReturn(false);

        // when
        ITouchHandlerOnImageInitializer result = testObj.get();

        // then
        assertThat(result, instanceOf(TouchHandlersOnImageInitializer.class));
    }
}
