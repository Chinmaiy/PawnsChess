package com.chinmaiy.view;

import com.chinmaiy.controller.PawnClickEventHandler;
import com.chinmaiy.logic.HumanPlayer;
import com.chinmaiy.logic.Player;
import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * Created by Chinmaiy
 */
public class BoardView extends Pane {

    private final String blackPawnURL = "file:resources/BlackPawn.png";
    private final String whitePawnURL = "file:resources/WhitePawn.png";

    private Board board;
    private int size;
    private Map<Color, Player> players;

    public BoardView(int size) {

        this.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.WHITE, null, null)));

        for(int row = 0; row < size; ++ row)
            for(int col = 0; col < size; ++col) {

            TileView tileView = new TileView(row, col);
            this.getChildren().add(tileView);
        }

        /*for now white is always down*/

        for(int col = 0; col < size; col++) {

            PawnView whitePawnView = new PawnView(whitePawnURL, size - 2, col, Color.WHITE, this);
            whitePawnView.addEventHandler(MouseEvent.MOUSE_CLICKED, new PawnClickEventHandler(this, whitePawnView));
            PawnView blackPawnView = new PawnView(blackPawnURL, 1, col, Color.BLACK, this);
            this.getChildren().addAll(whitePawnView, blackPawnView);
        }

    }

    public BoardView(Board board, Map<Color, Player> players) {

        this.board = board;
        this.players = players;
        size = board.getSize();

        this.draw();
    }

    public void draw() {

        this.getChildren().clear();

        Player currentPlayer = players.get(board.getCurrentPlayer());

        int[][] boardMatrix = board.getBoardRepresentation();

        this.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.WHITE, null, null)));

        for(int row = 0; row < size; ++row)
            for(int col = 0; col < size; ++col) {
                TileView tileView = new TileView(row, col);
                this.getChildren().add(tileView);

                Color boardColor = Color.getColorFor(boardMatrix[row][col]);
                if(boardColor != Color.NOTHING) {

                    String pawnURL = boardColor == Color.WHITE ? whitePawnURL : blackPawnURL;
                    PawnView pawnView = new PawnView(pawnURL, row, col, boardColor, this);
                    this.getChildren().add(pawnView);

                    if(currentPlayer instanceof HumanPlayer && boardColor == currentPlayer.getColor())
                        pawnView.addEventHandler(MouseEvent.MOUSE_CLICKED, new PawnClickEventHandler(this, pawnView));
                }

            }
    }

    public TileView getTileViewByPosition(int row, int col) {

        int idx = this.getChildren().indexOf(new TileView(row, col));
        return (TileView)this.getChildren().get(idx);
    }

    public Board getBoard() {
        return board;
    }

    public Map<Color, Player> getPlayers() {
        return players;
    }
}

