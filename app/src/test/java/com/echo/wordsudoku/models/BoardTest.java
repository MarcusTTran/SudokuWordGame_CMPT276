package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

    //Sample set of wordPairs to use
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


    @Test
    public void testCheckWinUnfilledBoard() {
        //Sample test
        Board testBoard = new Board(9, wordPairs, "French", 1);
        assertEquals(false, testBoard.checkWin());
    }

//    @Test
//    void getUnSolvedBoard() {
//    }
//
//    @Test
//    void getSolvedBoard() {
//    }
//
//    @Test
//    void getMistakes() {
//    }
//
//    @Test
//    void insertWord() {
//    }
//
//    @Test
//    void checkWin() {
//    }
//
//    @Test
//    void generateGame() {
//    }
//
//    @Test
//    void printSudoku_int() {
//    }
//
//    @Test
//    void printSudoku_String() {
//    }
}