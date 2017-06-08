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

package eu.ydp.empiria.player.client.controller.report.table.modification;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.report.ReportStyleNameConstants;
import eu.ydp.empiria.player.client.controller.report.table.cell.CellCoords;

public class RowStylesAppenderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private RowStylesAppender testObj;

    private ReportStyleNameConstants styleNameConstants;

    //@formatter:off
    private final String OUTPUT = "" +
            "<colgroup>" +
            "<col></colgroup>" +
            "<tbody align=\"left\">" +
            "<tr></tr>" +
            "<tr></tr>" +
            "<tr class=\"qp-report-table-row qp-report-table-row-2\">" +
            "<td></td>" +
            "<td></td>" +
            "<td></td>" +
            "<td class=\"qp-report-table-cell qp-report-table-col-3\"></td>" +
            "</tr>" +
            "</tbody>";
    //@formatter:on

    @Override
    protected void gwtSetUp() throws Exception {
        styleNameConstants = GWT.create(ReportStyleNameConstants.class);
        testObj = new RowStylesAppender(styleNameConstants);
    }

    public void testShouldAddStylesToRow() {
        // given
        FlexTable flexTable = new FlexTable();
        CellCoords cellCoords = new CellCoords(2, 3);

        // when
        testObj.append(flexTable, cellCoords);

        // then
        String tabelHTML = flexTable.getElement().getInnerHTML();
        assertEquals(tabelHTML, OUTPUT);
    }
}
