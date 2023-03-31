package com.echo.wordsudoku.models.Util;

import static org.junit.Assert.*;

import com.echo.wordsudoku.models.utility.MathUtils;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test for MathUtils
 * @author Kousha Amouzesh
 * @version 1.0
*/

public class MathUtilsTest {

    @Test
    public void testIsPerfectSquare() {
        assertTrue(MathUtils.isPerfectSquare(4));
        assertTrue(MathUtils.isPerfectSquare(9));
        assertTrue(MathUtils.isPerfectSquare(16));
        assertFalse(MathUtils.isPerfectSquare(5));
        assertFalse(MathUtils.isPerfectSquare(7));
        assertFalse(MathUtils.isPerfectSquare(10));
    }

    @Test
    public void testIsPrimeNumber() {
        assertFalse(MathUtils.isPrimeNumber(0));
        assertFalse(MathUtils.isPrimeNumber(1));
        assertTrue(MathUtils.isPrimeNumber(2));
        assertTrue(MathUtils.isPrimeNumber(3));
        assertFalse(MathUtils.isPrimeNumber(4));
        assertTrue(MathUtils.isPrimeNumber(5));
        assertFalse(MathUtils.isPrimeNumber(6));
        assertTrue(MathUtils.isPrimeNumber(7));
        assertFalse(MathUtils.isPrimeNumber(8));
        assertFalse(MathUtils.isPrimeNumber(9));
        assertFalse(MathUtils.isPrimeNumber(10));
    }

    @Test
    public void testGetRandomNumberBetweenIncluding() {
        int num = 100;
        int min = 1;
        int max = 100;
        for (int i = 0; i < num; i++) {
            int random = MathUtils.getRandomNumberBetweenIncluding(min, max);
            assertTrue(random >= min);
            assertTrue(random <= max);
        }
    }

    @Test
    public void testGetMiddleFactors() {
        assertArrayEquals(new int[]{2, 3}, MathUtils.getMiddleFactors(6));
        assertArrayEquals(new int[]{3, 4}, MathUtils.getMiddleFactors(12));
        assertArrayEquals(new int[]{7, 8}, MathUtils.getMiddleFactors(56));
        assertArrayEquals(new int[]{9, 10}, MathUtils.getMiddleFactors(90));
    }

    @Test
    public void testGenerateRandomIndexes() {
        int size = 5;
        int min = 1;
        int max = 10;
        List<Integer> indexes = MathUtils.generateRandomIndexes(size, max, min);
        assertEquals(size, indexes.size());
        for (int index : indexes) {
            assertTrue(index >= min);
            assertTrue(index <= max);
        }
    }
}
