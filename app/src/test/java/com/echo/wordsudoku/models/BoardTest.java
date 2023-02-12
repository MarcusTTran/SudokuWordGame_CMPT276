package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.Arrays.deepEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class BoardTest {

    //Sample set of wordPairs to use, use ONLY for 9x9 Sudokus
    private WordPair[] wordPairs = {
            new WordPair("water", "aqua"),
            new WordPair("red", "rouge"),
            new WordPair("black", "noire"),
            new WordPair("yellow", "jaune"),
            new WordPair("yes", "oui"),
            new WordPair("no", "non"),
            new WordPair("he", "il"),
            new WordPair("she", "elle"),
            new WordPair("they", "ils")
    };

    //Try to insert word not included in wordPair list
    @org.junit.jupiter.api.Test
    public void testInsertWordMissingWord() {

        RuntimeException[] runtimeArray;

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            Board testBoard = new Board(9, wordPairs, "French", 20);
            String[][] stringBoard = testBoard.getUnSolvedBoard();
            //Variables to save coords
            int x = 0;
            int y = 0;

            //Find empty space
            int empties = 0;
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 9; k++) {
                    if (stringBoard[i][k] == null) {
                        x = i;
                        y = k;
                    }
                }
            }

            //Try to insert word not in wordPair list
            testBoard.insertWord(x, y, "UNDEFINED");
        });

    }

    //Try to insert word in non-empty cell
    @org.junit.jupiter.api.Test
    public void testInsertWordNonEmptyCell() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            Board testBoard = new Board(9, wordPairs, "French", 20);
            String[][] stringBoard = testBoard.getUnSolvedBoard();
            //Variables to save coords
            int x = 0;
            int y = 0;

            //Find non-empty space
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 9; k++) {
                    if (stringBoard[i][k] != null) {
                        x = i;
                        y = k;
                    }
                }
            }

            testBoard.insertWord(x, y, "water");
        });
    }

    //Test that trying to enter a word into all non-empty cells correctly throws a RuntimeException
    @org.junit.jupiter.api.Test
    public void testInsertWordNonEmptyCellRepeated() {
        int numberOfCellsToRemove = 20;
        int totalCellsIn9x9Board = 81;

        Board testBoard = new Board(9, wordPairs, "French", 20);
        String[][] stringBoard = testBoard.getUnSolvedBoard();
        //Variables to save coords
        int x = 0;
        int y = 0;

        //Find some non=empty cell
        int totalExceptions = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (stringBoard[i][k] != null) {
                    try {
                        testBoard.insertWord(i, k, "Water");
                    } catch(RuntimeException e) {
                        totalExceptions++;
                    }
                }
            }
        }

        int numberOfExpectedExceptions = totalCellsIn9x9Board - numberOfCellsToRemove;
        assertEquals(numberOfExpectedExceptions, totalExceptions);
    }

    
    //Test if the word selected to be inserted by user is correctly inserted into the board
    @org.junit.jupiter.api.Test
    public void testInsertWordCorrectString() {
        //Generate random number, N, between 81-20
        int max = 8;
        int min = 0;

        int x_value = 0;
        int y_value = 0;

        Board testBoard = new Board(9, wordPairs, "French", 45);
        String[][] duplicateStringBoard = new String[9][9];

        //Make copy of unsolved board
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                duplicateStringBoard[i][k] = testBoard.getUnSolvedBoard()[i][k];
            }
        }


        int index = 0;

        boolean searchingEmpty = true;
        while(searchingEmpty) {
            x_value = new Random().nextInt(max - min + 1) + min;
            y_value = new Random().nextInt(max - min + 1) + min;
            if (testBoard.getUnSolvedBoard()[x_value][y_value] == null) {
                searchingEmpty = false;
                for (int i = 0; i < wordPairs.length; i++) {
                    testBoard.insertWord(x_value, y_value, wordPairs[i].getEnglish());
                    if (!deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
                        System.out.println("Word inserted");
                        index = i;
                    }
                }
            }
        }

        System.out.println("Index of word inserted: " + index);
        if (deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
            System.out.println("Boards are the same");
        } else {
            System.out.println("Boards are NOT the same");
        }

        assertEquals(wordPairs[index].getEnglish(), testBoard.getUnSolvedBoard()[x_value][y_value]);
    }

    //Check number of mistakes in newly created board
    @org.junit.jupiter.api.Test
    public void testGetMistakesNewBoard() {
        Board testBoard = new Board(9, wordPairs, "French", 20);
        int totalMistakes = testBoard.getMistakes();
        assertEquals(0, totalMistakes);
    }

    //Test that board can produce correct number of blank cells
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationRandom() {
        //Generate random number, N, between 81-20
        int max = 65;
        int min = 1;
        int randomNumber = new Random().nextInt(max - min + 1) + min;

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs, "French", randomNumber);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();
        String[][] duplicateStringBoard = new String[9][9];

        //Find some empty cell
        int totalEmptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard[i][k] == null) {
                    totalEmptyCells++;
                }
            }
        }

        assertEquals(randomNumber, totalEmptyCells);
    }






    //Not sure about this one, 72 is the maximum largest number acceptable
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationMaximum() {

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs, "French", 72);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();

        //Count up empty spaces
        int emptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard[i][k] == null) {
                    emptyCells++;
                }
            }
        }
        assertEquals(72, emptyCells);
    }

    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationMinimum() {

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs, "French", 1);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();

        //Count up empty spaces
        int emptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard[i][k] == null) {
                    emptyCells++;
                }
            }
        }
        assertEquals(1, emptyCells);
    }

    //Insert word to invalid spot to test incrementation of mistakes
    @org.junit.jupiter.api.Test
    public void testInsertWordInvalidCell() {
        Board testBoard = new Board(9, wordPairs, "French", 1);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();
        //Find non-empty space
        int empties = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard[i][k] != null) {
                }
            }
        }
    }







//    @org.junit.jupiter.api.Test
//    public void testBlankCellsGeneration() {
//        //Generate random number, N, with value between dim * dim
//        //Generate new Board with N empty cells
//        //Check how many empty cells there are
//        //If empty cells = N, then true.
//        int dim = 9;
//        int min = 0;
//        int max = dim * dim;
//
//        int randomNumber = new Random().nextInt(max - min + 1) + min;
//
//        Board testBoard = new Board(dim, wordPairs, "French", randomNumber);
//        String[][] emptyCellTester = testBoard.getUnSolvedBoard();
//
//        int totalEmptyCells = 0;
//        for (int i = 0; i < emptyCellTester.length; i++) {
//            for (int j = 0; j < emptyCellTester.length; j++) {
//                if (emptyCellTester[i][j] == null) {
//                    totalEmptyCells++;
//                }
//            }
//        }
//        assertEquals(randomNumber, totalEmptyCells);
//    }

//    @Test
//    public void testMinimumBlanksPuzzleGeneration() {
//        //Test if Puzzle can generate puzzle with minimum amount of blanks
//        //Tests removeCellsByDifficulty()
//
//        //Not working for some reason
//        Board testBoard = new Board(9, wordPairs, "French", someNum);
//        String[][] emptyCellTester = testBoard.getUnSolvedBoard();
//        int totalEmptyCells = 0;
//        for (int i = 0; i < emptyCellTester.length; i++) {
//            for (int j = 0; j < emptyCellTester.length; j++) {
//                if (emptyCellTester[i][j] == null) {
//                    totalEmptyCells = totalEmptyCells + totalEmptyCells;
//                }
//            }
//        }
//        assertEquals(81, totalEmptyCells);
//
//    }

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