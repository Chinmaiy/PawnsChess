package com.chinmaiy.exceptions;

/**
 * @author Chinmaiy
 */
public class InvalidBoardException extends Exception {

    public InvalidBoardException(String message) {
        super(message);
    }

    public InvalidBoardException() {
        this("The board must be a square-shaped.");
    }
}
