package com.chinmaiy.logic;

import com.chinmaiy.model.Board;
import com.chinmaiy.utils.Color;
import com.chinmaiy.model.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Chinmaiy
 */
public class AIPlayerMinMax extends Player {

    /*when it's his turn he is maximizing the score*/
    private List<Move> nextScoredValidMoves;

    public AIPlayerMinMax(Color color) {

        super(color);
    }

    @Override
    public Move pickMove(Board board) {

        nextScoredValidMoves = new ArrayList<>();

        List<Move> nextValidMoves = board.getNextValidMovesFor(color);

        for (Move move : nextValidMoves) {

            board.doMove(move);
            move.setScore(minimax(board, 3));
            nextScoredValidMoves.add(move);
            board.restoreBefore(move);
        }

        return pickMove(nextScoredValidMoves);
    }

    private Move pickMove(List<Move> scoredValidMoves) {

        Collections.sort(nextScoredValidMoves);
        //scoredValidMoves.stream().forEach(System.out::println);
        int maxScore = nextScoredValidMoves.get(0).getScore();
        List<Move> chooseFrom = nextScoredValidMoves.stream()
                .filter(move -> move.getScore() == maxScore)
                .collect(Collectors.toList());

        int size = chooseFrom.size();
        Random random = new Random();
        int chosenMoveIdx = random.nextInt(size);

       // System.out.println("Chosen move: " + nextScoredValidMoves.get(chosenMoveIdx));
       // System.out.println();
        return nextScoredValidMoves.get(chosenMoveIdx);
    }

    private int minimax(Board board, int depth) {

        if(board.isFinal())
            return board.isWinner(color) ?  board.WINNER_SCORE : -board.WINNER_SCORE;

        if(depth == 0)
            return board.computeScoreFor(color);

        List<Integer> scores = new ArrayList<>();
        List<Move> nextValidMoves = board.getNextValidMovesFor(board.getCurrentPlayer());

        for (Move move : nextValidMoves) {

            board.doMove(move);
            int currentScore = minimax(board, depth - 1);
            scores.add(currentScore);
            board.restoreBefore(move);
        }

        Collections.sort(scores);
        int scoreIdx = board.getCurrentPlayer() == color ? scores.size() - 1 : 0;
        return scores.get(scoreIdx);
    }
}
