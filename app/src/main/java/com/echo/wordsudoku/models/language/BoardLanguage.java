package com.echo.wordsudoku.models.language;


/*
* BoardLanguage class represents the language of the board
* It contains the language names (ENGLISH, FRENCH) and the default language (defaultLanguage: int)
* It also contains the methods to get the other language name and the language name based on the given language name
* @author Echo
* @version 1.0
* @since 1.0
* @see WordPair
* @see Cell
* @see Puzzle
*
* Usage:
* BoardLanguage.ENGLISH
* BoardLanguage.FRENCH
* BoardLanguage.defaultLanguage()
* BoardLanguage.getOtherLanguage(language)
* BoardLanguage.getLanguageName(language)
*
* @author eakbarib
*
* @version 1.0
*
* TODO: Use enums instead of integer constants? Maybe iteration 3?
*  */

import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.models.words.WordPair;

public class BoardLanguage {
    // The language names
    // SUGGESTION: Use enums instead of integer constants? Maybe iteration 3?
    public static final int ENGLISH = 0;
    public static final int FRENCH = 1;

    public static final int SPANISH = 2;
    public static final int CHINESE = 3;
    public static final int ARABIC = 4;

    // @utility method
    // Returns the language name based on the given language name
    // @param language: the language name
    // @return the language name
    // @throws IllegalArgumentException if the given language name is invalid
    public static String getLanguageName(int language) throws IllegalLanguageException {
        switch (language) {
            case ENGLISH:
                return "English";
            case FRENCH:
                return "French";
            case SPANISH:
                return "Spanish";
            case CHINESE:
                return "Chinese";
            case ARABIC:
                return "Arabic";
            default:
                throw new IllegalLanguageException();
        }
    }

    // @utility method
    // Returns whether the given language name is valid or not
    // @param language: the language
    // @return whether the given language name is valid or not
    public static boolean isValidLanguage(int language) {
        return language == ENGLISH || language == FRENCH || language == SPANISH || language == CHINESE || language == ARABIC;
    }


    // @utility method
    // Returns the default language name (which is ENGLISH)
    // @return the default language name
    public static int defaultLanguage() {
        return WordPair.LANG1;
    }


}
