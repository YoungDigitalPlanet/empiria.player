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

package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfaceRectangleFinder {

    private final SurfacesOffsetsCalculator surfacesOffsetsUtils;

    @Inject
    public SurfaceRectangleFinder(SurfacesOffsetsCalculator surfacesOffsetsUtils) {
        this.surfacesOffsetsUtils = surfacesOffsetsUtils;
    }

    public int findOffsetLeft(ConnectionItems items) {
        return surfacesOffsetsUtils.findMinOffsetLeft(items);
    }

    public int findOffsetTop(ConnectionItems items) {
        return surfacesOffsetsUtils.findMinOffsetTop(items);
    }

    public int findWidth(ConnectionItems items) {
        final int minLeft = surfacesOffsetsUtils.findMinOffsetLeft(items);
        final int maxRight = surfacesOffsetsUtils.findMaxOffsetRight(items);

        return maxRight - minLeft;
    }

    public int findHeight(ConnectionItems items) {
        final int minTop = surfacesOffsetsUtils.findMinOffsetTop(items);
        final int maxBottom = surfacesOffsetsUtils.findMaxOffsetBottom(items);

        return maxBottom - minTop;
    }
}
