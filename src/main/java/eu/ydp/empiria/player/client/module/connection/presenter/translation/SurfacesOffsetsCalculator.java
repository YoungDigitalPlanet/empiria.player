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

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

public class SurfacesOffsetsCalculator {

    @Inject
    private SurfacesOffsetsExtractor offsetsExtractor;

    public int findMinOffsetLeft(ConnectionItems items) {
        Collection<Integer> leftOffsets = offsetsExtractor.extractLeftOffsets(items);

        return Collections.min(leftOffsets);
    }

    public int findMaxOffsetLeft(ConnectionItems items) {
        Collection<Integer> leftOffsets = offsetsExtractor.extractLeftOffsets(items);

        return Collections.max(leftOffsets);
    }

    public int findMinOffsetTop(ConnectionItems items) {
        Collection<Integer> topOffsets = offsetsExtractor.extractTopOffsets(items);

        return Collections.min(topOffsets);
    }

    public int findMaxOffsetTop(ConnectionItems items) {
        Collection<Integer> topOffsets = offsetsExtractor.extractTopOffsets(items);

        return Collections.max(topOffsets);
    }

    public int findMaxOffsetRight(ConnectionItems items) {
        Collection<Integer> rightOffsets = offsetsExtractor.extractRightOffsets(items);

        return Collections.max(rightOffsets);
    }

    public int findMaxOffsetBottom(ConnectionItems items) {
        Collection<Integer> bottomOffsets = offsetsExtractor.extractBottomOffsets(items);

        return Collections.max(bottomOffsets);
    }
}
