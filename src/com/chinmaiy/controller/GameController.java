package com.chinmaiy.controller;

import com.chinmaiy.logic.Player;
import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Color;
import com.chinmaiy.view.GameWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chinmaiy
 */
public class GameController {

    private final Board board;
    private Map<Color, Player> players;

    private GameWindow gameWindow;

    public GameController(Board board, Player player1, Player player2) {

        this.board = board;
        players = new HashMap<>();
        players.put(player1.getColor(), player1);
        players.put(player2.getColor(), player2);

        gameWindow = new GameWindow(this);
    }

    public Board getBoard() {
        return board;
    }

    public Map<Color, Player> getPlayers() {
        return players;
    }

}
