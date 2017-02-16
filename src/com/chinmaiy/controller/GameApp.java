package com.chinmaiy.controller;

import com.chinmaiy.logic.*;
import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Color;
import com.chinmaiy.view.GameWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Chinmaiy
 */
public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        Board board = new Board();
        Player player1 = new HumanPlayer(Color.WHITE);
        Player player2 = new AIPlayerRandomAndMinMax(Color.BLACK);

        GameController gameController = new GameController(board, player1, player2);
        GameWindow gameWindow = new GameWindow(gameController);
        gameWindow.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
