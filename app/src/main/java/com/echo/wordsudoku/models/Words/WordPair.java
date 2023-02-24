package com.echo.wordsudoku.models.Words;

import com.echo.wordsudoku.models.Board.BoardLanguage;

/**
 *  ========================================= WORDPAIR =========================================
 *  DESCRIPTION OF FIELDS AND FEATURES
 -   WordPair contains a english and french translation of a word
 -   it also has an id to for its identification while place on a board cell
 -   it also provides getters and setters for its field
 -   The purpose of the class is to collect the relevant words which will be given to the board
     as a list of WordPairs for the generation of a new game
 ========================================= WORDPAIR ===========================================
 */

public class WordPair {

    private String eng; // english word
    private String fre; // french word

    // constructor
    // EFFECTS: assigns the eng and fre translation of a word
    // @param - str eng - english word
    //          str fre - french word
    public WordPair(String eng, String fre) {
        setEnglish(eng);
        setFrench(fre);
    }


    // getters and setters

    // EFFECT: get english
    // @returns the English translation
    public String getEnglish() {
        return eng;
    }

    // EFFECT: get french
    // @return returns the French translation
    public String getFrench() {
        return fre;
    }
    // EFFECT: sets/changes the English translation
    // @param String eng - english word to be set
    public void setEnglish(String eng) {
        this.eng = eng;
    }

    // EFFECT: sets/changes the French translation
    // @param String fre - french word to be set
    public void setFrench(String fre) {
        this.fre = fre;
    }

    // EFFECT: get the chosen translation
    // @return the desired language translation based on the given language name
    public String getEnglishOrFrench(int language) {
        // if language is English
        if (language == BoardLanguage.ENGLISH) {
            // get English word
            return this.getEnglish();
        // else if language is English
        } else if (language == BoardLanguage.FRENCH) {
            // get the french word
            return this.getFrench();
        } else {
            // else if language is invalid throw invalid language exception
            throw new IllegalArgumentException("Invalid language name");
        }
    }


}
