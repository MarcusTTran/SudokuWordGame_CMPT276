package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;

import org.junit.jupiter.api.Test;

/**
 * Unit test for PuzzleDimensions
 * @version 1.0
 */

public class PuzzleDimensionTest {

    @Test
    void testConstructorValidInput() throws IllegalDimensionException {
        // Test valid input
        PuzzleDimensions pd = new PuzzleDimensions(4);
        assertEquals(4, pd.getPuzzleDimension());
        assertEquals(new Dimension(2, 2), pd.getEachBoxDimension());
        assertEquals(new Dimension(2, 2), pd.getBoxesInPuzzleDimension());
    }


    @Test
    void testConstructorWithPuzzleDimension() throws IllegalDimensionException {
        PuzzleDimensions pd = new PuzzleDimensions(6);
        PuzzleDimensions copy = new PuzzleDimensions(pd);
        assertEquals(pd, copy);

    }

    @Test
    void testConstructorWithDimensionEachBoxDimensionBoxesInPuzzleDimension() {
        PuzzleDimensions pd = new PuzzleDimensions(6, new Dimension(2, 3), new Dimension(3, 2));
        assertEquals(6, pd.getPuzzleDimension());
        assertTrue(new Dimension(2, 3).equals(pd.getEachBoxDimension()));
        assertTrue(new Dimension(3, 2).equals(pd.getBoxesInPuzzleDimension()));
    }



    @Test
    void testConstructorInvalidInput() {
        // Test invalid input
        assertThrows(IllegalArgumentException.class, () -> new PuzzleDimensions(5));
        assertThrows(IllegalArgumentException.class, () -> new PuzzleDimensions(7));
    }


    @Test
    void testGetBoxesInPuzzleDimension() throws IllegalDimensionException {
        PuzzleDimensions pdSix = new PuzzleDimensions(6);
        assertEquals(new Dimension(3, 2), pdSix.getBoxesInPuzzleDimension());
        PuzzleDimensions pdTwelve = new PuzzleDimensions(12);
        assertEquals(new Dimension(3, 4), pdTwelve.getEachBoxDimension());
    }


    @Test
    void testGetEachBoxDimension() throws IllegalDimensionException {
        PuzzleDimensions pdSix = new PuzzleDimensions(6);
        assertEquals(new Dimension(2, 3), pdSix.getEachBoxDimension());
        PuzzleDimensions pdTwelve = new PuzzleDimensions(12);
        assertEquals(new Dimension(3, 4), pdTwelve.getEachBoxDimension());
    }




    @Test
    void testSetPuzzleDimension() throws IllegalDimensionException {
        // Test setting valid puzzle dimensions
        PuzzleDimensions pd = new PuzzleDimensions(4);
        pd.setPuzzleDimension(6);
        assertEquals(6, pd.getPuzzleDimension());
        assertEquals(new Dimension(2, 3), pd.getEachBoxDimension());
        assertEquals(new Dimension(3, 2), pd.getBoxesInPuzzleDimension());

        pd.setPuzzleDimension(9);
        assertEquals(9, pd.getPuzzleDimension());
        assertEquals(new Dimension(3, 3), pd.getEachBoxDimension());
        assertEquals(new Dimension(3, 3), pd.getBoxesInPuzzleDimension());

        pd.setPuzzleDimension(12);
        assertEquals(12, pd.getPuzzleDimension());
        assertEquals(new Dimension(3, 4), pd.getEachBoxDimension());
        assertEquals(new Dimension(4, 3), pd.getBoxesInPuzzleDimension());

        // Test setting invalid puzzle dimension
        assertThrows(IllegalArgumentException.class, () -> pd.setPuzzleDimension(5));
        assertThrows(IllegalArgumentException.class, () -> pd.setPuzzleDimension(7));
    }



    @Test
    void testEquals() throws IllegalDimensionException {
        PuzzleDimensions pd1 = new PuzzleDimensions(6);
        PuzzleDimensions pd2 = new PuzzleDimensions(6);
        PuzzleDimensions pd3 = new PuzzleDimensions(9);

        // Check that pd1 is equal to itself
        assertTrue(pd1.equals(pd1));

        // Check that pd1 is equal to pd2 and pd2 is equal to pd1
        assertTrue(pd1.equals(pd2));
        assertTrue(pd2.equals(pd1));

        // Check that pd1 is not equal to pd3 and pd3 is not equal to pd1
        assertFalse(pd1.equals(pd3));
        assertFalse(pd3.equals(pd1));

    }



}
