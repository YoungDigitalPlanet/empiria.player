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
public class DistanceCalculator {

    public double calculateDistanceBetween(LineSegment lineSegment, Point point) {
        // Adjust vectors relative to x1,y1
        // x2,y2 becomes relative vector from x1,y1 to end of segment
        int x2 = lineSegment.getPointEnd().getX() - lineSegment.getPointStart().getX();
        int y2 = lineSegment.getPointEnd().getY() - lineSegment.getPointStart().getY();
        // px,py becomes relative vector from x1,y1 to test point
        int px = point.getX() - lineSegment.getPointStart().getX();
        int py = point.getY() - lineSegment.getPointStart().getY();
        double dotprod = px * x2 + py * y2;
        // dotprod is the length of the px,py vector
        // projected on the x1,y1=>x2,y2 vector times the
        // length of the x1,y1=>x2,y2 vector
        double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
        // Distance to line is now the length of the relative point
        // vector minus the length of its projection onto the line
        double lenSq = px * px + py * py - projlenSq;
        if (lenSq < 0) {
            lenSq = 0;
        }

        return Math.sqrt(lenSq);
    }
}
