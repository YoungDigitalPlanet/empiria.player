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

package eu.ydp.empiria.player.client.controller.communication;

public class Result {

    private float min;
    private float max;
    private float score;

    public Result() {
        min = 0;
        max = 1;
        score = 0;
    }

    public Result(float outcomeScore, float lowerBound, float upperBound) {
        this();
        min = lowerBound;
        max = upperBound;
        score = outcomeScore;

    }

    public Result(Float outcomeScore, Float lowerBound, Float upperBound) {
        this();
        if (lowerBound != null)
            min = lowerBound;

        if (upperBound != null)
            max = upperBound;

        if (outcomeScore != null)
            score = outcomeScore;

    }

    public float getScore() {
        return score;
    }

    public float getMinPoints() {
        return min;
    }

    public float getMaxPoints() {
        return max;
    }

    public void merge(Result result) {
        score += result.getScore();
        min += result.getMinPoints();
        max += result.getMaxPoints();
    }
}
