package com.chinmaiy.logic;

import com.chinmaiy.model.*;
import com.chinmaiy.utils.Color;

import java.util.List;
import java.util.Random;

/**
 * Created by Chinmaiy
 */
public class AIPlayerRandom extends Player {

    public AIPlayerRandom(Color color) {
        super(color);
    }

    @Override
    public Move pickMove(Board board) {

        List<Pawn> pawns = board.getPawnsFor(color); //or get the color of the current player from the board
        int pawnsNr = pawns.size();

        Random random = new Random();
        int pawnIndex = random.nextInt(pawnsNr);

        Pawn pickedPawn = pawns.get(pawnIndex);
        Tile source = new Tile(pickedPawn.getRow(), pickedPawn.getCol());

        List<Tile> nextValidTiles = board.getNextValidTilesFor(pickedPawn);
        int movesNr = nextValidTiles.size();

        random = new Random();
        int tileIndex = random.nextInt(movesNr);
        Tile destination = nextValidTiles.get(tileIndex);

        return new Move(source, destination, color);
    }
}
