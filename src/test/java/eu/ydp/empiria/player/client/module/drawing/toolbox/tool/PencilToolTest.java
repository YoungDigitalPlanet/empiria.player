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
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
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
public class PencilToolTest {

    @InjectMocks
    private PencilTool pencilTool;

    @Mock
    private ColorModel colorModel;
    @Mock
    private DrawCanvas canvas;

    @After
    public void verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(canvas, colorModel);
    }

    @Test
    public void shouldDrawPoint() throws Exception {
        // given
        Point point = new Point(1, 2);

        // when
        pencilTool.start(point);

        // then
        verify(canvas).drawPoint(point, colorModel);
    }

    @Test
    public void shouldDrawLine() throws Exception {
        // given
        Point startPoint = new Point(1, 1);
        Point endPoint = new Point(2, 2);

        // when
        pencilTool.move(startPoint, endPoint);

        // then
        verify(canvas).drawLine(startPoint, endPoint, colorModel);
    }

    @Test
    public void shouldSetLineWidthOnSetUp() throws Exception {
        // when
        pencilTool.setUp();

        // then
        verify(canvas).setLineWidth(PencilTool.LINE_WIDTH);
    }
}
