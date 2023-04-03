package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
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
    public void testConstructorBoxesAndLanguage() throws NegativeNumberException {
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
    public void testConstructorWithBoxesCellsLanguage() throws NegativeNumberException {

        // Create the CellBox2DArray
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells, 0);

        // Check that the dimensions are correct
        assertEquals(this.boxes, cellBox2DArray.getBoxDimensions());
        assertEquals(this.cells, cellBox2DArray.getCellDimensions());
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(areAllCellsAreEmpty(cellBox2DArray));

        // check cells language to be English
        assertTrue(checkCellSetAtLanguage(BoardLanguage.ENGLISH, cellBox2DArray));
    }

    // test the constructor with negative para form dimensions
    @Test
    public void testNegativeNumberException() {
        assertThrows(NegativeNumberException.class, () -> {
            new CellBox2DArray(new Dimension(-1, 1), new Dimension(1, 1), 0);
        });
    }


    @Test
    void testConstructorWithPuzzleDimensionLanguage() throws IllegalDimensionException, NegativeNumberException {
        PuzzleDimensions puzzleDimensions = new PuzzleDimensions(9);
        CellBox2DArray cellBox2DArray = new CellBox2DArray(puzzleDimensions, BoardLanguage.ENGLISH);

        // Check that the dimensions are correct
        assertEquals(puzzleDimensions.getBoxesInPuzzleDimension(), cellBox2DArray.getBoxDimensions());
        assertEquals(puzzleDimensions.getEachBoxDimension(), cellBox2DArray.getCellDimensions());

        // are the cells not null
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(areAllCellsAreEmpty(cellBox2DArray));
    }


    @Test
    void testConstructorBoxesCells() throws NegativeNumberException {

        // Create the CellBox2DArray
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);

        // Check that the dimensions are correct
        assertEquals(this.boxes, cellBox2DArray.getBoxDimensions());
        assertEquals(this.cells, cellBox2DArray.getCellDimensions());
        assertNotNull(cellBox2DArray.getCellBoxes());

        // Check that the cells are empty
        assertTrue(areAllCellsAreEmpty(cellBox2DArray));
    }


    @Test
    void testConstructorPuzzleDimension() throws IllegalDimensionException, NegativeNumberException {
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
        assertTrue(areAllCellsAreEmpty(cellBox2DArray));
    }



    @Test
    void testConstructorWithCellBox2DArray() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox2DArray cellBox2DArrayMain = new CellBox2DArray(cellBox2DArray);
        // checked if copied models are equal
        assertTrue(cellBox2DArrayMain.equals(cellBox2DArray));
    }



    @Test
    void testGetCellBoxes() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox[][] cellBoxes = cellBox2DArray.getCellBoxes();
        // check if the returned array is not null
        assertNotNull(cellBoxes);
        // check if the returned array is the same as the one in the class
        assertArrayEquals(cellBoxes, cellBox2DArray.getCellBoxes());
    }

    @Test
    void testSetCellBoxes() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox[][] cellBoxes = cellBox2DArray.getCellBoxes();
        cellBoxes[0][0] = new CellBox(new WordPair("e", "f"), 3, 3);
        cellBox2DArray.setCellBoxes(cellBoxes);
        // check if the modified cellBox is the same as the one in the cellBox2DArray
        assertEquals(cellBox2DArray.getCellBox(0, 0), cellBoxes[0][0]);
    }


    @Test
    void testSetCellBoxesOutOfBound() throws NegativeNumberException {
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
        assertFalse(areAllCellsAreEmpty(cellBox2DArray));

    }


    @Test
    void testGetSetBoxDimension() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        Dimension dimension = new Dimension(4, 4);
        cellBox2DArray.setBoxDimensions(dimension);
        assertTrue(dimension.equals(cellBox2DArray.getBoxDimensions()));
    }

    @Test
    void testGetSetCellDimension() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        Dimension dimension = new Dimension(4, 4);
        cellBox2DArray.setCellDimensions(dimension);
        assertTrue(dimension.equals(cellBox2DArray.getCellDimensions()));
    }

    @Test
    void testGetCellBox() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox cellBox = cellBox2DArray.getCellBox(0, 0);
        assertNotNull(cellBox);
        assertEquals(cellBox, cellBox2DArray.getCellBoxes()[0][0]);
    }


    // test index out of bound exception with get Cell box
    @Test
    void testGetCellBoxOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(10, 10));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(11, 11));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(-1, -1));
    }


    @Test
    void testGetCellBoxWithDimension() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox cellBox = cellBox2DArray.getCellBox(new Dimension(1,1));
        assertNotNull(cellBox);
        assertEquals(cellBox, cellBox2DArray.getCellBoxes()[0][0]);
    }

    @Test
    void testGetCellBoxWithDimensionOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(new Dimension(9, 9)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(new Dimension(11, 11)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> cellBox2DArray.getCellBox(new Dimension(-1, -1)));
    }



    @Test
    void testGetCellFromBigArray() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        Cell cellFirst = cellBox2DArray.getCellFromBigArray(0, 0);
        Cell cellLast = cellBox2DArray.getCellFromBigArray(8, 8);
        assertNotNull(cellFirst);
        assertNotNull(cellLast);
        assertEquals(cellFirst, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0]);
        assertEquals(cellLast, cellBox2DArray.getCellBoxes()[0][0].getCells()[8][8]);

    }


    @Test
    void testGetCellFromBigArrayOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.getCellFromBigArray(81, 81));
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.getCellFromBigArray(-1, -1));
    }


    @Test
    void testGetCellsFromBigArrayWithDimension() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        Cell cellFirst = cellBox2DArray.getCellFromBigArray(new Dimension(0, 0));
        Cell cellLast = cellBox2DArray.getCellFromBigArray(new Dimension(8, 8));
        assertNotNull(cellFirst);
        assertNotNull(cellLast);
        assertEquals(cellFirst, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0]);
        assertEquals(cellLast, cellBox2DArray.getCellBoxes()[0][0].getCells()[8][8]);

    }

    @Test
    void getCellsFromBigArrayOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.getCellFromBigArray(new Dimension(81, 81)));
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.getCellFromBigArray(new Dimension(-1, -1)));
    }


    @Test
    void testSetCellsFromBigArray() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");
        cellBox2DArray.setCellFromBigArray(0, 0, wordPair);
        assertEquals(wordPair, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0].getContent());
    }

    @Test
    void testSetCellFromBigArrayOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.setCellFromBigArray(81, 81, wordPair));
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.setCellFromBigArray(-1, -1, wordPair));
    }


    @Test
    void testSetCellFromBigArrayWithDimension() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");
        cellBox2DArray.setCellFromBigArray(new Dimension(0, 0), wordPair);
        assertEquals(wordPair, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0].getContent());
    }


    @Test
    void testSetCellFromBigArrayWithDimensionOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.setCellFromBigArray(new Dimension(81, 81), wordPair));
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.setCellFromBigArray(new Dimension(-1, -1), wordPair));
    }

    @Test
    void testSetCellClearFromBigArray() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");

        // set cell to wordPair content
        cellBox2DArray.setCellFromBigArray(0, 0, wordPair);
        assertEquals(wordPair, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0].getContent());

        // clear the cell of the same coordinate
        cellBox2DArray.clearCellFromBigArray(0,0);

        // the cell should be like this after clear() is called
        Cell clearedCell = new Cell(null, true, BoardLanguage.ENGLISH, true);
        assertEquals(clearedCell, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0]);
    }


    @Test
    void testSetCellClearFromBigArrayOutOfBound() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        WordPair wordPair = new WordPair("test", "test");

        // set cell to wordPair content
        cellBox2DArray.setCellFromBigArray(0, 0, wordPair);
        assertEquals(wordPair, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0].getContent());

        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.clearCellFromBigArray(81, 81));
        assertThrows(IndexOutOfBoundsException.class, () -> cellBox2DArray.clearCellFromBigArray(-1, -1));

        // but now try with valid coordinate
        cellBox2DArray.clearCellFromBigArray(0,0);


        Cell clearedCell = new Cell(null, true, BoardLanguage.ENGLISH, true);
        assertEquals(clearedCell, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0]);

    }


    @Test
    void testSetCellClearFromBigArrayWhenAlreadyCleared() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        cellBox2DArray.clearCellFromBigArray(0,0);
        Cell clearedCell = new Cell(null, true, BoardLanguage.ENGLISH, true);

        // the cell should be cleared regardless and no change will happen to the content in the cell
        assertEquals(clearedCell, cellBox2DArray.getCellBoxes()[0][0].getCells()[0][0]);
    }

    @Test
    void testGetColumnsRows() throws NegativeNumberException {
        // 9 x 9
        CellBox2DArray cellBox2DArrayNine = new CellBox2DArray(this.boxes, this.cells);
        assertEquals(81,  cellBox2DArrayNine.getColumns());
        assertEquals(81,  cellBox2DArrayNine.getRows());

        // 12 x 12
        CellBox2DArray cellBox2DArrayTwelve = new CellBox2DArray(new Dimension(12, 12), new Dimension(12, 12));
        assertEquals(144,  cellBox2DArrayTwelve.getColumns());
        assertEquals(144,  cellBox2DArrayTwelve.getRows());

        // 6 x 6
        CellBox2DArray cellBox2DArraySix = new CellBox2DArray(new Dimension(6, 6), new Dimension(6, 6));
        assertEquals(36,  cellBox2DArraySix.getColumns());
        assertEquals(36,  cellBox2DArraySix.getRows());

        // 4 x 4
        CellBox2DArray cellBox2DArrayFour = new CellBox2DArray(new Dimension(4, 4), new Dimension(4, 4));
        assertEquals(16,  cellBox2DArrayFour.getColumns());
        assertEquals(16,  cellBox2DArrayFour.getRows());
    }


    @Test
    void testLanguage() throws NegativeNumberException {
        CellBox2DArray cellBox2DArrayEnglish = new CellBox2DArray(this.boxes, this.cells);
        assertTrue(checkCellSetAtLanguage(BoardLanguage.ENGLISH, cellBox2DArrayEnglish));
        CellBox2DArray cellBox2DArrayFrench = new CellBox2DArray(this.boxes, this.cells, BoardLanguage.FRENCH);
        assertTrue(checkCellSetAtLanguage(BoardLanguage.FRENCH, cellBox2DArrayFrench));
    }


    boolean checkCellSetAtLanguage(int language, CellBox2DArray cellBox2DArray){
        
        for (int i = 0; i < cellBox2DArray.getCellBoxes().length; i++) {
            for (int j = 0; j < cellBox2DArray.getCellBoxes().length; j++) {
                if (cellBox2DArray.getCellBox(i, j).getCell(i,j).getLanguage() != language){
                    return false;
                }
            }
        }
        return true;
    }


    @Test
    void testIsFilled() throws NegativeNumberException {
        CellBox2DArray cellBox2DArrayFilled = new CellBox2DArray(this.boxes, this.cells);
        CellBox2DArray cellBox2DArrayNotFilled = new CellBox2DArray(this.boxes, this.cells);

        // fill all cells
        for (int x = 0; x < cellBox2DArrayFilled.getRows(); x++) {
            for (int y = 0; y < cellBox2DArrayFilled.getColumns(); y++) {
                // leave one empty for one of the cell boxes
                if (y != 80) {
                    cellBox2DArrayNotFilled.setCellFromBigArray(x, y, new WordPair("test", "test"));
                }
                cellBox2DArrayFilled.setCellFromBigArray(x, y, new WordPair("test", "test"));
            }
        }

        // check filled and not filled cellBoxes
        assertTrue(cellBox2DArrayFilled.isFilled());
        assertFalse(cellBox2DArrayNotFilled.isFilled());
    }


    @Test
    void testEquals() throws NegativeNumberException {
        CellBox2DArray cellBox2DArray = new CellBox2DArray(this.boxes, this.cells);
        CellBox2DArray cellBox2DArrayCopy = new CellBox2DArray(this.boxes, this.cells);
        CellBox2DArray notEqualCellBox2DArray = new CellBox2DArray(this.boxes, this.cells);

        // fill all cells with same content
        cellBox2DArray = fillAllCells(cellBox2DArray);
        cellBox2DArrayCopy = fillAllCells(cellBox2DArrayCopy);
        // check if the two cellBox2DArray are equal
        assertTrue(cellBox2DArray.equals(cellBox2DArrayCopy));
        assertFalse(cellBox2DArray.equals(notEqualCellBox2DArray));
    }

    CellBox2DArray fillAllCells(CellBox2DArray cellBox2DArray){
        for (int x = 0; x < cellBox2DArray.getRows(); x++) {
            for (int y = 0; y < cellBox2DArray.getColumns(); y++) {
                cellBox2DArray.setCellFromBigArray(x, y, new WordPair("test", "test"));
            }
        }
        return cellBox2DArray;
    }



    // helper method for checking whether all cells are empty or not
    boolean areAllCellsAreEmpty(CellBox2DArray cellBox2DArray){
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
