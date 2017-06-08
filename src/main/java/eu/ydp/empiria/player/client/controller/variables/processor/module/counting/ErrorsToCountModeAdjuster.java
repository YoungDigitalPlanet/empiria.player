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

package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;

public class ErrorsToCountModeAdjuster {

    public int adjustValueToCountMode(int value, CountMode countMode) {
        int adjustedValue;
        if (countMode == CountMode.CORRECT_ANSWERS) {
            adjustedValue = value;
        } else {
            adjustedValue = getOneIfBiggerThenZero(value);
        }

        return adjustedValue;
    }

    private int getOneIfBiggerThenZero(int value) {
        if (value > 0) {
            return 1;
        } else {
            return value;
        }
    }
}
