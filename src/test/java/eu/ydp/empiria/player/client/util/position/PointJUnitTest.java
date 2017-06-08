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

package eu.ydp.empiria.player.client.util.position;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("PMD")
public class PointJUnitTest {

    private Point point;

    @Test
    public void testPoint() {
        point = new Point(20, 30);
        assertEquals(20, point.getX());
        assertEquals(30, point.getY());

        point = new Point(-1, -70);
        assertEquals(-1, point.getX());
        assertEquals(-70, point.getY());

    }

}
