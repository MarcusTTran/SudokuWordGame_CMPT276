package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.*;

import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.sudoku.CellBox;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit test for CellBox
 * @version 1.0
 */
class CellBoxTest {

    private CellBox cellBox;
    private WordPair wordPair;
    @BeforeEach
    public void setUp() {
        this.wordPair = new WordPair("english", "french");
        this.cellBox = new CellBox(wordPair, 3, 3);
    }

    @Test
    public void testGetCells() {
        assertNotNull(cellBox.getCells());
        assertEquals(cellBox.getCells().length, 3);
        assertEquals(cellBox.getCells()[0].length, 3);
    }

    @Test
    public void testSetCells() {
        Cell[][] newCells = new Cell[3][3];

        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[i].length; j++) {
                newCells[i][j] = new Cell(this.wordPair);
            }
        }

        cellBox.setCells(newCells);

        assertArrayEquals(cellBox.getCells(), newCells);
    }




    @Test
    public void testIsContaining() {
        assertTrue(this.cellBox.isContaining(this.wordPair));
        assertFalse(this.cellBox.isContaining(new WordPair("e", "f")));
    }


    @Test
    public void testGetCell() {
        assertNotNull(this.cellBox.getCell(0, 0));
        assertNotNull(this.cellBox.getCell(2, 2));

        try {
            this.cellBox.getCell(3, 3);
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }

    }

    @Test
    public void testSetCell() {
        // Initialize a 2x2 CellBox with default language
        CellBox cellBox = new CellBox(new WordPair("eng", "fre"), 2, 2);

        // Initialize new 2D Cell array
        Cell[][] newCells = new Cell[][]{
                {new Cell(this.wordPair, 1), new Cell(this.wordPair, 1)},
                {new Cell(this.wordPair, 1), new Cell(this.wordPair, 1)}
        };

        // Set new cells to CellBox
        cellBox.setCells(newCells);

        // Check if new cells are set correctly
        assertArrayEquals(newCells, cellBox.getCells());


    }

    @Test
    public void testSetCellUnequalSize() {

        // Initialize a 2x2 CellBox with default language
        CellBox cellBox = new CellBox(new WordPair("eng", "fre"), 2, 2);

        // Initialize new 3x3 Cell array
        Cell[][] newCells2 = new Cell[][]{
                {new Cell(this.wordPair, 1), new Cell(this.wordPair, 1), new Cell(this.wordPair, 1)},
                {new Cell(this.wordPair, 1), new Cell(this.wordPair, 1), new Cell(this.wordPair, 1)},
                {new Cell(this.wordPair, 1), new Cell(this.wordPair, 1), new Cell(this.wordPair, 1)}
        };

        // Set new cells to CellBox
        try {
            cellBox.setCells(newCells2);
            fail("IllegalArgumentException not thrown");
            // Check if new cells are set correctly
            assertArrayEquals(newCells2, cellBox.getCells());
        } catch (IllegalArgumentException e) {
            // expected
        }


    }


    @Test
    public void testSetCellsLanguage() {
        // Create a cell box with 2x2 dimensions and fill it with words
        this.wordPair = new WordPair("hello", "bonjour");
        CellBox cellBox = new CellBox(wordPair, 2, 2);

        // Set the language to English
        cellBox.setCellsLanguage(0);

        // Assert that all cells have the correct language
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(cellBox.getCell(i, j).getLanguage(), 0);
            }
        }

        // Set the language to Spanish
        cellBox.setCellsLanguage(1);

        // Assert that all cells have the correct language
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(cellBox.getCell(i, j).getLanguage(), 1);
            }
        }
    }



}
