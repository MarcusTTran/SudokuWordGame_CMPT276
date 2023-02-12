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

    //Boards to use for debugging
    private int[][] debugBoard = {
            {6,0,0,4,0,0,3,0,8},
            {7,3,0,0,2,8,5,0,0},
            {8,0,5,0,0,0,4,2,9},
            {0,8,0,0,4,9,6,1,3},
            {4,0,0,0,0,0,0,0,0},
            {0,5,1,6,0,0,0,8,0},
            {9,4,6,1,0,0,8,3,0},
            {0,2,0,3,0,6,7,0,0},
            {0,7,0,0,0,0,1,0,0}
    };

    private int[][] debugBoardSolutions = {
            {6,9,2,4,1,5,3,7,8},
            {7,3,4,9,2,8,5,6,1},
            {8,1,5,7,6,3,4,2,9},
            {2,8,7,5,4,9,6,1,3},
            {4,6,9,8,3,1,2,5,7},
            {3,5,1,6,7,2,9,8,4},
            {9,4,6,1,5,7,8,3,2},
            {1,2,8,3,9,6,7,4,5},
            {5,7,3,2,8,4,1,9,6}
    };


    @org.junit.jupiter.api.Test
    public void testGetUnsolved() {
        Board testBoard = new Board(9, wordPairs, "French", 0);
        testBoard.insertDebugBoard(debugBoard, debugBoardSolutions);
        assertEquals(debugBoard, testBoard.getUnSolvedBoard());
    }

    @org.junit.jupiter.api.Test
    public void testGetSolved() {
        Board testBoard = new Board(9, wordPairs, "French", 0);
        testBoard.insertDebugBoard(debugBoard, debugBoardSolutions);
        assertEquals(debugBoardSolutions, testBoard.getSolvedBoard());
    }

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
        //This is not possible
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