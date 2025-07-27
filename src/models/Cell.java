package models;

import java.util.Objects;

public class Cell {
    private int row;
    private int col;
    private CellState state;

    public Cell(int row, int col, CellState state) {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isWall() {
        return state == CellState.WALL;
    }

    public boolean isStart() {
        return state == CellState.START;
    }

    public boolean isEnd() {
        return state == CellState.END;
    }

    public boolean isVisited() {
        return state == CellState.VISITED;
    }

    public boolean isSolution() {
        return state == CellState.SOLUTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Cell{" +
               "row=" + row +
               ", col=" + col +
               ", state=" + state +
               '}';
    }
}