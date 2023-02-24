package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import com.echo.wordsudoku.models.Board.BoardLanguage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardLanguageTest {

    //Test that getOtherLanguage throws exception when entering non-supported language
    @Test
    void testGetOtherLanguageException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BoardLanguage.getOtherLanguage(-10);
        });
    }

    //Test that getOtherLanguage returns English when passed 1 (Value of French)
    @Test
    void testReturnEnglish() {
        assertEquals(0, BoardLanguage.getOtherLanguage(1));
    }

    //Test that getOtherLanguage returns French when passed 0 (Value of English)
    @Test
    void testReturnFrench() {
        assertEquals(1, BoardLanguage.getOtherLanguage(0));
    }

    @Test
    void testGetLanguageNameEnglish() {
        assertEquals("English", BoardLanguage.getLanguageName(0));
    }

    @Test
    void testGetLanguageNameFrench() {
        assertEquals("French", BoardLanguage.getLanguageName(1));
    }

    @Test
    void testGetLanguageNameException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assertEquals("French", BoardLanguage.getLanguageName(-10));
        });

    }



}