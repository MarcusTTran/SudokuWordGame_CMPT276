package com.echo.wordsudoku.models;

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
    public WordPair(String eng, String fre) {
        setEnglish(eng);
        setFrench(fre);
    }


    // getters and setters
    // EFFECT: returns the English translation
    public String getEnglish() {
        return eng;
    }

    // EFFECT: returns the French translation
    public String getFrench() {
        return fre;
    }
    // EFFECT: sets/changes the English translation
    public void setEnglish(String eng) {
        this.eng = eng;
    }

    // EFFECT: sets/changes the French translation
    public void setFrench(String fre) {
        this.fre = fre;
    }




    // EFFECT: returns the desired language translation based on the given language name
    public String getEnglishOrFrench(int language) {
        if (language == BoardLanguage.ENGLISH) {
            return this.getEnglish();
        } else if (language == BoardLanguage.FRENCH) {
            return this.getFrench();
        } else {
            throw new IllegalArgumentException("Invalid language name");
        }
    }


}
