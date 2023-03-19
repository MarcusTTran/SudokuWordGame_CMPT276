package com.echo.wordsudoku.models.words;

import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.Writable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

public class WordPair implements Writable {

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

    public boolean isEqual(WordPair other) {
        return this.getEnglish().equals(other.getEnglish()) && this.getFrench().equals(other.getFrench());
    }

    public boolean doesContain(String word) {
        return this.getEnglish().equals(word) || this.getFrench().equals(word);
    }


    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("eng", this.getEnglish());
        json.put("fre", this.getFrench());
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPair wordPair = (WordPair) o;

        // if both are null
        if (this == null && wordPair == null) {
            return true;
        }
        return eng.equals(wordPair.eng) && fre.equals(wordPair.fre);
    }

    public static boolean doesListContainRepeatingWordPairs(List<WordPair> wordPairs) {
        for (int i = 0; i < wordPairs.size(); i++) {
            for (int j = i+1; j < wordPairs.size(); j++) {
                if (wordPairs.get(i).equals(wordPairs.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean doesListContainThisWordPair(List<WordPair> wordPairs, WordPair wordPair) {
        for (WordPair wp : wordPairs) {
            if(wordPair!=null) {
                if (wordPair.equals(wp)) {
                    return true;
                }
            }
        }
        return false;
    }

}
