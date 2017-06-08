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
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_ITEMS_INCLUDE;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagesRangeExtractorTest {

    @InjectMocks
    private PagesRangeExtractor testObj;

    @Mock
    private DataSourceDataSupplier dataSourceDataSupplier;
    @Mock
    private StyleSocket styleSocket;
    @Mock
    private Element element;

    @Test
    public void shouldExtractRangeWhenDefined() {
        // given
        int ITEMS_COUNT = 12;
        when(dataSourceDataSupplier.getItemsCount()).thenReturn(ITEMS_COUNT);

        Map<String, String> styles = new HashMap<String, String>();
        styles.put(EMPIRIA_REPORT_ITEMS_INCLUDE, "1,3,9:-2");
        when(styleSocket.getStyles(element)).thenReturn(styles);

        // when
        List<Integer> pagesRange = testObj.extract(element);

        // then
        assertThat(pagesRange).containsExactly(0, 2, 8, 9, 10);
    }

    @Test
    public void shouldExtractRangeWhenNotDefined() {
        // given
        int ITEMS_COUNT = 4;
        when(dataSourceDataSupplier.getItemsCount()).thenReturn(ITEMS_COUNT);

        Map<String, String> styles = new HashMap<String, String>();
        when(styleSocket.getStyles(element)).thenReturn(styles);

        // when
        List<Integer> pagesRange = testObj.extract(element);

        // then
        assertThat(pagesRange).containsExactly(0, 1, 2, 3);
    }
}
