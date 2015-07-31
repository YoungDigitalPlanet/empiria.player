package eu.ydp.empiria.player.client.module.report.table.modification;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.report.table.cell.CellCoords;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class RowStylesAppender {

    private static final String TABLE_COL_PREFIX = "qp-report-table-col-";
    private static final String TABLE_CELL_STYLE = "qp-report-table-cell";
    private final StyleNameConstants styleNames;

    @Inject
    public RowStylesAppender(StyleNameConstants styleNames) {
        this.styleNames = styleNames;
    }

    public void append(FlexTable table, CellCoords cellCoords) {
        table.getFlexCellFormatter().addStyleName(cellCoords.getRow(), cellCoords.getCol(), TABLE_CELL_STYLE);
        table.getFlexCellFormatter().addStyleName(cellCoords.getRow(), cellCoords.getCol(), TABLE_COL_PREFIX + cellCoords.getCol());
        table.getRowFormatter().addStyleName(cellCoords.getRow(), styleNames.QP_REPORT_TABLE_ROW());
        table.getRowFormatter().addStyleName(cellCoords.getRow(), styleNames.QP_REPORT_TABLE_ROW() + "-" + cellCoords.getRow());
    }
}
