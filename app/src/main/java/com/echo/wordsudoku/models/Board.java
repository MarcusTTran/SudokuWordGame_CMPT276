package com.echo.wordsudoku.models;

/**
 *  ========================================= BOARD =========================================
 *  DESCRIPTION OF FIELDS AND FEATURES
    -   the Board class contains 4 parallel arrays 2 of them contain the numerical
        representation [1-N]
        of each word. the board array displays the unsolved puzzle. A similarly structure array will
        contain the solution of the puzzle.
    -   the 2 other array will contain the unsolved word puzzle and the other contain the
        solved word puzzle
    -   to instantiate the class the Board requires a list of 9 WordPairs picked in the
        Activity class.
    -   according to the user's choice, the board language can be either in English or French.
        The opposite of user's chosen language will appear as input for the remaining cells.
    -   according to the difficulty of the game the number of empty cells at the beginning of the
        game can be assigned while the class is being instantiated.
     ========================================= BOARD =========================================
*/

public class Board {
    private int[][] board;
    private int [][] solutions;
    private String[][] displayBoard, displayBoard_Solved;
    private WordPair[] wordPairs;
    private final boolean[][] insertibility;

    // change to enum later
    String board_language, input_language;
    private int numToRemove;
    private int mistakes;
    private int dim;
    private final String ENGLISH = "English";
    private final String FRENCH = "French";

    private int BOX_LENGTH = 3;

    // CONSTRUCTOR
    // EFFECT: makes a 2D array list and adds empty string to each location on list
    public Board(int dim, WordPair[] wordPairs, String board_language, int numToRemove) {
        this.dim = dim;

        // TODO : change this part because later on we want to generate a board of 6x6 or 12x12 we can't use
        this.BOX_LENGTH = (int)Math.sqrt(dim);
        // end TODO

        this.board = new int[dim][dim];
        this.solutions = new int[dim][dim];
        this.displayBoard = new String[dim][dim];
        this.displayBoard_Solved = new String[dim][dim];
        this.wordPairs = wordPairs;
        this.board_language = board_language;
        // Sets the input language to the opposite of the board language
        this.input_language = this.board_language.equals(ENGLISH) ? FRENCH : ENGLISH;


        // Remove the number of cells based on the difficulty
        this.numToRemove = numToRemove;

        this.mistakes = 0;

        // generate the board and displayed board values
        this.generateGame();
        this.insertibility = getInsertTable(this.board,dim,dim);
        this.GenerateWordPuzzle();

        // UNCOMMENT FOR TESTING THE BOARD LAYOUT ON CONSOLE
//        this.printSudoku_int(this.getUnSolvedBoard());
//        this.printSudoku_int(this.getSolvedBoard());
    }


    // This utility method is used to return a 2D array of boolean values which indicate whether a cell is filled or not
    private boolean[][] getInsertTable(int[][] board, int xDimension, int yDimension) {
        boolean[][] insertTable = new boolean[xDimension][yDimension];
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                if (board[i][j] != 0) {
                    insertTable[i][j] = false;
                } else {
                    insertTable[i][j] = true;
                }
            }
        }
        return insertTable;
    }


    // @eakbarib
    // Utility methods for converting the numbers in board int array (which contain sudoku numbers [1-9] (inclusive)) to array-compatible indices [0-8] (inclusive)  and vice versa
    private int convertSudokuNumberToIndex(int x) {
        return x - 1;
    }

    private int convertIndexToSudokuNumber(int x) {
        return x + 1;
    }

    // @eakbarib
    // Utility method for finding the index of a word in the wordPairs array
    private int getAssociatedWordPairIndex(String word) {
        for (int i = 0; i < wordPairs.length; i++) {
            if (wordPairs[i].getEnglishOrFrench(board_language,true).equals(word)) {
                return i;
            }
        }
        return -1;
    }


    // EFFECT: returns the number of mistakes made in the game by the player
    public int getMistakes() {
        return mistakes;
    }

    //@eakbarib
    // Returns the latest board filled with initial words and user-input words
    // Could be used for updating the Sudoku board in the UI
    public String[][] getUnSolvedBoard() {
        return displayBoard;
    }


    // EFFECT: adds a the fre or eng word to the location on the board array
    public void insertWord(int x, int y, String word) {
        if(!insertibility[x][y]) {
            throw new RuntimeException("Cannot insert word in a non-empty cell");
        }
        int input = convertIndexToSudokuNumber(getAssociatedWordPairIndex(word));
        // if the word is not found in the list of word pairs (should never happen)
        if (input == 0) {
            throw new RuntimeException("Word not found in the list of word pairs");
        }
        // set the corresponding board value to the word's index
        board[x][y] = input;
        // set the corresponding display board value to the word
        displayBoard[x][y] = wordPairs[convertSudokuNumberToIndex(input)].getEnglishOrFrench(input_language);
        boolean isSafe = checkValidity(x, y, input);

        if (!isSafe)
            mistakes++;
    }

    // EFFECT: checks for wins
    public boolean checkWin() {

        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                if (board[x][y] != solutions[x][y])
                    return false;
            }
        }
        return true;
    }

    // NOT NEEDED FOR NOW
//    // EFFECTS: check for mistakes
//    public boolean checkMistake() {
//
//        return false;  // stub
//    }

    // EFFECT: prepare a List of Coordinates where the initial positions of the words are
    public void generateGame() {
        // Algorithm
        // 1. Fills the diagonal 3x3 matrices
        // 2. recursively fill the rest of the non-diagonal
        //    for each cell fill them by trying every possible value until
        //    it's valid within (row, col, box)
        // 3. once everything is filled remove n elements randomly to generate a game
        //    based on numToRemove

        completeDiagonal();

        completeRemaining(0, BOX_LENGTH);

        solutions = takeCopy(board, solutions);

        removeCellsByDifficulty(); //after we know the solution
    }

    // EFFECT: generates the word puzzle according to the selected board language
    private void GenerateWordPuzzle() {
        // Simply fill the display board with the corresponding word in the word pair. It is mapping the board int array to the string array
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                if (board[x][y] != 0) {
                    displayBoard[x][y] =
                            wordPairs[convertSudokuNumberToIndex(board[x][y])].getEnglishOrFrench(board_language);
                }
                displayBoard_Solved[x][y] =
                        wordPairs[convertSudokuNumberToIndex(solutions[x][y])].getEnglishOrFrench(input_language);
            }
        }
    }

    // EFFECT: fill the diagonal line in the board
    private void completeDiagonal() {
        for (int k = 0; k < dim; k = k + BOX_LENGTH) {
            completeBox(k, k); // to only fill the diagonal cells (k, k)
        }
    }

    // EFFECT: fill the remaining cells when all diagonals are filled check if the position is safe
    //            before inserting the randomly generated value
    private boolean completeRemaining(int i, int j) {

        if (j >= dim && i < dim - 1)
        {
            i = i + 1;
            j = 0;
        }
        if (j >= dim && i >= dim)
            return true;

        if (i < BOX_LENGTH) {
            if (j < BOX_LENGTH) {
                j = BOX_LENGTH;
            }

        } else if (i < dim - BOX_LENGTH) {

            if (j == (int) (i/ BOX_LENGTH) * BOX_LENGTH) {
                j = j + BOX_LENGTH;
            }
        }
        else
        {
            if (j == dim - BOX_LENGTH) {
                i = i + 1;
                j = 0;
                if (i >= dim)
                    return true;
            }
        }

        for (int num = 1; num <= dim; num++)
        {
            if (checkValidity(i, j, num)) {
                board[i][j] = num;
                if (completeRemaining(i, j + 1)) {
                    return true;
                }

                board[i][j] = 0;
            }
        }
        return false;
    }

    // EFFECT: return true if the same value does not exist in the same BOX, Row, and Column
    //         else false
    private boolean checkValidity(int x, int y, int val) {
        
        return (isNotInRow(x, val) &&
                isNotInCol(y, val) &&
                isNotInBox((x - (x % BOX_LENGTH)), (y - (y % BOX_LENGTH)), val));
    }

    // EFFECT: fill a 3x3 matrix
    private void completeBox(int row, int col) { ///!!!!!!!!!!!!
        int val = generateRandomValue(dim);

        for (int x = 0; x < BOX_LENGTH; x++) {
            for (int y = 0; y < BOX_LENGTH; y++) {

                while (!isNotInBox(row, col, val)) {
                    val = generateRandomValue(dim);
                }

                board[row + x][col + y] = val;
            }
        }
    }

    // EFFECT: check if the same value exists in the box of 3x3
    private boolean isNotInBox(int x_start, int y_start, int val) {

        for (int i = 0; i < BOX_LENGTH; i++) {
            for (int j = 0; j < BOX_LENGTH; j++) {
                if (board[x_start + i][y_start + j] == val)
                    return false;
            }
        }

        return true;
    }

    // EFFECT: check if the save value exists in the same column,
    //         return false if it does, true otherwise
    private boolean isNotInCol(int y, int val) {

        for (int i = 0; i < dim; i++) {
            if (board[i][y] == val)
                return false;
        }

        return true;
    }

    // EFFECT: check if the same value exists in the given row
    private boolean isNotInRow(int x, int val) {
        for (int j = 0; j < dim; j++) {
            if (board[x][j] == val)
                return false;
        }
        return true;
    }

    // EFFECT: remove n digits from the board where n = k_difficulty
    private void removeCellsByDifficulty() {

        int num = numToRemove;

        while (num != 0) {
            int loc = generateRandomValue(dim * dim) - 1;

            int x = loc / dim;
            int y = loc % dim;

            if (y != 0) {
                y = y - 1;
            }

            if (board[x][y] != 0) {
                num--;
                board[x][y] = 0;
            }
        }
    }


    //EFFECT: generate a random number
    private int generateRandomValue(int i) {
        return (int) Math.floor(((Math.random() * i) + 1));
    }

    //EFFECT: prints the board board on console given the array
    public void printSudoku_int(int [][] print_board) {
        System.out.println("PRINTING: ");
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
                System.out.print(print_board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // EFFECT: prints the displayed sudoku containing french and english words
    public void printSudoku_String(String [][] print_board) {
        System.out.println("PRINTING: ");
        for (int x = 0; x < dim; x++)
        {
            for (int y = 0; y < dim; y++)
                System.out.print(print_board[x][y] + " ");
            System.out.println();
        }
        System.out.println();
    }


    // EFFECT: take copy of an array without taking a reference
    private int [][] takeCopy(int [][] copiedFrom, int [][] copiedInto) {
        for (int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                copiedInto[i][j] = copiedFrom[i][j];
            }
        }

        return copiedInto;
    }



        // UNCOMMENT FOR TESTING THE BOARD LAYOUT
//         printSudoku_String(displayBoard);
//         printSudoku_String(displayBoard_Solved);
}
