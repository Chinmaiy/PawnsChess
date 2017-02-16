package com.chinmaiy.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chinmaiy on 11/2/2016.
 */
public enum Color {

    NOTHING(0),
    WHITE(-1),
    BLACK(1);

    private final int value;

    private static Map<Integer, Color> map = new HashMap<>();

    static {
        Color[] colors = Color.values();
        for (Color color: colors) {
            map.put(color.value, color);
        }
    }

    Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Color getColorFor(int value) {
        return map.get(value);
    }
}
