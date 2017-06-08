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

package eu.ydp.empiria.player.client.module.item;

import com.google.common.collect.Range;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.collections.SimpleRangeMap;

public class ProgressToStringRangeMap {

    private static final int PROGRESS_MIN = 0;
    private static final int PROGRESS_MAX = 100;

    private final SimpleRangeMap<Integer, String> feedbacks = SimpleRangeMap.<Integer, String>create();

    public ProgressToStringRangeMap() {
        fillMappingOutOfRange();
    }

    private void fillMappingOutOfRange() {
        feedbacks.put(Range.closedOpen(Integer.MIN_VALUE, PROGRESS_MIN), "");
        feedbacks.put(Range.openClosed(PROGRESS_MAX, Integer.MAX_VALUE), "");
    }

    public void addValueForRange(Range<Integer> range, String value) {
        feedbacks.put(range, value);
    }

    public String getValueForProgress(int progress) {
        String feedback;
        feedback = feedbacks.get(progress);
        if (feedback != null) {
            return feedback;
        }
        return StringUtils.EMPTY_STRING;
    }
}
