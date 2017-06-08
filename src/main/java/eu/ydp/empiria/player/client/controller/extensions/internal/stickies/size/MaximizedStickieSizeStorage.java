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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

public class MaximizedStickieSizeStorage {

    private final Map<Integer, StickieSize> colorToStickieSizeMap = new HashMap<Integer, StickieSize>();

    public Optional<StickieSize> getSizeOfMaximizedStickie(int colorIndex) {
        StickieSize stickieSize = colorToStickieSizeMap.get(colorIndex);
        return Optional.fromNullable(stickieSize);
    }

    public void updateIfBiggerThanExisting(int colorIndex, StickieSize size) {
        StickieSize existingStickieSize = colorToStickieSizeMap.get(colorIndex);

        StickieSize mergedSize;
        if (existingStickieSize == null) {
            mergedSize = size;
        } else {
            mergedSize = mergeSizes(size, existingStickieSize);
        }

        colorToStickieSizeMap.put(colorIndex, mergedSize);
    }

    private StickieSize mergeSizes(StickieSize size, StickieSize existingStickieSize) {
        int maxHeight = Math.max(size.getHeight(), existingStickieSize.getHeight());
        int maxWidth = Math.max(size.getWidth(), existingStickieSize.getWidth());
        StickieSize mergedSize = new StickieSize(maxWidth, maxHeight);
        return mergedSize;
    }
}
