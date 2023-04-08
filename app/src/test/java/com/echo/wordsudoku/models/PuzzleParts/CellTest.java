package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellTest {

/**
 * Test the Cell class
 * @author Kousha Amouzesh
 * @version 1.0
 */

    private Cell cellWithContentLanguage;
    private Cell cellWithContentLanguageAndEditable;
    private Cell cellWithContentLanguageAndIsEditableIsEmpty;
    private Cell cellWithCell; // copy constructor


    private Cell cellWithContent;
    private Cell cellWithLanguage;
    private Cell cellNoParams;


    private WordPair content;

    private final int ENGLISH = WordPair.LANG1;
    private final int FRENCH = WordPair.LANG2;

    @BeforeEach
    void setUp () {
        this.content = new WordPair("eng", "fre");
    }

    @Test
    void testContentLanguageConstructor() {
        this.cellWithContentLanguage = new Cell(content, ENGLISH);

        assertThrows(NullPointerException.class, () -> new Cell(null, ENGLISH));
        assertTrue(this.cellWithContentLanguage.getContent().equals(this.content));
        assertEquals(this.cellWithContentLanguage.getLanguage(), ENGLISH);
        assertFalse(this.cellWithContentLanguage.isEmpty());
    }



    @Test
    void testContentIsEditableLanguageConstructor() {
        this.cellWithContentLanguageAndEditable = new Cell(content, false, FRENCH);
        assertFalse(this.cellWithContentLanguageAndEditable.isEditable());

    }


    @Test
    void testClear() {
        this.cellWithContent = new Cell(this.content);
        assertTrue(this.cellWithContent.getContent().equals(this.content));
        assertFalse(this.cellWithContent.isEmpty());
        this.cellWithContent.clear();
        assertNull(this.cellWithContent.getContent());
        assertTrue(this.cellWithContent.isEmpty());

    }


    @Test
    void testContentIsEditableLanguageIsEmptyConstructor() {
        this.cellWithContentLanguageAndIsEditableIsEmpty = new Cell(this.content, true, ENGLISH, false);
        assertTrue(this.cellWithContentLanguageAndIsEditableIsEmpty.getContent().equals(this.content));
        assertTrue(this.cellWithContentLanguageAndIsEditableIsEmpty.isEditable());
        assertFalse(this.cellWithContentLanguageAndIsEditableIsEmpty.isEmpty());
        assertEquals(this.cellWithContentLanguageAndIsEditableIsEmpty.getLanguage(), ENGLISH);

    }


    @Test
    void testCellWithCellConstructor() {
        this.cellWithContentLanguage = new Cell(content, ENGLISH);
        this.cellWithCell = new Cell(this.cellWithContentLanguage);
        assertTrue(cellWithCell.getContent().equals(this.content));
        assertEquals(cellWithCell.getLanguage(), ENGLISH);
        assertFalse(cellWithCell.isEmpty());
        assertTrue(cellWithCell.isEditable());
    }


    @Test
    void testCellLanguageConstructor() {
        this.cellWithLanguage = new Cell(ENGLISH);
        assertTrue(this.cellWithLanguage.isEmpty());
        assertTrue(this.cellWithLanguage.isEditable());
        assertNull(this.cellWithLanguage.getContent());
        assertEquals(this.cellWithLanguage.getLanguage(), ENGLISH);
    }


    @Test
    void testCellWithContentConstructor() {
        this.cellWithContent = new Cell(this.content);
        assertTrue(this.cellWithContent.getContent().equals(this.content));
        assertTrue(this.cellWithContent.isEditable());
        assertFalse(this.cellWithContent.isEmpty());
        assertEquals(this.cellWithContent.getLanguage(), ENGLISH);
    }


    @Test
    void testCellNoParamsConstructor() {
        this.cellNoParams = new Cell();
        assertNull(this.cellNoParams.getContent());
        assertTrue(this.cellNoParams.isEditable());
        assertTrue(this.cellNoParams.isEmpty());
        assertEquals(this.cellNoParams.getLanguage(), ENGLISH);
    }

    @Test
    void testGetContent() {
        this.cellWithContent = new Cell(this.content);
        assertTrue(cellWithContent.getContent().equals(this.content));
    }

    @Test
    void testSetContent() {
        this.cellNoParams = new Cell();
        this.cellNoParams.setContent(this.content);
        assertTrue(cellNoParams.getContent().equals(this.content));

        assertThrows(NullPointerException.class, () -> this.cellNoParams.setContent(null));
    }

    @Test
    void testSetLanguage() {
        this.cellWithContent = new Cell(this.content);
        assertEquals(this.cellWithContent.getLanguage(), ENGLISH);
        this.cellWithContent.setLanguage(FRENCH);
        assertEquals(this.cellWithContent.getLanguage(), FRENCH);
    }

    @Test
    void testSetLanguageInvalid() {
            this.cellWithContent = new Cell(this.content);
            assertEquals(this.cellWithContent.getLanguage(), ENGLISH);
            assertThrows(IllegalArgumentException.class, () -> this.cellWithContent.setLanguage(3));

    }


    @Test
    void testEquals() {
        Cell one = new Cell(this.content);
        Cell two = new Cell(this.content);
        Cell three = new Cell(new WordPair("e", "f"));

        assertTrue(one.equals(two));
        assertFalse(one.equals(three));
    }

    @Test
    void testIsEqual() {
        this.cellNoParams = new Cell();
        Cell cellWithDifferentConent = new Cell (new WordPair("e", "f"));
        Cell cellWithContentSame = new Cell(this.content);
        this.cellWithContent = new Cell(this.content);

        assertFalse(cellWithContent.isContentEqual(cellWithDifferentConent));
        assertTrue(cellWithContentSame.isContentEqual(cellWithContent));
    }














}
