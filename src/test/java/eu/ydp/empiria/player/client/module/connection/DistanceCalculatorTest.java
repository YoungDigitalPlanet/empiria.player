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

import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DistanceCalculatorTest {

    @InjectMocks
    private DistanceCalculator testObj;

    @Test
    public void testCalculateDistanceBetween_shouldReturn0() {
        LineSegment lineSegment = new LineSegment(new Point(0, 0), new Point(4, 4));
        Point point = new Point(1, 1);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(0d, result, 0);
    }

    @Test
    public void testCalculateDistanceBetween_shouldReturn2() {

        LineSegment lineSegment = new LineSegment(new Point(0, 0), new Point(4, 0));
        Point point = new Point(1, 2);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(2d, result, 0);
    }

    @Test
    public void testCalculateDistanceBetween_shouldReturn1() {

        LineSegment lineSegment = new LineSegment(new Point(0, 5), new Point(0, 1));
        Point point = new Point(1, 8);

        double result = testObj.calculateDistanceBetween(lineSegment, point);

        assertEquals(1d, result, 0);
    }

}
