package com.echo.wordsudoku.models.Language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.models.language.BoardLanguage;

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
    void getOtherLanguageInvalidInput() {

        try {
            boardLanguage.getOtherLanguage(3);
            // fail since 3 is not a valid language
            fail("IllegalArgumentException not thrown");
        } catch (Exception e) {
            //expected
        }
    }


    // test getOtherLanguage method with valid input (0, 1)
    @Test
    void getOtherLanguageValidInput() {

        try {
            assertEquals(BoardLanguage.ENGLISH, boardLanguage.getOtherLanguage(BoardLanguage.FRENCH));
            assertEquals(BoardLanguage.FRENCH, boardLanguage.getOtherLanguage(BoardLanguage.ENGLISH));

        } catch (Exception e) {
            // fail since both languages are valid
            fail("IllegalArgumentException thrown");
        }
    }


    // test getLanguageName method with invalid input , not 0 or 1
    @Test
    void getLanguageNameInvalidInput() {

        try {
            boardLanguage.getLanguageName(3);
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

        } catch (Exception e) {
            // fail since both languages are valid
            fail("IllegalArgumentException thrown");
        }
    }

    // test isValidLanguage method with invalid input , not 0 or 1
    @Test
    void isLanguageValidInvalidLanguage() {

        assertFalse(boardLanguage.isValidLanguage(3));
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
        assertEquals(BoardLanguage.ENGLISH, boardLanguage.defaultLanguage());
    }

}
