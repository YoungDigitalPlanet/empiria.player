package eu.ydp.empiria.player.client.module.report.table.cell;

public class CellCoords {
    private final int row;
    private final int col;

    public CellCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
