package com.chinmaiy.controller;

import com.chinmaiy.model.Board;
import com.chinmaiy.model.Pawn;
import com.chinmaiy.model.Tile;
import com.chinmaiy.view.BoardView;
import com.chinmaiy.view.PawnView;
import com.chinmaiy.view.TileView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chinmaiy
 */
public class PawnClickEventHandler implements EventHandler<MouseEvent> {

    private BoardView boardView;
    private PawnView pawnView;

    private static Map<TileView, ValidMoveTileClickEventHandler> nextValidTileViews;

    public PawnClickEventHandler(BoardView boardView, PawnView pawnView) {

        this.boardView = boardView;
        this.pawnView = pawnView;

        nextValidTileViews = new HashMap<>();
    }

    @Override
    public void handle(MouseEvent event) {

        this.resetValidTileViews();

        int row = (int)event.getSceneY() / TileView.SIZE;
        int col = (int)event.getSceneX() / TileView.SIZE;

        Board board = boardView.getBoard();
        List<Tile> nextValidTiles = board.getNextValidTilesFor(new Pawn(board.getCurrentPlayer(), row, col));

        for (Tile tile : nextValidTiles) {

            TileView tileView = boardView.getTileViewByPosition(tile.getRow(), tile.getCol());
            tileView.setFill(Color.LIGHTGREEN);
            ValidMoveTileClickEventHandler eventHandler = new ValidMoveTileClickEventHandler(this, tileView);
            tileView.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

            nextValidTileViews.put(tileView, eventHandler);
        }
    }

    public void resetValidTileViews() {

        nextValidTileViews.forEach((tileView, ev) -> {

            tileView.resetColor();
            tileView.removeEventHandler(MouseEvent.MOUSE_CLICKED, ev);
        });
        nextValidTileViews.clear();
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public PawnView getPawnView() {
        return pawnView;
    }

    public Map<TileView, ValidMoveTileClickEventHandler> getNextValidTileViews() {
        return nextValidTileViews;
    }
}
