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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieDragController {

    private final IStickiePresenter stickiePresenter;
    private final IStickieProperties stickieProperties;
    private boolean dragging = false;
    private Point<Integer> dragInitMousePosition;
    private Point<Integer> dragInitViewPosition;

    @Inject
    public StickieDragController(@Assisted IStickiePresenter stickiePresenter, @Assisted IStickieProperties stickieProperties) {
        this.stickiePresenter = stickiePresenter;
        this.stickieProperties = stickieProperties;
    }

    public void dragStart(Point<Integer> point) {
        dragInitMousePosition = point;
        dragInitViewPosition = stickieProperties.getPosition();
        dragging = true;
    }

    public void dragMove(Point<Integer> point) {
        if (dragging) {
            int xMoveDistance = point.getX() - dragInitMousePosition.getX();
            int yMoveDistance = point.getY() - dragInitMousePosition.getY();

            int newLeft = dragInitViewPosition.getX() + xMoveDistance;
            int newTop = dragInitViewPosition.getY() + yMoveDistance;

            Point<Integer> newPosition = new Point<Integer>(newLeft, newTop);
            stickiePresenter.moveStickieToPosition(newPosition);
        }
    }

    public void dragEnd() {
        dragging = false;
    }

}
