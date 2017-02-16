package com.chinmaiy.view;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Chinmaiy
 */
public class TileView extends Rectangle {

    public final static int SIZE = 60;

    private int row;
    private int col;

    public TileView(int row, int col) {

        this.row = row;
        this.col = col;

        this.resetColor();
        this.setStroke(Color.BLACK);

        this.setX(col * SIZE);
        this.setY(row * SIZE);

        this.setWidth(SIZE);
        this.setHeight(SIZE);
    }

    public void resetColor() {

        Color tileColor = (row + col) % 2 == 0 ? Color.LIGHTYELLOW : Color.DARKGRAY;
        this.setFill(tileColor);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof TileView)) return false;

        TileView tileView = (TileView) o;

        return this.row == tileView.row && this.col == tileView.col;
    }

    @Override
    public int hashCode() {

        int result = row;
        result = 31 * result + col;
        return result;
    }
}
