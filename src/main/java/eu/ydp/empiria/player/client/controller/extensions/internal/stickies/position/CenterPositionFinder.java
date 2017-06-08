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

import com.google.common.collect.Range;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder.Axis;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class CenterPositionFinder {

    private final RangeCreator rangeCreator;
    private final ViewportHelper viewportHelper;
    private final WidgetSizeHelper sizeHelper;

    @Inject
    public CenterPositionFinder(RangeCreator rangeCreator, ViewportHelper viewportHelper, WidgetSizeHelper sizeHelper) {
        this.rangeCreator = rangeCreator;
        this.viewportHelper = viewportHelper;
        this.sizeHelper = sizeHelper;
    }

    public Integer getCenterPosition(int size, int parentAbsoluteCoord, Axis coord) {
        // viewport
        Rectangle viewport = viewportHelper.getViewport();
        Range<Integer> viewportRange = rangeCreator.getRangeForAxis(viewport, coord);

        // container
        Rectangle playerRect = sizeHelper.getPlayerContainerRectangle();
        Range<Integer> containerRange = rangeCreator.getRangeForAxis(playerRect, coord);

        // compute
        int value = findCenterPosition(size, containerRange, viewportRange);

        return value - parentAbsoluteCoord;
    }

    private int findCenterPosition(Integer itemSize, Range<Integer> container, Range<Integer> viewport) {
        int result;
        if (itemSize > container.upperEndpoint() - container.lowerEndpoint()) {
            result = container.lowerEndpoint();
        } else {
            int min = Math.max(container.lowerEndpoint(), viewport.lowerEndpoint());
            int max = Math.min(container.upperEndpoint(), viewport.upperEndpoint());
            if (min + itemSize > max) {
                if (isViewportAboveContainer(container, viewport)) {
                    result = container.upperEndpoint() - itemSize;
                } else {
                    result = container.lowerEndpoint();
                }
            } else {
                result = min + (max - min - itemSize) / 2;
            }
        }
        return result;
    }

    private boolean isViewportAboveContainer(Range<Integer> container, Range<Integer> viewport) {
        return container.upperEndpoint() < viewport.lowerEndpoint();
    }
}
