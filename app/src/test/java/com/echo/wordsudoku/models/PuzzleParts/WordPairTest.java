package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.jupiter.api.Assertions.*;

import com.echo.wordsudoku.models.TestUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class WordPairTest {

    public static List<WordPair> makeRandomWordPairList(int size) {
        List<WordPair> wordPairList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            wordPairList.add(new WordPair(TestUtils.makeRandomEnglishWord(), TestUtils.makeRandomFrenchWord()));
        }
        return wordPairList;
    }

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
        assertEquals(englishWord, testWordPair.getEnglishOrFrench(0));
    }

    @Test
    void getEnglishOrFrenchFrench() {
        String frenchWord = "Jaune";
        WordPair testWordPair = new WordPair("Yellow", frenchWord);
        assertEquals(frenchWord, testWordPair.getEnglishOrFrench(1));
        assertEquals("Yellow", testWordPair.getEnglishOrFrench(0));
    }

    //Test for IllegalArgumentException thrown when requesting translation in non support language
    @Test
    void getEnglishOrFrenchException() {
        IllegalArgumentException thrownException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            WordPair testWordPair = new WordPair("Yellow", "Jaune");
            testWordPair.getEnglishOrFrench(2);
        });
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