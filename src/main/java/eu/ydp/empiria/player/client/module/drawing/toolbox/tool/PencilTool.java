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

public class PencilTool implements Tool {

    public static final int LINE_WIDTH = 4;
    private final ColorModel color;
    private final DrawCanvas canvas;

    public PencilTool(ColorModel colorModel, DrawCanvas canvas) {
        this.color = colorModel;
        this.canvas = canvas;
    }

    @Override
    public void start(Point point) {
        canvas.drawPoint(point, color);
    }

    @Override
    public void move(Point start, Point end) {
        canvas.drawLine(start, end, color);
    }

    @Override
    public void setUp() {
        canvas.setLineWidth(LINE_WIDTH);
    }

}
