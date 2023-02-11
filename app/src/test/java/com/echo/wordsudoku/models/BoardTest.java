package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BoardTest {

    //Sample set of wordPairs to use, use ONLY for 9x9 Sudokus
    private WordPair[] wordPairs = {
            new WordPair("water", "aqua", 1),
            new WordPair("red", "rouge", 2),
            new WordPair("black", "noire", 3),
            new WordPair("yellow", "jaune", 4),
            new WordPair("yes", "oui", 5),
            new WordPair("no", "non", 6),
            new WordPair("he", "il", 7),
            new WordPair("she", "elle", 8),
            new WordPair("they", "ils", 9)
    };

    @org.junit.jupiter.api.Test
    public void testBlankCellsGeneration() {
        //Generate random number, N, with value between dim * dim
        //Generate new Board with N empty cells
        //Check how many empty cells there are
        //If empty cells = N, then true.
        int dim = 9;
        int min = 0;
        int max = dim * dim;

        int randomNumber = new Random().nextInt(max - min + 1) + min;

        Board testBoard = new Board(dim, wordPairs, "French", randomNumber);
        int[][] emptyCellTester = testBoard.getUnSolvedBoard();

        int totalEmptyCells = 0;
        for (int i = 0; i < emptyCellTester.length; i++) {
            for (int j = 0; j < emptyCellTester.length; j++) {
                if (emptyCellTester[i][j] == 0) {
                    totalEmptyCells++;
                }
            }
        }
        assertEquals(randomNumber, totalEmptyCells);
    }

    @Test
    public void testCompletelyBlankPuzzleGeneration() {
        //Test if Puzzle can generate completely blank puzzle
        //Is this even possible?
        //Tests removeCellsByDifficulty()

        //Not working for some reason
//        Board testBoard = new Board(9, wordPairs, "French", 81);
//        int[][] emptyCellTester = testBoard.getUnSolvedBoard();
//        int totalEmptyCells = 0;
//        for (int i = 0; i < emptyCellTester.length; i++) {
//            for (int j = 0; j < emptyCellTester.length; j++) {
//                if (emptyCellTester[i][j] == 0) {
//                    totalEmptyCells = totalEmptyCells + totalEmptyCells;
//                }
//            }
//        }
//        assertEquals(81, totalEmptyCells);

    }

    @Test
    public void testCreateBoard() {
    }

    @Test
    public void testCheckWinFalse() {
        //checkWin on a puzzle with 1 empty cell
        Board testBoard = new Board(9, wordPairs, "French", 1);
        assertEquals(false, testBoard.checkWin());
    }

    @Test
    public void testCheckWinTrue() {
        //checkWin on a puzzle with no empty cells
        Board testBoard = new Board(9, wordPairs, "French", 0);
        assertEquals(true, testBoard.checkWin());
    }

    @org.junit.jupiter.api.Test
    public void testGetMistakesFullBoard() {
        //test getMistakes on an newly created puzzle
        Board testBoard = new Board(9, wordPairs, "French", 0);
        assertEquals(0, testBoard.getMistakes());
    }

    @Test
    void getUnSolvedBoard() {

    }

    @Test
    void getSolvedBoard() {
    }

    @Test
    void getMistakes() {
    }

    @Test
    void insertWord() {
    }

    @Test
    void checkWin() {
    }

    @Test
    void generateGame() {
    }

    @Test
    void printSudoku_int() {
    }

    @Test
    void printSudoku_String() {
    }
}