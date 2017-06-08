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

package eu.ydp.empiria.player.client.module.colorfill.presenter.compare;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

import java.util.Map;

public class AreasMapsComparator {

    public AreasMapComparationResult findDifference(Map<Area, ColorModel> previous, Map<Area, ColorModel> current) {
        MapDifference<Area, ColorModel> differences = Maps.difference(previous, current);

        checkDifferencesCount(differences);

        if (isAdded(differences)) {
            return getAdded(differences);
        }
        if (isRemoved(differences)) {
            return getRemoved(differences);
        }
        if (isChanged(differences)) {
            return getChanged(differences);
        }

        return AreasMapComparationResult.ofSame();
    }

    private void checkDifferencesCount(MapDifference<Area, ColorModel> differences) {
        int differencesCount = differences.entriesDiffering().size() + differences.entriesOnlyOnLeft().size() + differences.entriesOnlyOnRight().size();
        if (differencesCount > 1) {
            throw new IllegalArgumentException("There must be at most one difference between previous and currrent map.");
        }
    }

    private boolean isChanged(MapDifference<Area, ColorModel> differences) {
        return !differences.entriesDiffering().isEmpty();
    }

    private AreasMapComparationResult getChanged(MapDifference<Area, ColorModel> differences) {
        Area area = differences.entriesDiffering().keySet().iterator().next();
        return AreasMapComparationResult.ofAddedOrChanged(area);
    }

    private boolean isRemoved(MapDifference<Area, ColorModel> differences) {
        return !differences.entriesOnlyOnLeft().isEmpty();
    }

    private AreasMapComparationResult getRemoved(MapDifference<Area, ColorModel> differences) {
        Area area = differences.entriesOnlyOnLeft().keySet().iterator().next();
        return AreasMapComparationResult.ofRemoved(area);
    }

    private boolean isAdded(MapDifference<Area, ColorModel> differences) {
        return !differences.entriesOnlyOnRight().isEmpty();
    }

    private AreasMapComparationResult getAdded(MapDifference<Area, ColorModel> differences) {
        Area area = differences.entriesOnlyOnRight().keySet().iterator().next();
        return AreasMapComparationResult.ofAddedOrChanged(area);
    }
}
