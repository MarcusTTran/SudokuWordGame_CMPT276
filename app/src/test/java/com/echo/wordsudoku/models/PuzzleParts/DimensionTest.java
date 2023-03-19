package com.echo.wordsudoku.models.PuzzleParts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.utility.MathUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the Dimension class
 * @author Kousha Amouzesh
 * @version 1.0
 *
 */

public class DimensionTest {

    private Dimension dimensionWithRowAndColumn;
    private Dimension dimensionWithDimension;


    // Makes a random dimension with the given puzzle size
    public static Dimension makeRandomDimension(int size) {
        return new Dimension(MathUtils.getRandomNumberBetweenIncluding(0, size-1), MathUtils.getRandomNumberBetweenIncluding(0, size-1));
    }

    @BeforeEach
    void setUp() {
        this.dimensionWithRowAndColumn = new Dimension(4, 5);
        this.dimensionWithDimension = new Dimension(new Dimension(4,5));
    }

    @Test
    void testGetRow() {
        assertEquals(4, dimensionWithRowAndColumn.getRows());
        assertEquals(4, dimensionWithDimension.getRows());
    }

    @Test
    void testGetColumn() {
        assertEquals(5, dimensionWithRowAndColumn.getColumns());
        assertEquals(5, dimensionWithDimension.getColumns());
    }


    @Test
    void testEquals() {
        assertTrue(dimensionWithRowAndColumn.equals(dimensionWithDimension));
        assertFalse(dimensionWithDimension.equals(new Dimension(5, 4)));
    }

    @Test
    void testSetters() {
        Dimension dimension = new Dimension(4, 5);
        dimension.setRows(5);
        dimension.setColumns(4);
        assertEquals(5, dimension.getRows());
        assertEquals(4, dimension.getColumns());
    }

    @Test
    void setArrayDimension() {
        int[] arrayDimension = new int[2];
        arrayDimension[0] = 4;
        arrayDimension[1] = 5;
        Dimension dimension = new Dimension(arrayDimension);
        assertEquals(4, dimension.getRows());
        assertEquals(5, dimension.getColumns());
    }


    @Test
    void setInvalidArrayDimension() {
        int[] arrayDimension = new int[3];
        arrayDimension[0] = 4;
        arrayDimension[1] = 5;
        arrayDimension[2] = 6;
        try {
            Dimension dimension = new Dimension(arrayDimension);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            //expected
        }

    }

}
