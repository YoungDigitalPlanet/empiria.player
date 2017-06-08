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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieViewPositionFinder {

    public static enum Axis {
        HORIZONTAL, VERTICAL
    }

    ;

    private final WidgetSizeHelper sizeHelper;
    final CenterPositionFinder positionFinder;

    @Inject
    public StickieViewPositionFinder(CenterPositionFinder positionFinder, WidgetSizeHelper sizeHelper) {
        this.positionFinder = positionFinder;
        this.sizeHelper = sizeHelper;
    }

    /**
     * Finds position for a stickie relatively to item container, taking all circumstances into consideration.
     */
    public Point<Integer> calculateCenterPosition(ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        int x = positionFinder.getCenterPosition(stickieDimensions.getWidth(), parentDimensions.getAbsoluteLeft(), Axis.HORIZONTAL);
        int y = positionFinder.getCenterPosition(stickieDimensions.getHeight(), parentDimensions.getAbsoluteTop(), Axis.VERTICAL);

        return new Point<Integer>(x, y);
    }

    public Point<Integer> refinePosition(Point<Integer> position, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        int refinedX = refinePositionHorizontal(position.getX(), stickieDimensions, parentDimensions);
        int refinedY = refinePositionVertical(position.getY(), stickieDimensions, parentDimensions);
        return new Point<Integer>(refinedX, refinedY);
    }

    private int refinePositionHorizontal(int left, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        int size = stickieDimensions.getWidth();
        int min = 0;
        int max = parentDimensions.getWidth();

        return refinePosition(left, size, min, max);
    }

    private int refinePositionVertical(int top, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        int size = stickieDimensions.getHeight();
        ContainerDimensions playerContainerDimensions = sizeHelper.getPlayerContainerDimensions();
        int min = playerContainerDimensions.getAbsoluteTop() - parentDimensions.getAbsoluteTop();
        int max = playerContainerDimensions.getAbsoluteTop() + playerContainerDimensions.getHeight() - parentDimensions.getAbsoluteTop();

        return refinePosition(top, size, min, max);
    }

    int refinePosition(int coord, int size, int min, int max) {

        int newCoord;

        if (coord < min) {
            newCoord = min;
        } else if (coord > max - size) {
            newCoord = max - size;
        } else {
            newCoord = coord;
        }

        return newCoord;
    }

}
