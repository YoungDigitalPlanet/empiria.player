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

package eu.ydp.empiria.player.client.module.selection.controller;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoAnswerPriorityComparatorJUnitTest {

    private NoAnswerPriorityComparator comparator = new NoAnswerPriorityComparator();

    @Test
    public void testCompare() {

        SelectionAnswerDto o1 = new SelectionAnswerDto("id1");
        SelectionAnswerDto o2 = new SelectionAnswerDto("id2");

        int compare = comparator.compare(o1, o2);
        assertEquals(0, compare);

        compare = comparator.compare(o2, o1);
        assertEquals(0, compare);
    }

}
