package com.chinmaiy.logic;

import com.chinmaiy.model.Board;
import com.chinmaiy.model.Move;
import com.chinmaiy.utils.Color;

/**
 * Created by Chinmaiy
 */
public class AIPlayerRandomAndMinMax extends Player {

    private final int RANDOM_MOVE = 3;

    private int movesCounter;
    private AIPlayerRandom randomPlayer;
    private AIPlayerMinMax minMaxPlayer;


    public AIPlayerRandomAndMinMax(Color color) {
        super(color);

        randomPlayer = new AIPlayerRandom(color);
        minMaxPlayer = new AIPlayerMinMax(color);
        movesCounter = 0;
    }

    @Override
    public Move pickMove(Board board) {

        if(movesCounter != 0 && movesCounter % 3 == RANDOM_MOVE)
            return randomPlayer.pickMove(board);

        return minMaxPlayer.pickMove(board);
    }
}
