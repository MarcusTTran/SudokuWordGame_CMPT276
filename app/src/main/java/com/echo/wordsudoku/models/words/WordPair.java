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


    public static final int LANG1 = 1;
    public static final int LANG2 = 2;

    private String lang1; // english word
    private String lang2; // french word

    // constructor
    // EFFECTS: assigns the eng and fre translation of a word
    public WordPair(String lang1, String lang2) {
        setLang1(lang1);
        setLang2(lang2);
    }


    // getters and setters
    // EFFECT: returns the English translation
    public String getLang1() {
        return lang1;
    }

    // EFFECT: returns the French translation
    public String getLang2() {
        return lang2;
    }
    // EFFECT: sets/changes the English translation
    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    // EFFECT: sets/changes the French translation
    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    // EFFECT: returns the desired language translation based on the given language name
    public String getEitherLanguage(int language) {
        // if language is English
        if (language == 1) {
            // get English word
            return this.getLang1();
        // else if language is English
        } else if (language == 2) {
            // get the french word
            return this.getLang2();
        } else {
            // else if language is invalid throw invalid language exception
            throw new IllegalArgumentException("Invalid language name");
        }
    }

    public boolean isEqual(WordPair other) {
        return this.getLang1().equals(other.getLang1()) && this.getLang2().equals(other.getLang2());
    }

    public boolean doesContain(String word) {
        return this.getLang1().equals(word) || this.getLang2().equals(word);
    }


    // return the json object of the word pair
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("lang1", this.getLang1());
        json.put("lang2", this.getLang2());
        return json;
    }

    //  returns true if the given object is equal to this WordPair
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPair wordPair = (WordPair) o;

        // if both are null
        if (this == null && wordPair == null) {
            return true;
        }
        return lang1.equals(wordPair.lang1) && lang2.equals(wordPair.lang2);
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
