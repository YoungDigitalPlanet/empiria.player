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

package eu.ydp.empiria.player.client.controller.report.table.extraction;

import com.google.gwt.xml.client.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ColspanExtractorTest {

    @InjectMocks
    private ColspanExtractor testObj;

    @Mock
    private Element element;

    @Test
    public void testExtractWhenExists() {
        // given
        int EXPECTED_COLSPAN = 2;
        when(element.getAttribute("colspan")).thenReturn("2");

        // when
        int colspan = testObj.extract(element);

        // then
        assertEquals(colspan, EXPECTED_COLSPAN);
    }

    @Test
    public void testExtractWhenNotExists() {
        // given
        int EXPECTED_COLSPAN = 1;
        when(element.getAttribute("colspan")).thenReturn(null);

        // when
        int colspan = testObj.extract(element);

        // then
        assertEquals(colspan, EXPECTED_COLSPAN);
    }
}
