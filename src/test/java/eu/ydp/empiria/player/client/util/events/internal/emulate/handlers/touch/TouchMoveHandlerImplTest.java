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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class TouchMoveHandlerImplTest {

    private TouchMoveHandlerImpl testObj;

    @Mock
    private TouchOnMoveHandler touchOnMoveHandler;

    @Mock
    private TouchMoveEvent touchMoveEvent;

    @Mock
    private NativeEvent nativeEvent;

    @Before
    public void setUp() {
        testObj = new TouchMoveHandlerImpl(touchOnMoveHandler);
    }

    @Test
    public void shouldCallOnMove() {
        // given
        when(touchMoveEvent.getNativeEvent()).thenReturn(nativeEvent);

        // when
        testObj.onTouchMove(touchMoveEvent);

        // then
        verify(touchOnMoveHandler).onMove(nativeEvent);
    }
}
