package com.example.owmtest;

public class DirectionConverter {

    public static final String[] DIRECTIONS = new String[]{"N", "NE", "E", "SE", "S", "SW", "NW"};

    public static String convertToCardinalDirection(double deg) {
        return DIRECTIONS[(int) Math.round(((deg % 360) / 45))];
    }

}
