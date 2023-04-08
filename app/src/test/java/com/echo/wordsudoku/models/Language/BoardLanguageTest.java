package com.echo.wordsudoku.models.Language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardLanguageTest {

    //test BoardLanguage class

    private BoardLanguage boardLanguage;


    @BeforeEach
    void runBefore() {
        //set up
        this.boardLanguage = new BoardLanguage();
    }


    // test getLanguageName method with invalid input , not 0 or 1
    @Test
    void getLanguageNameInvalidInput() {

        try {
            boardLanguage.getLanguageName(7);
            fail("IllegalArgumentException not thrown");
        } catch (Exception e) {
            //expected
        }
    }

    // test getLanguageName method with valid input (0, 1)
    @Test
    void getLanguageNameValidInput() {

        try {
            assertEquals("English", boardLanguage.getLanguageName(BoardLanguage.ENGLISH));
            assertEquals("French", boardLanguage.getLanguageName(BoardLanguage.FRENCH));

        } catch (IllegalLanguageException e) {
            // fail since both languages are valid
            fail("IllegalArgumentException thrown");
        }
    }

    // test isValidLanguage method with invalid input , not 0 or 1
    @Test
    void isLanguageValidInvalidLanguage() {
        assertFalse(boardLanguage.isValidLanguage(-1));

    }


    // test isValidLanguage method with valid input (0, 1)
    @Test
    void isLanguageValidValidInput() {

        assertEquals(true, boardLanguage.isValidLanguage(BoardLanguage.ENGLISH));
        assertEquals(true, boardLanguage.isValidLanguage(BoardLanguage.FRENCH));

    }


    // test defaultLanguage method
    @Test
    void defaultLanguage() {
        // default language is English
        assertEquals(WordPair.LANG1, boardLanguage.defaultLanguage());
    }

}
