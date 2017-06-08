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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import eu.ydp.gwtutil.client.geom.Point;

public interface IStickieProperties {

    int getColorIndex();

    void setColorIndex(int colorIndex);

    String getStickieTitle();

    void setStickieTitle(String stickieTitle);

    String getStickieContent();

    void setStickieContent(String bookmarkContent);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    Point<Integer> getPosition();

    void setPosition(Point<Integer> newPosition);

    boolean isMinimized();

    void setMinimized(boolean minimized);

    void updateTimestamp();

}
