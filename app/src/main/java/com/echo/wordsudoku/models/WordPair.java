package com.echo.wordsudoku.models;

public class WordPair {

    String eng; // english word
    String fre; // french word

    int id;

    // constructor
    // EFFECTS: assigns the eng and fre translation of a word
    public WordPair(String eng, String fre, int id) {
        this.eng = eng;
        this.fre = fre;
        this.id = id;
    }

    public String getEnglish() {
        return eng;
    }

    public String getFrench() {
        return fre;
    }

    public String getEnglishOrFrench(String language) {
        if (language == "English") {
            return this.getEnglish();
        } else {
            return this.getFrench();
        }
    }

    public int getId() {
        return id;
    }

    public void setEnglish(String eng) {
        this.eng = eng;
    }

    public void setFrench(String fre) {
        this.fre = fre;
    }

    public void setId(int id) {
        this.id = id;
    }


}
