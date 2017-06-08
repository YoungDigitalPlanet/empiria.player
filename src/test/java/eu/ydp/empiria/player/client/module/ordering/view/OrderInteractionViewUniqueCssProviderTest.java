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

package eu.ydp.empiria.player.client.module.ordering.view;

import eu.ydp.empiria.player.client.module.ordering.OrderingStyleNameConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionViewUniqueCssProviderTest {

    @InjectMocks
    private OrderInteractionViewUniqueCssProvider testObj;

    @Mock
    private OrderingStyleNameConstants styleNameConstants;

    @Test
    public void getNextTest() {
        // given
        when(styleNameConstants.QP_ORDERED_UNIQUE()).thenReturn("qp-ordered-unique");
        // when
        String result1 = testObj.getNext();
        String result2 = testObj.getNext();
        String result3 = testObj.getNext();

        // then
        assertEquals("qp-ordered-unique-1", result1);
        assertEquals("qp-ordered-unique-2", result2);
        assertEquals("qp-ordered-unique-3", result3);

    }

}
