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

package eu.ydp.empiria.player.client.module.selection.model;

public class SelectionGridElementPosition {

    private final int columnNumber;
    private final int rowNumber;

    public SelectionGridElementPosition(int columnNumber, int rowNumber) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + columnNumber;
        result = prime * result + rowNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SelectionGridElementPosition)) {
            return false;
        }
        SelectionGridElementPosition other = (SelectionGridElementPosition) obj;
        if (columnNumber != other.columnNumber) {
            return false;
        }
        if (rowNumber != other.rowNumber) {
            return false;
        }
        return true;
    }

}
