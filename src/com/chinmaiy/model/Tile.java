package com.chinmaiy.model;

/**
 * @author Chinmaiy
 */
public class Tile {

    private int row;
    private int col;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Tile copy() {
        return new Tile(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public boolean isOnBoard(Board board) {

        int size = board.getSize();

        if(row < 0 || row >= size  || col < 0 || col >= size)
            return false;

        return true;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Tile(").append(row).append(",").append(col).append(")");
        return sb.toString();
    }
}
