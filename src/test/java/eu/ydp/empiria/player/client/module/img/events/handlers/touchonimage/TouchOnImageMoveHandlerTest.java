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

package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TouchOnImageMoveHandlerTest {

    @InjectMocks
    private TouchOnImageMoveHandler testObj;
    @Mock
    private CanvasMoveEvents canvasMoveEvents;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;

    @Test
    public void shouldRunOnMoveMove_whenOneTouch() {
        // given
        when(touchOnImageEvent.getLength()).thenReturn(1);

        Point point = new Point(0, 0);
        when(touchOnImageEvent.getPoint(0)).thenReturn(point);

        // when
        testObj.onMove(touchOnImageEvent);

        // then
        verify(canvasMoveEvents).onMoveMove(point);
    }

    @Test
    public void shouldRunOnMoveScale_whenTwoTouches() {
        // given
        when(touchOnImageEvent.getLength()).thenReturn(2);

        Point firstFinger = new Point(0, 0);
        when(touchOnImageEvent.getPoint(0)).thenReturn(firstFinger);

        Point secondFinger = new Point(0, 0);
        when(touchOnImageEvent.getPoint(1)).thenReturn(secondFinger);

        // when
        testObj.onMove(touchOnImageEvent);

        // then
        verify(canvasMoveEvents).onMoveScale(firstFinger, secondFinger);
    }
}
