package com.echo.wordsudoku.models;

import java.util.ArrayList;

public class Board {

    ArrayList<ArrayList<String>> board;

    // constructor
    // EFFECTS: makes a 2D array list and adds empty string to each location on list
    public Board() {
        board = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                board.get(x).add(""); // allocate empty string to replace later
            }
        }
    }

    // EFFECTS: adds a the fre or eng word to the location on the board array
    public void insertWord(int x, int y, WordPair wp) {

    }

    // EFFECTS: checks for wins
    public boolean checkWin() {

        return false; // stub
    }

    // EFFECTS: check for mistakes
    public boolean checkMistake() {

        return false;  // stub
    }


}
