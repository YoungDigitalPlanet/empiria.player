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

import com.google.gwt.user.client.ui.FlexTable;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.cell.CellCoords;
import eu.ydp.empiria.player.client.module.report.ReportStyleNameConstants;

public class RowStylesAppender {

    private static final String TABLE_COL_PREFIX = "qp-report-table-col-";
    private static final String TABLE_CELL_STYLE = "qp-report-table-cell";
    private final ReportStyleNameConstants styleNames;

    @Inject
    public RowStylesAppender(ReportStyleNameConstants styleNames) {
        this.styleNames = styleNames;
    }

    public void append(FlexTable table, CellCoords cellCoords) {
        table.getFlexCellFormatter().addStyleName(cellCoords.getRow(), cellCoords.getCol(), TABLE_CELL_STYLE);
        table.getFlexCellFormatter().addStyleName(cellCoords.getRow(), cellCoords.getCol(), TABLE_COL_PREFIX + cellCoords.getCol());
        table.getRowFormatter().addStyleName(cellCoords.getRow(), styleNames.QP_REPORT_TABLE_ROW());
        table.getRowFormatter().addStyleName(cellCoords.getRow(), styleNames.QP_REPORT_TABLE_ROW() + "-" + cellCoords.getRow());
    }
}
