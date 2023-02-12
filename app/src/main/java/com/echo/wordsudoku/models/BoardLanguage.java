package com.echo.wordsudoku.models;

public class BoardLanguage {
    // The language names
    public static final int ENGLISH = 0;
    public static final int FRENCH = 1;

    // EFFECT: returns the other language name based on the given language name
    // @param language: the language name
    // @return the other language name
    // @throws IllegalArgumentException if the given language name is invalid
    public static int getOtherLanguage(int language) {
        if (language == ENGLISH) {
            return FRENCH;
        } else if (language == FRENCH) {
            return ENGLISH;
        } else {
            throw new IllegalArgumentException("Invalid language name");
        }
    }
}
