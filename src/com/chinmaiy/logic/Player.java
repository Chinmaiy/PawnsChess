package com.chinmaiy.logic;

import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Color;
import com.chinmaiy.model.Move;

/**
 * Created by Chinmaiy
 */
public abstract class Player {

    protected Color color;

    public Player(Color color) {

        this.color = color;
    }

    public abstract Move pickMove(Board board);

    public Color getColor() {
        return color;
    }
}
