package com.chinmaiy.model;

import com.chinmaiy.exceptions.InvalidBoardException;
import com.chinmaiy.utils.Color;
import com.chinmaiy.utils.Dimensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chinmaiy
 */
public class Board {

    public static final int WINNER_SCORE = 1000;

    private int[][] board;
    private int size;

    private Color currentPlayer;

    public Board() {

        size = Dimensions.NORMAL_BOARD.getValue();

        board = new int[size][size];
        Arrays.fill(board[1], Color.BLACK.getValue());
        Arrays.fill(board[size - 2], Color.WHITE.getValue());

        currentPlayer = Color.WHITE;
    }

    public Board(int[][] board, Color currentPlayer) throws InvalidBoardException {

        int rowsNr = board.length;
        int colsNr = board[0].length;
        if(rowsNr != colsNr)
            throw new InvalidBoardException();

        size = rowsNr;
        this.board = new int[size][size];
        this.board = Arrays.copyOf(board, size * size);

        this.currentPlayer = currentPlayer;
    }

    public List<Pawn> getPawnsFor(Color color) {

        List<Pawn> pawns = new ArrayList<>();

        int lookFor = color.getValue();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col)
                if(board[row][col] == lookFor)
                    pawns.add(new Pawn(color, row, col));
        }

        return pawns;
    }

    public List<Tile> getNextValidTilesFor(Pawn pawn) {

        List<Tile> nextValidTiles = new ArrayList<>();

        int sign = pawn.getColor().getValue();
        Color opponent = Color.getColorFor(-sign);

        int pawnRow = pawn.getRow();
        int pawnCol = pawn.getCol();

        Tile left = new Tile(pawnRow + sign, pawnCol + sign);
        Tile right = new Tile(pawnRow + sign, pawnCol - sign);
        Tile up = new Tile(pawnRow + sign, pawnCol);

        if(left.isOnBoard(this) && whatIsOn(left) == opponent)
            nextValidTiles.add(left);

        if(right.isOnBoard(this) && whatIsOn(right) == opponent)
            nextValidTiles.add(right);

        if(up.isOnBoard(this) && whatIsOn(up) == Color.NOTHING)
            nextValidTiles.add(up);

        if(pawnRow == 1 || pawnRow == size - 2) {

            Tile upup = new Tile(pawnRow + 2 * sign, pawnCol);
            if(upup.isOnBoard(this) && whatIsOn(upup) == Color.NOTHING)
                nextValidTiles.add(upup);
        }

        return nextValidTiles;
    }

    public List<Move> getNextValidMovesFor(Color color) {

        List<Move> nextValidMoves = new ArrayList<>();

        List<Pawn> pawns = this.getPawnsFor(color);
        for (Pawn pawn : pawns) {

            List<Tile> nextValidTileForPawn = this.getNextValidTilesFor(pawn);
            nextValidMoves.addAll(nextValidTileForPawn.stream()
                    .map(tile -> new Move(pawn.getRow(), pawn.getCol(), tile.getRow(), tile.getCol(), color))
                    .collect(Collectors.toList()));
        }

        return nextValidMoves;
    }

    /*should change this to reflect the winner also*/
    public boolean isFinal() {

        int empty = Color.NOTHING.getValue();

        //someone got to the finish line
        for(int col = 0; col < size; ++col)
            if(board[0][col] != empty || board[size - 1][col] != empty )
                return true;

        //current player does not have any pawns left (equivalent to not having any moves left)
        List<Pawn> currentPlayersPawns = getPawnsFor(currentPlayer);
        if(currentPlayersPawns.isEmpty())
            return true;

        //current player is blocked (should be a draw if the previous player is also blocked as of his last move -> this state
        boolean canMove = false;
        for (Pawn pawn : currentPlayersPawns)
            if(!getNextValidTilesFor(pawn).isEmpty()) {
                canMove = true;
                break;
            }

        if(!canMove)
            return true;

        return false;
    }

    public boolean isWinner(Color player) {

        int lookFor = player.getValue();
        for(int col = 0; col < size; ++col)
            if(board[0][col] == lookFor || board[size - 1][col] == lookFor)
                return true;

        int opponent = -player.getValue();
        if(currentPlayer.getValue() == opponent) {
            List<Pawn> currentPlayersPawns = getPawnsFor(currentPlayer);
            if(currentPlayersPawns.isEmpty())
                return true;

            boolean opponentCanMove = false;
            for (Pawn pawn : currentPlayersPawns) {
                if(!getNextValidTilesFor(pawn).isEmpty()) {
                    opponentCanMove = true;
                    break;
                }
            }

            if(!opponentCanMove)
                return true;
        }

        return false;
    }

    private Color whatIsOn(Tile tile) {

        return Color.getColorFor(board[tile.getRow()][tile.getCol()]);
    }

    public void doMove(Move move) {

        Tile from = move.getSource();
        Tile to = move.getDestination();

        board[from.getRow()][from.getCol()] = Color.NOTHING.getValue();
        board[to.getRow()][to.getCol()] = currentPlayer.getValue();

        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    public void restoreBefore(Move move) {

        Color whoDid = move.getPlayer();
        int opponent = -whoDid.getValue();

        Tile from = move.getSource();
        Tile to = move.getDestination();

        board[from.getRow()][from.getCol()] = whoDid.getValue();
        board[to.getRow()][to.getCol()] = move.isAttackingMove() ? opponent : Color.NOTHING.getValue();

        currentPlayer = whoDid;
    }

    public int getSize() {
        return size;
    }

    public int[][] getBoardRepresentation() {
        return board;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public int computeScoreFor(Color player) {

        int score = 0;

        Color opponent = Color.getColorFor(-player.getValue());

        List<Pawn> pawns = this.getPawnsFor(player);
        List<Pawn> opponentsPawns = this.getPawnsFor(opponent);
        score += 100 * (pawns.size() - opponentsPawns.size()); //offensive strategy: go for the opponent's pawns

        for (Pawn pawn : pawns) {
            List<Tile> validTiles = this.getNextValidTilesFor(pawn);
            score += validTiles.size(); //mobility

            if(!validTiles.isEmpty()) {

                int distanceToFinishScore = player == Color.WHITE ? size - pawn.getRow() : pawn.getRow();
                if(this.isOnFreeColumn(pawn))
                    score += 50 * distanceToFinishScore;
                else
                    score += 2 * distanceToFinishScore;

                score += this.getAttackersVsDefendersScoreFor(pawn);
            }
            else
                score -= 50; //blocked pawn (should take into consideration if it can be unblocked?)
        }

        return score;
    }

    private boolean isOnFreeColumn(Pawn pawn) {

        int opponent = -pawn.getColor().getValue();
        int col = pawn.getCol();

        if(pawn.getColor() == Color.WHITE) {
            for(int row = pawn.getRow() - 1; row >= 0; --row)
                if(board[row][col] == opponent)
                    return false;
        }
        else {
            for(int row = pawn.getRow() + 1; row < size; ++row)
                if(board[row][col] == opponent)
                    return false;
        }

        return true;
    }

    private int getAttackersVsDefendersScoreFor(Pawn pawn) {

        int diff = this.getDefendersFor(pawn) - this.getAttackersFor(pawn);
        if(diff >= 0)
            return 100;
        else
            return -200;
    }

    private int getAttackersFor(Pawn pawn) {

        int sign = pawn.getColor().getValue();
        int row = pawn.getRow();
        int col = pawn.getCol();

        int nrOfAttackers = 0;

        Tile right = new Tile(row + sign, col + sign);
        if(right.isOnBoard(this) && board[row + sign][col + sign] == -sign)
            nrOfAttackers++;

        Tile left = new Tile(row + sign, col - sign);
        if(left.isOnBoard(this) && board[row + sign][col - sign] == -sign)
            nrOfAttackers++;

        return nrOfAttackers;
    }

    private int getDefendersFor(Pawn pawn) {

        int sign = pawn.getColor().getValue();
        int row = pawn.getRow();
        int col = pawn.getCol();

        int nrOfDefenders = 0;

        Tile left = new Tile(row - sign, col - sign);
        if(left.isOnBoard(this) && board[row - sign][col - sign] == sign)
            nrOfDefenders++;

        Tile right = new Tile(row - sign, col + sign);
        if(right.isOnBoard(this) && board[row - sign][col + sign] == sign)
            nrOfDefenders++;

        return nrOfDefenders++;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < size; ++row) {

            sb.append(row).append(" ");
            for (int col = 0; col < size; ++col) {

                if (Color.getColorFor(board[row][col]) == Color.WHITE)
                    sb.append("w ");
                else if(Color.getColorFor(board[row][col]) == Color.BLACK)
                    sb.append("b ");
                else
                    sb.append("_ ");
            }
            sb.append("\n");
        }
        sb.append("  ");
        for (int col = 0; col < size; ++col)
            sb.append(col).append(" ");
        sb.append("\n");
        return sb.toString();
    }
}
