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

import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class AreasMapComparationResult {

    public static AreasMapComparationResult ofAddedOrChanged(Area area) {
        AreasMapComparationResult result = new AreasMapComparationResult();
        result.area = area;
        result.addedOrChanged = true;
        return result;
    }

    public static AreasMapComparationResult ofRemoved(Area area) {
        AreasMapComparationResult result = new AreasMapComparationResult();
        result.area = area;
        result.addedOrChanged = false;
        return result;
    }

    public static AreasMapComparationResult ofSame() {
        return new AreasMapComparationResult();
    }

    private Area area;
    private boolean addedOrChanged;

    private AreasMapComparationResult() {
    }

    public Area getArea() {
        return area;
    }

    public boolean isDifference() {
        return area != null;
    }

    public boolean isAddedOrChanged() {
        return addedOrChanged;
    }
}
