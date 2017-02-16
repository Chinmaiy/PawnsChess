package com.chinmaiy.controller;

import com.chinmaiy.logic.HumanPlayer;
import com.chinmaiy.logic.Player;
import com.chinmaiy.model.Board;
import com.chinmaiy.model.Move;
import com.chinmaiy.utils.Color;
import com.chinmaiy.view.BoardView;
import com.chinmaiy.view.TileView;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.Map;

/**
 * Created by Chinmaiy
 */
public class ValidMoveTileClickEventHandler implements EventHandler<MouseEvent> {

    private PawnClickEventHandler pawnClickEvent;
    private TileView tileView;

    public ValidMoveTileClickEventHandler(PawnClickEventHandler pawnClickEvent, TileView tileView) {

        this.pawnClickEvent = pawnClickEvent;
        this.tileView = tileView;
    }

    @Override
    public void handle(MouseEvent event) {

        int destinationRow = (int)event.getSceneY() / TileView.SIZE;
        int destinationCol = (int)event.getSceneX() / TileView.SIZE;

        pawnClickEvent.resetValidTileViews();

        int sourceRow = pawnClickEvent.getPawnView().getRow();
        int sourceCol = pawnClickEvent.getPawnView().getCol();

        BoardView boardView = pawnClickEvent.getBoardView();
        Board board = boardView.getBoard();
        board.doMove(new Move(sourceRow, sourceCol, destinationRow, destinationCol, board.getCurrentPlayer()));
        if(board.isFinal())
            this.showWinner();
        else {

            Player currentPlayer = boardView.getPlayers().get(board.getCurrentPlayer());
            if(!(currentPlayer instanceof HumanPlayer)) {
                Move nextMove = currentPlayer.pickMove(board);
                board.doMove(nextMove);
                if(board.isFinal())
                    this.showWinner();
                else
                    boardView.draw();
            }
            else
                boardView.draw();
        }
    }

    public void showWinner() {

        BoardView boardView = pawnClickEvent.getBoardView();
        boardView.getChildren().clear();

        Color winner = Color.getColorFor(-boardView.getBoard().getCurrentPlayer().getValue());

        Label winnerLabel = new Label(winner.name() + " wins!!!");
        winnerLabel.setFont(new Font("Cambria", 56));

        winnerLabel.relocate(80, 200);

        boardView.getChildren().add(winnerLabel);
    }
}
