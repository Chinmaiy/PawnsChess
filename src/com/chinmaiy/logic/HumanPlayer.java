package com.chinmaiy.logic;

import com.chinmaiy.model.*;
import com.chinmaiy.utils.Color;

import java.util.Scanner;

/**
 * Created by Chinmaiy
 */
public class HumanPlayer extends Player {

    public HumanPlayer(Color color) {
        super(color);
    }

    @Override
    public Move pickMove(Board board) {

        Tile sourceTile = pickSourceTile(board); //need the parameter to test if is valid move (if it is one of the list of the pawn)
        Tile destinationTile = pickDestinationTile();
        return new Move(sourceTile, destinationTile, color);
    }

    private Tile pickSourceTile(Board board) {

        System.out.println("Pick pawn: ");

        return pickTile();
    }

    private Tile pickDestinationTile() {

        System.out.println("Pick next tile: ");

        return pickTile();
    }

    private Tile pickTile() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Row: ");
        int row = Integer.parseInt(scanner.next());
        System.out.println("Col: ");
        int col = Integer.parseInt(scanner.next());

        return new Tile(row, col);
    }
}
