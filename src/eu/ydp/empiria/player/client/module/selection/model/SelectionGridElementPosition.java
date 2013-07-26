package eu.ydp.empiria.player.client.module.selection.model;


public class SelectionGridElementPosition {
	
	public SelectionGridElementPosition(
			int columnNumber,
			int rowNumber) {
		setColumnNumber(columnNumber);
		setRowNumber(rowNumber);
	}

	private int columnNumber;
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	private int rowNumber;
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	@Override 
	public boolean equals(Object objectToCompare) {
		if (this == objectToCompare) {
			return true;
		}
		if (objectToCompare == null) {
			return false;
		}
		SelectionGridElementPosition positionToCompare = (SelectionGridElementPosition)objectToCompare; 
		if(positionToCompare.columnNumber == columnNumber &&
			positionToCompare.rowNumber == rowNumber) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Integer.parseInt(toString());
	}
	
	public String toString() {
		return "17" + String.valueOf(columnNumber) + String.valueOf(rowNumber);
	}
}
