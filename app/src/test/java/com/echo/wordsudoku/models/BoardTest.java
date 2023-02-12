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

    //Try to insert word not included in wordPair list (should throw exception)
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

    //Try to insert word in non-empty cell (should throw exception)
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

    //Try to enter a word into ALL non-empty cells (should throw N total RuntimeExceptions)
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

        //Make copy of unsolved board to compare to detect for word insertions
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                duplicateStringBoard[i][k] = testBoard.getUnSolvedBoard()[i][k];
            }
        }

        //Index to save which word we inserted into the board
        int index = 0;

        //Search for some empty cell, try to all possible words in wordPair list until insertion is successful, if statement compares duplicate and board to detect that insertion was made since after insertion the board will differs from the original duplicate
        //Once detected save the index of the word that was inserted
        boolean searchingEmpty = true;
        while(searchingEmpty) {
            x_value = new Random().nextInt(max - min + 1) + min;
            y_value = new Random().nextInt(max - min + 1) + min;
            if (testBoard.getUnSolvedBoard()[x_value][y_value] == null) {
                searchingEmpty = false;
                for (int i = 0; i < wordPairs.length; i++) {
                    testBoard.insertWord(x_value, y_value, wordPairs[i].getEnglish());
                    if (!deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
                        index = i;
                    }
                }
            }
        }

        //If the word stored at the empty cell coordinates match that of the index of the wordPair we inserted then the insertion of the correct word was successful
        assertEquals(wordPairs[index].getEnglish(), testBoard.getUnSolvedBoard()[x_value][y_value]);
    }

    //Check number of mistakes in newly created board (should be 0)
    @org.junit.jupiter.api.Test
    public void testGetMistakesNewBoard() {
        Board testBoard = new Board(9, wordPairs, "French", 20);
        int totalMistakes = testBoard.getMistakes();
        assertEquals(0, totalMistakes);
    }

    //Test that board can produces correct number of blank cells with random number
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationRandom() {
        //Generate random number, N, between 81-20
        int max = 65;
        int min = 1;
        int randomNumber = new Random().nextInt(max - min + 1) + min;

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs, "French", randomNumber);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();

        //Find empty cells
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


    // Try to create the largest possible board
    // Not sure about this test, 72 is the maximum largest number acceptable
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

    // Try to create board with no empty cells
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationNone() {

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs, "French", 0);
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
        assertEquals(0, emptyCells);
    }

    // Try to create board with single empty cell
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationOne() {

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

    //Insert word to invalid cell to test incrementation of mistakes
    //Purposefully induce a mistake, I don't think this is possible
    @org.junit.jupiter.api.Test
    public void testInsertWordInvalidCell() {
        Board testBoard = new Board(9, wordPairs, "French", 43);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();
        //Find non-empty space
        int empties = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard[i][k] != null) {

                }
            }
        }

        String[][] duplicateStringBoard = new String[9][9];

        //Make copy of unsolved board to compare to detect for word insertions
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                duplicateStringBoard[i][k] = testBoard.getUnSolvedBoard()[i][k];
            }
        }

        if (deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
            System.out.println("The same");
        } else {
            System.out.println("NOT same");
        }

//        int x = 0;
//        int y = 0;
//        int emptyCells = 0;
//        for (int i = 0; i < 9; i++) {
//            for (int k = 0; k < 9; k++) {
//                if (testStringBoard[i][k] == null) {
//                    x = i;
//                    y = k;
//                }
//            }
//        }

        int min = 0;
        int max = 8;
        int x_value = 0;
        int y_value = 0;

        int countMistakes = 0;

        boolean searchingEmpty = true;
        while(searchingEmpty) {
            x_value = new Random().nextInt(max - min + 1) + min;
            y_value = new Random().nextInt(max - min + 1) + min;
            if (testBoard.getUnSolvedBoard()[x_value][y_value] == null) {
                System.out.println("Found empty cell: ");
                System.out.println("x: " + x_value + "y: " + y_value);
                searchingEmpty = false;
                for (int i = 0; i < wordPairs.length; i++) {
                    testBoard.insertWord(x_value, y_value, wordPairs[i].getEnglish());
                    countMistakes++;
                }
            }
        }

        if (deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
            System.out.println("The same");
        } else {
            System.out.println("NOT same");
        }

        testBoard.getUnSolvedBoard();


        System.out.println(testBoard.getMistakes());

        assertEquals(countMistakes, testBoard.getMistakes());

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
    public void testGenerateGame() {

    }

    //Call checkWin on an unfilled board
    @Test
    public void testCheckWinUnfilledBoard() {
        //checkWin on a puzzle with 1 empty cell
        Board testBoard = new Board(9, wordPairs, "French", 1);
        assertEquals(false, testBoard.checkWin());
    }

    //Call checkWin on a completely full board
    @Test
    public void testCheckWinFullBoard() {
        //checkWin on a puzzle with no empty cells
        Board testBoard = new Board(9, wordPairs, "French", 0);
        assertEquals(true, testBoard.checkWin());
    }

    //Call checkWin on a completely full INCORRECT board
    @Test
    public void testCheckWinIncorrectFullBoard() {
        Board testBoard = new Board(9, wordPairs, "French", 30);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard == null) {
                    testBoard.insertWord(i, k, wordPairs[i].getEnglish());
                }
            }
        }
        assertEquals(false, testBoard.checkWin());
    }

    @org.junit.jupiter.api.Test
    public void testGetMistakesFullBoard() {
        //test getMistakes on an newly created puzzle
        Board testBoard = new Board(9, wordPairs, "French", 0);
        assertEquals(0, testBoard.getMistakes());
    }

    @Test
    public void testConstructorDimensions() {
        int dimensions = 9;
        Board testBoard = new Board(dimensions, wordPairs, "French", 0);
//        for (int i = 0; i < )
//
//        assertEquals(0, testBoard.getMistakes());
    }


    @Test
    void generateGame() {
    }


}