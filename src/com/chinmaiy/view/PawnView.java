package com.chinmaiy.view;

import com.chinmaiy.utils.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Chinmaiy
 */
public class PawnView extends ImageView {

    private static final int WIDTH = TileView.SIZE - 10;
    private static final int HEIGHT = TileView.SIZE - 10;

    private int row;
    private int col;

    private BoardView board;

    public PawnView(String URL, int row, int col, Color color, BoardView board) {
        super(new Image(URL, WIDTH, HEIGHT, true, true));

        this.board = board;
        this.row = row;
        this.col = col;

        drawAt(row, col);
        /*add listener only for the ones that are human player and only if it's their turn*/
    }

    public void drawAt(int row, int col) {

        this.row = row;
        this.col = col;

        int x = col * TileView.SIZE + 10;
        int y = row * TileView.SIZE + 5;

        this.relocate(x, y);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
