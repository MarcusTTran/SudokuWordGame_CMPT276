package com.echo.wordsudoku.models;

public class WordPair {

    String eng; // english word
    String fre; // french word

    // constructor
    // EFFECTS: assigns the eng and fre translation of a word
    public WordPair(String eng, String fre) {
        this.eng = eng;
        this.fre = fre;
    }

    public String getEnglish() {
        return eng;
    }

    public String getFrench() {
        return fre;
    }

    public void setEnglish(String eng) {
        this.eng = eng;
    }

    public void setFrench(String fre) {
        this.fre = fre;
    }
}
