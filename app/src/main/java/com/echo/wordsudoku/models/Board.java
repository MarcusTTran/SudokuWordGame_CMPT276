package com.echo.wordsudoku.models;

import java.util.ArrayList;

public class Board {
    private int[][] board;

    private String[][] displayBoard;
    private WordPair[] wordPairs;
    private int [][] solutions;
    private ArrayList<Coord> sampleCoordinates;

    // change to enum later
    String language;
    private final int GRID_SIZE = 9;
    private final String ENGLISH = "English";
    private final String FRENCH = "French";

    // constructor
    // EFFECTS: makes a 2D array list and adds empty string to each location on list
    public Board(WordPair[] wordPairs, String language) {
        this.board = new int[GRID_SIZE][GRID_SIZE];
        this.wordPairs = wordPairs;
        this.sampleCoordinates = new ArrayList<>();
        this.language = language;

        generateSampleCoordinates();
    }

    // EFFECTS: adds a the fre or eng word to the location on the board array
    public void insertWord(int x, int y, WordPair wordPair, String language) {

    }

    // EFFECTS: checks for wins
    public boolean checkWin() {

        return false; // stub
    }

    // EFFECTS: check for mistakes
    public boolean checkMistake() {

        return false;  // stub
    }

    // EFFECTS: prepare a List of Coordinates where the initial positions of the words are
    private void generateSampleCoordinates() {

    }



}
