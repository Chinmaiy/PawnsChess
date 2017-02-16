package com.chinmaiy.model;

import com.chinmaiy.utils.Color;

/**
 * @author Chinmaiy
 */
public class Move implements Comparable<Move> {

    private Tile from;
    private Tile to;

    private Color player;

    private int score;

    public Move(Tile source, Tile destination, Color player) {

        from = source;
        to = destination;

        this.player = player;

        score = 0;
    }

    public Move(int sourceRow, int sourceCol, int destinationRow, int destinationCol, Color player) {

        this(new Tile(sourceRow, sourceCol), new Tile(destinationRow, destinationCol), player);
    }

    public boolean isAttackingMove() {

        return from.getCol() == to.getCol() + 1 || from.getCol() == to.getCol() - 1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tile getSource() {
        return from;
    }

    public Tile getDestination() {
        return to;
    }

    public Color getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Move move) {
        return move.score - score; //it will be negative when this.score is better so to sort them in descending order
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Move from ").append(from).append(" to ").append(to).append(" | score: ").append(score);
        return sb.toString();
    }
}
