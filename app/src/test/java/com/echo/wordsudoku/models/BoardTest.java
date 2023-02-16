package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.Arrays.deepEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BoardTest {

    //Sample set of wordPairs to use, use ONLY for 9x9 Sudoku
    private final WordPair[] wordPairs9x9 = {
            new WordPair("Water", "Aqua"),
            new WordPair("Red", "Rouge"),
            new WordPair("Black", "Noire"),
            new WordPair("Yellow", "Jaune"),
            new WordPair("Yes", "Oui"),
            new WordPair("No", "Non"),
            new WordPair("He", "Il"),
            new WordPair("She", "Elle"),
            new WordPair("They", "Ils")
    };

    private final WordPair[] wordPairs12x12 = {
            new WordPair("Water", "Aqua"),
            new WordPair("Red", "Rouge"),
            new WordPair("Black", "Noire"),
            new WordPair("Yellow", "Jaune"),
            new WordPair("Yes", "Oui"),
            new WordPair("No", "Non"),
            new WordPair("He", "Il"),
            new WordPair("She", "Elle"),
            new WordPair("They", "Ils"),
            new WordPair("Fish", "Peche"),
            new WordPair("Dog", "Chien"),
            new WordPair("Cat", "Chat")
    };
    private final WordPair[] wordPairs6x6 = {
            new WordPair("Water", "Aqua"),
            new WordPair("Red", "Rouge"),
            new WordPair("Black", "Noire"),
            new WordPair("Yellow", "Jaune"),
            new WordPair("Yes", "Oui"),
            new WordPair("No", "Non"),
    };


    //9x9 test board used for debugging to validate Board class logic (checkValidity)
    private final int[][] testBoard9x9 = {
            {0,7,0,0,2,0,0,4,6},
            {0,6,0,0,0,0,8,9,0},
            {2,0,0,8,0,0,7,1,5},
            {0,8,4,0,9,7,0,0,0},
            {7,1,0,0,0,0,0,5,9},
            {0,0,0,1,3,0,4,8,0},
            {6,9,7,0,0,2,0,0,8},
            {0,5,8,0,0,0,0,6,0},
            {4,3,0,0,8,0,0,7,0}
    };
    //9x9 test board solutions
    private final int[][] testBoard9x9Solution = {
             {8,7,5,9,2,1,3,4,6},
             {3,6,1,7,5,4,8,9,2},
             {2,4,9,8,6,3,7,1,5},
             {5,8,4,6,9,7,1,2,3},
             {7,1,3,2,4,8,6,5,9},
             {9,2,6,1,3,5,4,8,7},
             {6,9,7,4,1,2,5,3,8},
             {1,5,8,3,7,9,2,6,4},
             {4,3,2,5,8,6,9,7,1}
    };

    //Test creating a board given too many word pairs
    @org.junit.jupiter.api.Test
    public void testBoardWithTooManyWordPairs() {
        int dimensions = 9;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(dimensions, wordPairs12x12, 1, 0);
        });
    }

    //Test creating a board with too few word pairs
    @org.junit.jupiter.api.Test
    public void testBoardWithTooFewWordPairs() {
        int dimensions = 9;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(dimensions, wordPairs6x6, 1, 0);
        });
    }

    //Test creating a board in a Non-supported language
    @org.junit.jupiter.api.Test
    public void testBoardNonSupportedLanguage() {
        int dimensions = 9;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(dimensions, wordPairs9x9, 10, 0);
        });
    }

    //Tests that Board's constructor correctly produces Board with specified dimensions
    @org.junit.jupiter.api.Test
    public void testConstructorDimensions() {
        int dimensions = 9;
        Board testBoard = new Board(dimensions, wordPairs9x9, 1, 0);
        assertEquals(dimensions, testBoard.getUnSolvedBoard()[0].length);
    }


    //Create 50 boards and test that they are all unique
    @org.junit.jupiter.api.Test
    public void testBoardUniqueness() {
        int numberOfBoards = 50;
        Board[] boardArray = new Board[numberOfBoards];

        for (int i = 0; i < numberOfBoards; i++) {
            boardArray[i] = new Board(9, wordPairs9x9, 1, 30);
        }
        boolean duplicates = false;
        for (int i = 0; i < numberOfBoards; i++) {
            for (int k = 0; k < numberOfBoards; k++) {
                //As long as the 2 boards we're comparing aren't the same ones
                if (k != i) {
                    if (deepEquals(boardArray[i].getUnSolvedBoard(), boardArray[k].getUnSolvedBoard())) {
                        duplicates = true;
                    }
                }
            }
        }
        assertFalse(duplicates);
    }



    // Test that Board throws exception when trying to produce board with less than 17 cells
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(9, wordPairs9x9, 1, 80);
        });
    }


    // Try to create Board with no empty cells
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationNone() {

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
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
        Board testBoard = new Board(9, wordPairs9x9, 1, 1);
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

    //Test that Board can produce correct number of blank cells with a given random number between 64 and 20
    @org.junit.jupiter.api.Test
    public void testBlankCellGenerationRandom() {
        //Generate random number, N, between 81-20
        int max = 64;
        int min = 20;
        int randomNumber = new Random().nextInt(max - min + 1) + min;

        //Generate board with that N empty spaces
        Board testBoard = new Board(9, wordPairs9x9, 1, randomNumber);
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


    // Try to call checkWin on an unfilled board
    @org.junit.jupiter.api.Test
    public void testCheckWinUnfilledBoard() {
        //checkWin on a puzzle with 1 empty cell
        Board testBoard = new Board(9, wordPairs9x9, 1, 1);
        assertFalse(testBoard.checkWin());
    }

    // Try to call checkWin on a completely full board
    @org.junit.jupiter.api.Test
    public void testCheckWinFullBoard() {
        //checkWin on a puzzle with no empty cells
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
        assertTrue(testBoard.checkWin());
    }

    // Try to call checkWin on a completely full INCORRECT board
    @org.junit.jupiter.api.Test
    public void testCheckWinIncorrectFullBoard() {
        Board testBoard = new Board(9, wordPairs9x9, 1, 30);
        String[][] testStringBoard = testBoard.getUnSolvedBoard();
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testStringBoard == null) {
                    testBoard.insertWord(i, k, wordPairs9x9[0].getEnglish());
                }
            }
        }
        assertFalse(testBoard.checkWin());
    }


    //Try to insert word not included in wordPair list (should throw exception)
    @org.junit.jupiter.api.Test
    public void testInsertWordMissingWord() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            //Create some new Board
            Board testBoard = new Board(9, wordPairs9x9, 1, 20);
            //Variables to save co-ords
            int x = 0;
            int y = 0;

            //Find empty cell
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 9; k++) {
                    if (testBoard.getUnSolvedBoard()[i][k] == null) {
                        x = i;
                        y = k;
                    }
                }
            }

            //Try to insert word not included in wordPair list
            testBoard.insertWord(x, y, "WORD_NOT_IN_LIST");
        });

    }

    //Try to insert word in permanent cell (should throw exception)
    @org.junit.jupiter.api.Test
    public void testInsertWordPermanentCell() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            //Remove a random number of cells
            int max = 64;
            int min = 1;
            int randomNumber = new Random().nextInt(max - min + 1) + min;
            Board testBoard = new Board(9, wordPairs9x9, 1, randomNumber);

            //Find random empty cell
            int x_value = 0;
            int y_value = 0;
            boolean searchingNonEmpty = true;
            while(searchingNonEmpty) {
                x_value = new Random().nextInt(9);
                y_value = new Random().nextInt(9);
                if (testBoard.getUnSolvedBoard()[x_value][y_value] != null) {
                    searchingNonEmpty = false;
                }
            }

            testBoard.insertWord(x_value, y_value, "Water");
        });
    }

    //Try to insert a new word into a non-empty non-permanent cell that we have previously filled
    @org.junit.jupiter.api.Test
    public void testInsertWordNonEmptyCell() {
        //Remove a random number of cells
        int max = 64;
        int min = 1;
        int randomNumber = new Random().nextInt(max - min + 1) + min;
        Board testBoard = new Board(9, wordPairs9x9, 1, randomNumber);

        //Find a random empty cell
        int x_value = 0;
        int y_value = 0;
        boolean searchingEmpty = true;
        while(searchingEmpty) {
            x_value = new Random().nextInt(9);
            y_value = new Random().nextInt(9);
            if (testBoard.getUnSolvedBoard()[x_value][y_value] == null) {
                searchingEmpty = false;
            }
        }

        //Insert a word into an empty cell, then insert a new word into the same cell
        boolean firstInsert = false;
        boolean secondInsert = false;
        boolean bothWorking = false;
        testBoard.insertWord(x_value, y_value, "Water");
        if (testBoard.getUnSolvedBoard()[x_value][y_value].equals("Water")) {
            firstInsert = true;
        }
        testBoard.insertWord(x_value, y_value, "Yellow");
        if (testBoard.getUnSolvedBoard()[x_value][y_value].equals("Yellow")) {
            secondInsert = true;
        }
        if (firstInsert == true && secondInsert == true) {
            bothWorking = true;
        }
        assertTrue(bothWorking);
    }

    //Try to enter a word into ALL non-empty cells (should throw N total RuntimeExceptions)
    @org.junit.jupiter.api.Test
    public void testInsertWordNonEmptyCellRepeated() {
        int max = 64;
        int min = 1;
        int numberOfCellsToRemove = new Random().nextInt(max - min + 1) + min;
        int totalCellsIn9x9Board = 81;

        Board testBoard = new Board(9, wordPairs9x9, 1, numberOfCellsToRemove);
        //Variables to save co-ords

        //Find some non-empty cell
        int totalExceptions = 0;
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                if (testBoard.getUnSolvedBoard()[i][k] != null) {
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

    //Test if the word selected to be inserted by user is the one inserted into the board
    @org.junit.jupiter.api.Test
    public void testInsertWordCorrectString() {
        //Generate random number, N, between 81-20
        int x_value = 0;
        int y_value = 0;

        Board testBoard = new Board(9, wordPairs9x9, 1, 45);
        String[][] duplicateStringBoard = new String[9][9];

        //Make copy of unsolved board to compare to detect for word insertions
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                duplicateStringBoard[i][k] = testBoard.getUnSolvedBoard()[i][k];
            }
        }

        //Index to save which word we inserted into the board
        int index = 0;

        //Search for some empty cell, try to insert all possible words in the wordPair list until insertion
        // is successful, the if statement compares duplicate and board to detect that insertion was made
        // since after insertion the board will differs from the original duplicate
        //Once detected save the index of the word that was inserted
        boolean searchingEmpty = true;
        while(searchingEmpty) {
            x_value = new Random().nextInt(9);
            y_value = new Random().nextInt(9);
            if (testBoard.getUnSolvedBoard()[x_value][y_value] == null) {
                searchingEmpty = false;
                for (int i = 0; i < wordPairs9x9.length; i++) {
                    testBoard.insertWord(x_value, y_value, wordPairs9x9[i].getEnglish());
                    if (!deepEquals(testBoard.getUnSolvedBoard(), duplicateStringBoard)) {
                        index = i;
                    }
                }
            }
        }

        //If the word stored at the empty cell coordinates match that of the index of the wordPair we inserted then the insertion of the correct word was successful
        assertEquals(wordPairs9x9[index].getEnglish(), testBoard.getUnSolvedBoard()[x_value][y_value]);
    }

    //Check number of mistakes in newly created board (should be 0)
    @org.junit.jupiter.api.Test
    public void testGetMistakesNewBoard() {
        int dim = 9;
        Board testBoard = new Board(dim, wordPairs9x9, 1, 20);
        int totalMistakes = testBoard.getMistakes();
        assertEquals(0, totalMistakes);

    }



    //Create a board, feed it in a test board along with solutions using createDebugBoard
    //Then make 3 mistakes to make sure getMistakes is correctly incremented
    @org.junit.jupiter.api.Test
    public void testIncrementOfMistakes() {
        Board testBoard = Board.createDebugBoard(wordPairs9x9, testBoard9x9, testBoard9x9Solution);
        testBoard.insertWord(0,0, wordPairs9x9[1].getEnglish());
        testBoard.insertWord(0,2, wordPairs9x9[6].getEnglish());
        testBoard.insertWord(1,0, wordPairs9x9[5].getEnglish());
        assertEquals(3, testBoard.getMistakes());
    }

    //Test checkValidity and getMistakes are working correctly when trying to enter a number already in the row
    @org.junit.jupiter.api.Test
    public void testCheckValidityRowInvalid() {
        Board testBoard = Board.createDebugBoard(wordPairs9x9, testBoard9x9, testBoard9x9Solution);
        testBoard.insertWord(0,6, wordPairs9x9[1].getEnglish());
        assertEquals(1, testBoard.getMistakes());
    }

    //Test checkValidity and getMistakes are working correctly when trying to enter a number already in the column
    @org.junit.jupiter.api.Test
    public void testCheckValidityColInvalid() {
        Board testBoard = Board.createDebugBoard(wordPairs9x9, testBoard9x9, testBoard9x9Solution);
        testBoard.insertWord(8,3, wordPairs9x9[3].getEnglish());
        assertEquals(1, testBoard.getMistakes());
    }

    //Test  checkValidity and getMistakes are working correctly when trying to enter a number already in the sub-box
    @org.junit.jupiter.api.Test
    public void testCheckValiditySubBoxInvalid() {
        Board testBoard = Board.createDebugBoard(wordPairs9x9, testBoard9x9, testBoard9x9Solution);
        testBoard.insertWord(3,6, wordPairs9x9[4].getEnglish());
        assertEquals(1, testBoard.getMistakes());
    }

    //Test the method that allows for creation of a debug board
    @org.junit.jupiter.api.Test
    public void testDebugBoardInsert() {
        Board testBoard = Board.createDebugBoard(wordPairs9x9, testBoard9x9, testBoard9x9Solution);
        //Compare that the board the Board uses is the same as the testBoard
        assertTrue(deepEquals(testBoard.getBoard(), testBoard9x9));
    }



    //Test getMistakes on a Board that is generated with no empty cells
    @org.junit.jupiter.api.Test
    public void testGetMistakesFullBoard() {
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
        assertEquals(0, testBoard.getMistakes());
    }


    //Test the constructor correctly produces Board with English words
    @org.junit.jupiter.api.Test
    public void testConstructorEnglishLanguage() {
        Board testBoard = new Board(9, wordPairs9x9, 0, 0);
        String[][] stringTestBoard = testBoard.getUnSolvedBoard();
        String someEnglishWord = stringTestBoard[0][0];
        boolean languageCheck = false;
        for (int i = 0; i < wordPairs9x9.length; i++) {
            if (wordPairs9x9[i].getEnglish().equals(someEnglishWord)) {
                languageCheck = true;
                break;
            }
        }
        assertTrue(languageCheck);
    }

    //Test the constructor correctly produces board with French words
    @org.junit.jupiter.api.Test
    public void testConstructorFrenchLanguage() {
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
        String[][] stringTestBoard = testBoard.getUnSolvedBoard();
        String someFrenchWord = stringTestBoard[0][0];
        boolean languageCheck = false;
        for (int i = 0; i < wordPairs9x9.length; i++) {
            if (wordPairs9x9[i].getFrench().equals(someFrenchWord)) {
                languageCheck = true;
                break;
            }
        }
        assertTrue(languageCheck);
    }


    //Test the constructor correctly produces board with ALL French words in the given wordPair list
    @org.junit.jupiter.api.Test
    public void testAllFrenchWordsIncluded() {
        List<String> allFrenchPairs = new ArrayList<>();
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
        String[][] stringTestBoard = testBoard.getUnSolvedBoard();

        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                for (int g = 0; g < wordPairs9x9.length; g++) {
                    if (stringTestBoard[i][k].equals(wordPairs9x9[i].getFrench()) && // French word is same as in solution boar
                            !allFrenchPairs.contains(wordPairs9x9[i].getFrench())) // Not already in allFrenchPairs list
                    {
                        // Add to list of french words
                        allFrenchPairs.add(wordPairs9x9[i].getFrench());
                    }
                }
            }
        }
        // Expected: 9 french words in allFrenchPairs
        assertEquals(wordPairs9x9.length, allFrenchPairs.size());
    }

    //Test the constructor correctly produces board with ALL English words in the given wordPair list
    @org.junit.jupiter.api.Test
    public void testAllEnglishWordsIncluded() {
        List<String> allFrenchPairs = new ArrayList<>();
        Board testBoard = new Board(9, wordPairs9x9, 1, 0);
        String[][] stringTestBoard = testBoard.getUnSolvedBoard();

        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                for (int g = 0; g < wordPairs9x9.length; g++) {
                    if (stringTestBoard[i][k].equals(wordPairs9x9[i].getFrench()) && !allFrenchPairs.contains(wordPairs9x9[i].getFrench())) {
                        allFrenchPairs.add(wordPairs9x9[i].getFrench());
                    }
                }
            }
        }
        assertEquals(wordPairs9x9.length, allFrenchPairs.size());
    }

}