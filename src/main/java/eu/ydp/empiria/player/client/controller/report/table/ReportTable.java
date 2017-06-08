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
