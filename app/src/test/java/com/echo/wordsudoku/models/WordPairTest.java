package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordPairTest {

    //Test getEnglish
    @Test
    void getEnglish() {
        String englishWord = "Water";
        WordPair testWordPair = new WordPair(englishWord, "Aqua");
        assertEquals(englishWord, testWordPair.getEnglish());
    }

    //Test getFrench
    @Test
    void getFrench() {
        String frenchWord = "Aqua";
        WordPair testWordPair = new WordPair("Water", frenchWord);
        assertEquals(frenchWord, testWordPair.getFrench());
    }

    //Test getFrenchOrFrench English scenario
    @Test
    void getEnglishOrFrenchEnglish() {
        String englishWord = "Black";
        WordPair testWordPair = new WordPair(englishWord, "Noire");
        assertEquals(englishWord, testWordPair.getEnglishOrFrench(0));
    }

    //Test getFrenchOrFrench French scenario
    @Test
    void getEnglishOrFrenchFrench() {
        String frenchWord = "Jaune";
        WordPair testWordPair = new WordPair("Yellow", frenchWord);
        assertEquals(frenchWord, testWordPair.getEnglishOrFrench(1));
        assertEquals("Yellow", testWordPair.getEnglishOrFrench(0));
    }

    //Test for IllegalArgumentException thrown when requesting translation in non-supported language
    @Test
    void getEnglishOrFrenchException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            WordPair testWordPair = new WordPair("Yellow", "Jaune");
            testWordPair.getEnglishOrFrench(2);
        });
    }

    //Test setEnglish
    @Test
    void setEnglish() {
        WordPair testWordPair = new WordPair("Black", "Rouge");
        String correctEnglishWord = "Red";
        testWordPair.setEnglish("Red");
        assertEquals(correctEnglishWord, testWordPair.getEnglish());
    }

    //Test setFrench
    @Test
    void setFrench() {
        WordPair testWordPair = new WordPair("Red", "Noire");
        String correctFrenchWord = "Rouge";
        testWordPair.setFrench("Rouge");
        assertEquals(correctFrenchWord, testWordPair.getFrench());
    }
}