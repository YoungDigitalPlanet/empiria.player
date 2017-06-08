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

package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.util.position.Point;

@Singleton
public class RectangleChecker {

    private final static int ACCEPTABLE_ERROR_LEVEL = 15;

    public boolean isPointInLineSegmentRectangle(LineSegment lineSegment, Point point) {

        Point pointStart = lineSegment.getPointStart();
        Point pointEnd = lineSegment.getPointEnd();
        if (isValueBetweenTwoNumbers(pointStart.getX(), pointEnd.getX(), point.getX())
                && isValueBetweenTwoNumbers(pointStart.getY(), pointEnd.getY(), point.getY())) {
            return true;
        }

        return false;

    }

    private boolean isValueBetweenTwoNumbers(int firstNumber, int secondNumber, int testedValue) {
        return ((firstNumber - ACCEPTABLE_ERROR_LEVEL <= testedValue && testedValue <= secondNumber + ACCEPTABLE_ERROR_LEVEL) || (secondNumber
                - ACCEPTABLE_ERROR_LEVEL <= testedValue && testedValue <= firstNumber + ACCEPTABLE_ERROR_LEVEL));

    }
}
