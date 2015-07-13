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
