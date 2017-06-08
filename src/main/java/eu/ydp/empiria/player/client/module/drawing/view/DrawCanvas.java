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

package eu.ydp.empiria.player.client.module.drawing.view;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

public interface DrawCanvas {
    void drawPoint(Point point, ColorModel color);

    void drawLine(Point startPoint, Point endPoint, ColorModel color);

    void erasePoint(Point point);

    void eraseLine(Point startPoint, Point endPoint);

    void clear();

    void setLineWidth(int lineWidth);
}
