package eu.ydp.empiria.player.client.controller.report.table;

import com.google.gwt.user.client.ui.FlexTable;

import java.util.Map;

public class ReportTable {

    private final FlexTable flexTable;
    private final Map<Integer, Integer> pageToRow;

    public ReportTable(FlexTable flexTable, Map<Integer, Integer> pageToRow) {
        this.flexTable = flexTable;
        this.pageToRow = pageToRow;
    }

    public FlexTable getFlexTable() {
        return flexTable;
    }

    public void addRowStyleName(int page, String styleName) {
        int row = currentRow(page);
        if (isRowValid(row)) {
            flexTable.getRowFormatter().addStyleName(row, styleName);
        }
    }

    public void removeRowStyleName(int page, String styleName) {
        int row = currentRow(page);
        if (isRowValid(row)) {
            flexTable.getRowFormatter().removeStyleName(row, styleName);
        }
    }

    private int currentRow(int page) {
        if (pageToRow.containsKey(page)) {
            return pageToRow.get(page);
        }
        return -1;
    }

    private boolean isRowValid(int row) {
        return row >= 0;
    }
}
