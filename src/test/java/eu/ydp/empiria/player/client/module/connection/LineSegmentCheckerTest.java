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

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LineSegmentCheckerTest {

    private final MocksCollector mocksCollector = new MocksCollector();
    @InjectMocks
    private LineSegmentChecker testObj;
    @Mock
    private DistanceCalculator distanceCalculator;
    @Mock
    private RectangleChecker rectangleChecker;
    @Mock
    private LineSegment lineSegment;
    @Mock
    private Point point;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(mocksCollector.getMocks());
    }

    @Test
    public void isLineSegmentNearPointTest_shouldReturnFalse_PointIsNotInLineSegmentRectangle() {
        // given
        when(rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point)).thenReturn(false);

        // when
        boolean result = testObj.isLineSegmentNearPoint(lineSegment, point);

        // then
        assertFalse(result);
        verify(rectangleChecker).isPointInLineSegmentRectangle(lineSegment, point);
    }

    @Test
    public void isLineSegmentNearPointTest_shouldReturnFalse_distanceIsTooBig() {
        // given
        when(rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point)).thenReturn(true);
        when(distanceCalculator.calculateDistanceBetween(lineSegment, point)).thenReturn(15.0);

        // when
        boolean result = testObj.isLineSegmentNearPoint(lineSegment, point);

        // then
        assertFalse(result);
        verify(rectangleChecker).isPointInLineSegmentRectangle(lineSegment, point);
        verify(distanceCalculator).calculateDistanceBetween(lineSegment, point);
    }

    @Test
    public void isLineSegmentNearPointTest_shouldReturnTrue() {
        // given
        when(rectangleChecker.isPointInLineSegmentRectangle(lineSegment, point)).thenReturn(true);
        when(distanceCalculator.calculateDistanceBetween(lineSegment, point)).thenReturn(14.0);

        // when
        boolean result = testObj.isLineSegmentNearPoint(lineSegment, point);

        // then
        assertTrue(result);
        verify(rectangleChecker).isPointInLineSegmentRectangle(lineSegment, point);
        verify(distanceCalculator).calculateDistanceBetween(lineSegment, point);
    }
}
