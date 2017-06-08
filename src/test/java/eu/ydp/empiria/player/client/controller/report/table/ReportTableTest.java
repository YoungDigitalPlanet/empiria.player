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

package eu.ydp.empiria.player.client.controller.report.table;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ReportTableTest {

    private ReportTable testObj;
    private FlexTable flexTable;
    private Map<Integer, Integer> pageToRow;
    private String styleName = "styleName";
    private int mappedPage = 3;
    private int mappedRow = 2;

    @Before
    public void init() {
        flexTable = mock(FlexTable.class, RETURNS_DEEP_STUBS);
        pageToRow = new HashMap<>();
        testObj = new ReportTable(flexTable, pageToRow);
        pageToRow.put(mappedPage, mappedRow);
    }

    @Test
    public void shouldAddRowStyleName_whenPageIsMapped() {
        // when
        testObj.addRowStyleName(mappedPage, styleName);

        // then
        verify(flexTable.getRowFormatter()).addStyleName(mappedRow, styleName);
    }

    @Test
    public void shouldRemoveRowStyleName_whenPageIsMapped() {
        // when
        testObj.removeRowStyleName(mappedPage, styleName);

        // then
        verify(flexTable.getRowFormatter()).removeStyleName(mappedRow, styleName);
    }

    @Test
    public void shouldNotAdd_OrRemoveRowStyleName_whenPageIsNotMapped(){
        // given
        int notMappedPage = 5;

        // when
        testObj.addRowStyleName(notMappedPage, styleName);
        testObj.removeRowStyleName(notMappedPage, styleName);

        // then
        verifyZeroInteractions(flexTable);
    }
}