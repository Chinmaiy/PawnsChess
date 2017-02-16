package com.chinmaiy.model;

import com.chinmaiy.utils.Color;

/**
 * @author Chinmaiy
 */
public class Pawn {

    private Color color;
    private int row;
    private int col;

    public Pawn(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public Pawn copy() {
        return new Pawn(color, row, col);
    }

    public Color getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
