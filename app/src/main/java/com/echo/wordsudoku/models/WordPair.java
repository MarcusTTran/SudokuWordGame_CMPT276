package com.echo.wordsudoku.models;

public class WordPair {

    String eng; // english word
    String fre; // french word
    int id; // used to do comparison of translation (still unsure to keep it)

    // constructor
    // EFFECTS: assigns the eng and fre translation of a word with a specific id
    public WordPair(String eng, String fre, int id) {
        this.eng = eng;
        this.fre = fre;
        this.id = id; //
    }

}
