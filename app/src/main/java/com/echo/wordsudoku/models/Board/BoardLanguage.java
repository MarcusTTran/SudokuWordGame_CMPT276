package com.echo.wordsudoku.models.Board;

public class BoardLanguage {
    // The language names
    // SUGGESTION: Use enums instead of integer constants? Maybe iteration 3?
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

    // EFFECT: returns the language name based on the given language name
    // @param language: the language name
    // @return the language name
    // @throws IllegalArgumentException if the given language name is invalid
    public static String getLanguageName(int language) {
        if (language == ENGLISH) {
            return "English";
        } else if (language == FRENCH) {
            return "French";
        } else {
            throw new IllegalArgumentException("Invalid language name");
        }
    }
}
