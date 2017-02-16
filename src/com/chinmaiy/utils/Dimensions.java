package com.chinmaiy.utils;

/**
 * @author Chinmaiy
 */
public enum Dimensions {

    NORMAL_BOARD(8);

    private final int value;

    Dimensions(int value) { this.value = value; }

    public int getValue() { return value; }
}
