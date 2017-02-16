package com.chinmaiy.view;

import com.chinmaiy.controller.GameController;
import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Dimensions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Chinmaiy
 */
public class GameWindow extends Stage {

    private BoardView boardView;
    private GameController gameController;

    public GameWindow(GameController gameController) {

        super();

        this.setTitle("PawnChess");
        this.setResizable(false);

        this.gameController = gameController;
        Board board = gameController.getBoard();
        boardView = new BoardView(board, gameController.getPlayers());
        int boardSize = board.getSize();

        Scene scene = new Scene(boardView, boardSize * TileView.SIZE - 10, boardSize * TileView.SIZE - 10);

        this.setScene(scene);
    }
}
