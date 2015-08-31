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
        if (isPageValid(page)) {
            int row = pageToRow.get(page);
            flexTable.getRowFormatter().addStyleName(row, styleName);
        }
    }

    public void removeRowStyleName(int page, String styleName) {
        if (isPageValid(page)) {
            int row = pageToRow.get(page);
            flexTable.getRowFormatter().removeStyleName(row, styleName);
        }
    }

    private boolean isPageValid(int page) {
        return pageToRow.containsKey(page);
    }
}
