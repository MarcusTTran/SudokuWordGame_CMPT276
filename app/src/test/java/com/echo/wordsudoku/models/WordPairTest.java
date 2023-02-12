package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordPairTest {

    @Test
    void getEnglish() {
        String englishWord = "Water";
        WordPair testWordPair = new WordPair(englishWord, "Aqua");
        assertEquals(englishWord, testWordPair.getEnglish());
    }

    @Test
    void getFrench() {
        String frenchWord = "Aqua";
        WordPair testWordPair = new WordPair("Water", frenchWord);
        assertEquals(frenchWord, testWordPair.getFrench());
    }

    @Test
    void getEnglishOrFrenchEnglish() {
        String englishWord = "Black";
        WordPair testWordPair = new WordPair(englishWord, "Noire");
        assertEquals(englishWord, testWordPair.getEnglishOrFrench("English"));
    }

    @Test
    void getEnglishOrFrenchFrench() {
        String frenchWord = "Jaune";
        WordPair testWordPair = new WordPair("Yellow", frenchWord);
        assertEquals(frenchWord, testWordPair.getEnglishOrFrench("French"));
    }

    //Test for IllegalArgumentException thrown when requesting translation in non support language
    @Test
    void getEnglishOrFrenchException() {
        IllegalArgumentException thrownException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            WordPair testWordPair = new WordPair("Yellow", "Jaune");
            testWordPair.getEnglishOrFrench("Spanish");
        });
    }

    //Test for getEnglishOrFrench with String value of French and boolean value of true
    @Test
    void getEnglishOrFrenchBoolTrueEnglish() {
        String englishWord = "Yellow";
        WordPair testWordPair = new WordPair(englishWord, "Jaune");
        assertEquals(englishWord, testWordPair.getEnglishOrFrench("French", true));
    }

    //Test for getEnglishOrFrench with String value of English and boolean value of true
    @Test
    void getEnglishOrFrenchBoolTrueFrench() {
        String frenchWord = "Jaune";
        WordPair testWordPair = new WordPair("Yellow", frenchWord);
        assertEquals(frenchWord, testWordPair.getEnglishOrFrench("English", true));
    }

    //Test for IllegalArgumentException thrown when non-support language is passed and boolean value true is passed
    @Test
    void getEnglishOrFrenchBooleanException() {

        IllegalArgumentException thrownException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            WordPair testWordPair = new WordPair("Yellow", "Jaune");
            testWordPair.getEnglishOrFrench("Spanish", true);
        });
    }

    //Test for getEnglishOrFrench with boolean value of false
    @Test
    void getEnglishOrFrenchBooleanFalse() {
        String frenchWord = "Jaune";
        WordPair testWordPair = new WordPair("Yellow", frenchWord);
        assertEquals(frenchWord, testWordPair.getEnglishOrFrench("French", false));
    }

    @Test
    void setEnglish() {
        WordPair testWordPair = new WordPair("Black", "Rouge");
        String correctEnglishWord = "Red";
        testWordPair.setEnglish("Red");
        assertEquals(correctEnglishWord, testWordPair.getEnglish());
    }

    @Test
    void setFrench() {
        WordPair testWordPair = new WordPair("Red", "Noire");
        String correctFrenchWord = "Rouge";
        testWordPair.setFrench("Rouge");
        assertEquals(correctFrenchWord, testWordPair.getFrench());
    }
}