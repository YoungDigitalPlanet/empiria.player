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

package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EraserToolTest {

    @InjectMocks
    private EraserTool eraserTool;

    @Mock
    private DrawCanvas canvas;

    @After
    public void verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(canvas);
    }

    @Test
    public void shouldErasePoint() throws Exception {
        // given
        Point point = new Point(1, 2);

        // when
        eraserTool.start(point);

        // then
        verify(canvas).erasePoint(point);
    }

    @Test
    public void shouldEraseLine() throws Exception {
        // given
        Point startPoint = new Point(1, 1);
        Point endPoint = new Point(2, 2);

        // when
        eraserTool.move(startPoint, endPoint);

        // then
        verify(canvas).eraseLine(startPoint, endPoint);
    }

    @Test
    public void shouldSetLineWidthOnSetUp() throws Exception {
        // when
        eraserTool.setUp();

        // then
        verify(canvas).setLineWidth(EraserTool.LINE_WIDTH);
    }
}
