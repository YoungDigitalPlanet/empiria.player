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

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.MaximizedStickieSizeStorage;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.StickieSize;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieMinimizeMaximizeController {

    private final IStickieProperties stickieProperties;
    private final MaximizedStickieSizeStorage maximizedStickieSizeStorage;
    private final StickieViewPositionFinder positionFinder;

    private Point<Integer> previousMinimizedPosition;

    @Inject
    public StickieMinimizeMaximizeController(@Assisted IStickieProperties stickieProperties, MaximizedStickieSizeStorage maximizedStickieSizeStorage,
                                             StickieViewPositionFinder positionFinder) {
        this.stickieProperties = stickieProperties;
        this.maximizedStickieSizeStorage = maximizedStickieSizeStorage;
        this.positionFinder = positionFinder;
    }

    public void resetCachedMinimizedPosition() {
        previousMinimizedPosition = null;
    }

    public Point<Integer> positionMinimizedStickie(ContainerDimensions stickieDimensions) {
        updateMaximizedStickieSize(stickieDimensions);
        return findCorrectMinimizedStickiePosition();
    }

    public Point<Integer> positionMaximizedStickie(ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        updateMaximizedStickieSize(stickieDimensions);
        if (previousMinimizedPosition == null) {
            previousMinimizedPosition = stickieProperties.getPosition();
        }

        return findCorrectMaximizedPosition(stickieDimensions, parentDimensions);
    }

    private Point<Integer> findCorrectMinimizedStickiePosition() {
        Point<Integer> newPosition;
        if (previousMinimizedPosition != null) {
            newPosition = previousMinimizedPosition;
        } else {
            newPosition = stickieProperties.getPosition();
        }

        return newPosition;
    }

    private void updateMaximizedStickieSize(ContainerDimensions stickieDimensions) {
        StickieSize stickieSize = new StickieSize(stickieDimensions.getWidth(), stickieDimensions.getHeight());
        maximizedStickieSizeStorage.updateIfBiggerThanExisting(stickieProperties.getColorIndex(), stickieSize);
    }

    private Point<Integer> findCorrectMaximizedPosition(ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
        Point<Integer> newPosition;
        Optional<StickieSize> optionalStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex());
        if (optionalStickieSize.isPresent()) {
            StickieSize maximizedStickieSize = optionalStickieSize.get();
            ContainerDimensions maximizedStickieDimensions = getMaximizedStickieDimensions(stickieDimensions, maximizedStickieSize);
            newPosition = positionFinder.refinePosition(stickieProperties.getPosition(), maximizedStickieDimensions, parentDimensions);
        } else {
            newPosition = positionFinder.refinePosition(stickieProperties.getPosition(), stickieDimensions, parentDimensions);
        }

        return newPosition;
    }

    private ContainerDimensions getMaximizedStickieDimensions(ContainerDimensions stickieDimensions, StickieSize maximizedStickieSize) {
        ContainerDimensions maximizedStickieDimensions = ContainerDimensions.Builder.fromContainerDimensions(stickieDimensions)
                .width(maximizedStickieSize.getWidth()).height(maximizedStickieSize.getHeight()).build();
        return maximizedStickieDimensions;
    }
}
