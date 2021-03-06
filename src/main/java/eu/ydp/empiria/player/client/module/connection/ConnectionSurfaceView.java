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

package eu.ydp.empiria.player.client.module.connection;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.util.position.Point;

import java.util.Map;

public interface ConnectionSurfaceView extends IsWidget {

    void clear();

    void drawLine(Point relativeStart, Point relativeEnd);

    void applyStyles(Map<String, String> styles);

    void removeFromParent();

    boolean isPointOnPath(Point relativePoint);

    void setOffsetLeft(int offsetLeft);

    int getOffsetLeft();

    void setOffsetTop(int offsetTop);
}
