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

public class StickiePropertiesTestable implements IStickieProperties {

    private int colorIndex = 0;
    private String stickieTitle = "";
    private String stickieContent = "";
    private Point<Integer> position = new Point<Integer>(0, 0);
    private boolean minimized = false;

    @Override
    public int getColorIndex() {
        return colorIndex;
    }

    @Override
    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    @Override
    public String getStickieTitle() {
        return stickieTitle;
    }

    @Override
    public void setStickieTitle(String stickieTitle) {
        this.stickieTitle = stickieTitle;
    }

    @Override
    public String getStickieContent() {
        return stickieContent;
    }

    @Override
    public void setStickieContent(String bookmarkContent) {
        this.stickieContent = bookmarkContent;
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public void setX(int x) {
        position = new Point<Integer>(x, position.getY());
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public void setY(int y) {
        position = new Point<Integer>(position.getX(), y);
    }

    @Override
    public Point<Integer> getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point<Integer> newPosition) {
        this.position = newPosition;
    }

    @Override
    public boolean isMinimized() {
        return minimized;
    }

    @Override
    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    @Override
    public void updateTimestamp() {

    }

}
