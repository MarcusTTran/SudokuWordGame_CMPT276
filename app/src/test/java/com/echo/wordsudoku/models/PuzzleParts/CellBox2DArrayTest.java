package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.sudoku.CellBox;
import com.echo.wordsudoku.models.sudoku.CellBox2DArray;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CellBox2DArrayTest {


/**
 * Test the CellBox2DArray class
 * @author Kousha Amouzesh
 * @version 1.0
 */

    private Dimension boxes;
    private Dimension cells;

    private ByteArrayOutputStream outStream;

    @BeforeEach
    void setUp() {
        // Test the third constructor
        this.boxes = new Dimension(9, 9);
        this.cells = new Dimension(9, 9);
    }

    @Test
    public void testConstructor1() {
        // Test the first constructor
        CellBox[][] cellBoxes = new CellBox[9][9];

        // Fill the array with CellBoxes
        for (int i = 0; i < cellBoxes.length; i++) {
            for (int j = 0; j < cellBoxes[i].length; j++) {
                cellBoxes[i][j] = new CellBox(new WordPair("e", "f"), 3, 3);
            }
        }

        // Create the CellBox2DArray
        CellBox2DArray cellBox2DArray = new CellBox2DArray(cellBoxes, this.boxes, this.cells);

        assertArrayEquals(cellBoxes, cellBox2DArray.getCellBoxes());
        assertEquals(this.boxes, cellBox2DArray.getBoxDimensions());
        assertEquals(this.cells, cellBox2DArray.getCellDimensions());
    }


    @Test
    public void testConstructorWithBoxesCellsLanguage() {

        // Create the CellBox2DArray
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells, 0);

        // Check that the dimensions are correct
        assertEquals(this.boxes, cellBox2DArray.getBoxDimensions());
        assertEquals(this.cells, cellBox2DArray.getCellDimensions());
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(testAllCellsAreEmpty(cellBox2DArray));
    }


    @Test
    void testConstructorWithPuzzleDimensionLanguage(){
        PuzzleDimensions puzzleDimensions = new PuzzleDimensions(9);
        CellBox2DArray cellBox2DArray = new CellBox2DArray(puzzleDimensions, BoardLanguage.ENGLISH);

        // Check that the dimensions are correct
        assertEquals(puzzleDimensions.getBoxesInPuzzleDimension(), cellBox2DArray.getBoxDimensions());
        assertEquals(puzzleDimensions.getEachBoxDimension(), cellBox2DArray.getCellDimensions());

        // are the cells not null
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(testAllCellsAreEmpty(cellBox2DArray));
    }


    @Test
    void testConstructorBoxesCells() {

        // Create the CellBox2DArray
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);

        // Check that the dimensions are correct
        assertEquals(this.boxes, cellBox2DArray.getBoxDimensions());
        assertEquals(this.cells, cellBox2DArray.getCellDimensions());
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(testAllCellsAreEmpty(cellBox2DArray));
    }


    @Test
    void testConstructorPuzzleDimension() {
        // Create the CellBox2DArray
        PuzzleDimensions puzzleDimensions = new PuzzleDimensions(9);
        CellBox2DArray cellBox2DArray = new CellBox2DArray(puzzleDimensions);

        // Check that the dimensions are correct
        assertEquals(puzzleDimensions.getBoxesInPuzzleDimension(), cellBox2DArray.getBoxDimensions());
        assertEquals(puzzleDimensions.getEachBoxDimension(), cellBox2DArray.getCellDimensions());

        // check puzzle language for one cell the remaining should be the same
        assertEquals(BoardLanguage.ENGLISH, cellBox2DArray.getCellBox(0, 0).getCell(0,0).getLanguage());

        // are the cells not null
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(testAllCellsAreEmpty(cellBox2DArray));
    }



    @Test
    void testConstructorWithCellBox2DArray() {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox2DArray cellBox2DArrayMain = new CellBox2DArray(cellBox2DArray);
        // checked if copied models are equal
        assertTrue(cellBox2DArrayMain.equals(cellBox2DArray));
    }



    @Test
    void testGetCellBoxes() {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox[][] cellBoxes = cellBox2DArray.getCellBoxes();
        // check if the returned array is not null
        assertNotNull(cellBoxes);
        // check if the returned array is the same as the one in the class
        assertArrayEquals(cellBoxes, cellBox2DArray.getCellBoxes());
    }

    @Test
    void testSetCellBoxes() {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox[][] cellBoxes = cellBox2DArray.getCellBoxes();
        cellBoxes[0][0] = new CellBox(new WordPair("e", "f"), 3, 3);
        cellBox2DArray.setCellBoxes(cellBoxes);
        // check if the modified cellBox is the same as the one in the cellBox2DArray
        assertEquals(cellBox2DArray.getCellBox(0, 0), cellBoxes[0][0]);
    }


    @Test
    void testSetCellBoxesOutOfBound() {
        this.boxes = new Dimension(12, 12);
        this.cells = new Dimension(12, 12);
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);


        // add a out of bound cellBox [][] to the array
        CellBox[][] cellBoxes = new CellBox[9][9];


        // Fill the array with CellBoxes
        for (int i = 0; i < cellBoxes.length; i++) {
            for (int j = 0; j < cellBoxes[i].length; j++) {
                cellBoxes[i][j] = new CellBox(new WordPair("e", "f"), 3, 3);
            }
        }



        cellBox2DArray.setCellBoxes(cellBoxes);
        assertNotNull(cellBox2DArray.getCellBoxes());

        // the cellBox2DArray must throw array out of bound exception however this exception
        // is caught within the model method and only the cells within the bounds are filled
        // therefor the cells won't be empty
        assertFalse(testAllCellsAreEmpty(cellBox2DArray));

    }







    // helper method for checking whether all cells are empty or not
    boolean testAllCellsAreEmpty(CellBox2DArray cellBox2DArray){
        for (int i = 0; i < cellBox2DArray.getCellBoxes().length; i++) {
            for (int j = 0; j < cellBox2DArray.getCellBoxes()[i].length; j++) {
                if(!cellBox2DArray.getCellBoxes()[i][j].getCells()[i][j].equals(new Cell())){
                    return false;
                }
            }
        }
        return true;
    }

}
