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
        assertEquals(englishWord, testWordPair.getLang1());
    }

    @Test
    void getFrench() {
        String frenchWord = "Aqua";
        WordPair testWordPair = new WordPair("Water", frenchWord);
        assertEquals(frenchWord, testWordPair.getLang2());
    }

    @Test
    void setEnglish() {
        WordPair testWordPair = new WordPair("Black", "Rouge");
        String correctEnglishWord = "Red";
        testWordPair.setLang1("Red");
        assertEquals(correctEnglishWord, testWordPair.getLang1());
    }

    @Test
    void setFrench() {
        WordPair testWordPair = new WordPair("Red", "Noire");
        String correctFrenchWord = "Rouge";
        testWordPair.setLang2("Rouge");
        assertEquals(correctFrenchWord, testWordPair.getLang2());
    }
}