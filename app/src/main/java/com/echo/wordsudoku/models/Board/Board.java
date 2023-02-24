package com.echo.wordsudoku.models.Board;

import com.echo.wordsudoku.models.Words.WordPair;

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

    //LogCat tag used for debugging
    private static final String TAG = "Board";

    // integer based board that is updated as new word is inserted
    private int[][] board;
    // the complete int based board
    private int [][] solutions;
    // displayBoard is updated relative to board field
    // displayBoard_Solved is the solution string board
    private String[][] displayBoard, displayBoard_Solved;
    //array of wordPairs used in the puzzle
    private WordPair[] wordPairs;

    // This 2D array is a constant which holds the values of the cells which are allowed to be filled.
    // Cells which are not allowed to be filled are marked as false.
    // The cells that are initially filled when the game is started cannot be changed and this
    // 2D array is used to keep track of those.
    // Marcus' Comment: Maybe in the future iterations, we could make this an enum (filled, open, filled_at_start)
    // insertAllowedInBoard is non-final to allow insertion of dummy-
    //insertAllowedInBoard to test Sudoku generation logic
    private final boolean[][] insertAllowedInBoard;

    // change to enum later
    private int board_language, input_language;
    // number of cells to hide for generating a puzzle
    private int numToRemove;
    // count the number of mistakes made in solving the puzzle
    private int mistakes;
    // dimension of the board is terms of dim x dim
    private int dim;

    private int BOX_LENGTH = 3;

    // CONSTRUCTOR
    // @param: dim: int representing the dimension (eg.9) of the puzzle
    //         wordPairs: list of wordsPairs used in puzzle
    //         board_language: string representing the language of the board
    //         numToRemove: number of cells remove for the initial state of the game
    // EFFECT: makes a 2D array list and adds empty string to each location on list
    public Board(int dim, WordPair[] wordPairs, int board_language, int numToRemove) {

        //Prevent user from making 9x9 board that is unsolvable
        // 17 cells is the minimum num of cells needed for 9x9
        checkMinNumberCellsValid(dim, numToRemove);

        //Prevent users from making a board where dimensions do not match length of wordPair list
        checkWordPairDimension(dim, wordPairs);


        this.dim = dim;

        // All Sudoku with unique solutions must have at least 17 clues
        // TODO : change this part because later on we want to generate a
        //        board of 6x6 or 12x12 we can't use

        this.BOX_LENGTH = (int)Math.sqrt(dim);
        // end TODO

        // instantiate the 4 2d array representing the board using the given Dimension
        this.board = new int[dim][dim];
        this.solutions = new int[dim][dim];
        this.displayBoard = new String[dim][dim];
        this.displayBoard_Solved = new String[dim][dim];

        // array of wordPairs
        this.wordPairs = wordPairs;
        // assign board language
        this.board_language = board_language;

        // Sets the input language to the opposite of the board language
        this.input_language = BoardLanguage.getOtherLanguage(board_language);

        // Remove the number of cells based on the difficulty
        this.numToRemove = numToRemove;

        this.mistakes = 0;

        // Generate the board and displayed board values
        this.generateGame();

        // Fill a 2d array of boolean values which indicates whether a cell is permanently filled or not
        // It is used to keep track of which cells are able to be filled and which are not (the cells which are initially filled are not allowed to be filled).
        this.insertAllowedInBoard = getInsertTable(this.board,dim,dim);
        this.GenerateWordPuzzle();

    }


    // EFFECT: Static factory method used to create debug boards for testing;
    //         Allows  for insertion of dummy boards and solutions; used to test Sudoku logic
    // @param  dim - The dimension of the board.
    //         wordPairs - An array of WordPair objects
    //         board_language - An integer indicating the language of the words in puzzle
    //         numToRemove - The number of cells to remove from the board to generate the puzzle.
    //         dummyBoard - A 2D integer array representing the board with some cells pre-filled.
    //         dummyBoardSolutions - A 2D integer array representing the solution to the board with
    //                               some cells pre-filled.
    // @return returns a new board using the 2nd board constructor for testing
    public static Board createDebugBoard(int dim, WordPair[] wordPairs,
                                         int board_language, int numToRemove,
                                         int[][] dummyBoard, int[][] dummyBoardSolutions) {
        return new Board(dim, wordPairs, board_language,
                numToRemove, dummyBoard, dummyBoardSolutions);
    }


    // EFFECT: Board Constructor for testing includes a dummyBoard for fault catching
    // @param  dim - The dimension of the board.
    //         wordPairs - An array of WordPair objects
    //         board_language - An integer indicating the language of the words in puzzle
    //         numToRemove - The number of cells to remove from the board to generate the puzzle.
    //         dummyBoard - A 2D integer array representing the board with some cells pre-filled.
    //         dummyBoardSolutions - A 2D integer array representing the solution to the board with
    //                               some cells pre-filled.
    private Board(int dim, WordPair[] wordPairs, int board_language, int numToRemove,
                  int[][] dummyBoard, int[][] dummyBoardSolutions) {

        checkMinNumberCellsValid(dim, numToRemove);

        //Prevent users from making a board where dimensions do not match length of wordPair list
        checkWordPairDimension(dim, wordPairs);

        this.dim = dim;
        this.BOX_LENGTH = (int)Math.sqrt(dim);

        this.board = dummyBoard;
        this.solutions = dummyBoardSolutions;
        this.displayBoard = new String[dim][dim];
        this.displayBoard_Solved = new String[dim][dim];
        this.wordPairs = wordPairs;
        this.board_language = board_language;

        // Sets the input language to the opposite of the board language
        this.input_language = BoardLanguage.getOtherLanguage(board_language);

        // Remove the number of cells based on the difficulty
        this.numToRemove = numToRemove;

        // initial count of mistakes made while inserting new value
        this.mistakes = 0;

        // Generate the board and displayed board values
        // Fill a 2d array of boolean values which indicates whether a cell is permanently filled
        // or not It is used to keep track of which cells are able to be filled and which are not
        // (the cells which are initially filled are not allowed to be filled).
        this.insertAllowedInBoard = getInsertTable(this.board,dim,dim);

        // generate the puzzle and the solution
        this.GenerateWordPuzzle();
    }

    // EFFECT: Checks if numToRemove does not exceed maximum minimum number
    // of cells (prevents creating invalid boards)
    // @param  int dim - dimension of the board
    //         int numToRemove - number of board to remove for initial playable state of the board
    // @throw  IllegalArgumentException thrown when there are more than 64 words removed
    public void checkMinNumberCellsValid(int dim, int numToRemove) {
        if (dim == 9 && numToRemove > 64 ) {
            throw new IllegalArgumentException
                    ("Number of cells to remove on a 9x9 board cannot exceed 64");
        }
    }

    // EFFECT: Checks if length of wordPair list matches given dimension
    // @param int dim - dimension of the board
    //        WordPair[] wordPair - array of wordPairs used
    // @throw IllegalArgumentException - if number of dimension isn't equal to the wordPairs in list
    public void checkWordPairDimension(int dim, WordPair[] wordPairs) {
        if (wordPairs.length != dim) {
            throw new IllegalArgumentException("WordPair list given must match dimension given");
        }
    }

    // EFFECT: take copy of the Board
    // @return copied new board
    public int[][] getBoard() {
        int[][] boardCopy = new int[dim][dim];
        return takeCopy(board, boardCopy);
    }

    // EFFECT: copy of the solutions
    // @return copy of the solution board
    public int[][] getBoardSolutions() {
        int[][] solutionCopy = new int[dim][dim];
        return takeCopy(solutions, solutionCopy);
    }


    // EFFECT: This utility method is used to return a 2D array of boolean values
    // which indicate whether a cell is filled or not
    // @param   int[][] board - board of ints
    //          int xDimension - x position
    //          int yDimension - y position
    // @return  an array of boolean
    private boolean[][] getInsertTable(int[][] board, int xDimension, int yDimension) {
        // Create a 2D boolean array to store the cell filled status.
        boolean[][] insertTable = new boolean[xDimension][yDimension];
        // Loop through the input board to get the cell filled status.
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                // If the cell is  filled
                if (board[i][j] != 0) {
                    // set the corresponding element in insertTable to false.
                    insertTable[i][j] = false;
                } else {
                    // else set the corresponding element in insertTable to true.
                    insertTable[i][j] = true;
                }
            }
        }

        return insertTable;
    }


    // @eakbarib
    // EFFECT: checks if the user has filled all of the cells in the board
    // @return true if all cells are filled, false otherwise
    public boolean hasUserFilled() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j]==0) {
                    return false;
                }
            }
        }
        return true;
    }


    // @eakbarib
    // EFFECT: Utility methods for converting the numbers in board int array
    // (which contain sudoku numbers [1-9] (inclusive)) to array-compatible
    // indices [0-8] (inclusive)  and vice versa
    // @param int x - number in cell
    // @return   index in array
    private int convertSudokuNumberToIndex(int x) {
        return x - 1;
    }

    // @param int x - index in array
    // @return   number in cell
    private int convertIndexToSudokuNumber(int x) {
        return x + 1;
    }

    // @eakbarib
    // EFFECT: Utility method for finding the index of a word in the wordPairs array
    // @param - String word - word value
    // @return - the index of the wordPair associated with the word
    private int getAssociatedWordPairIndex(String word) {
        for (int i = 0; i < wordPairs.length; i++) {
            if (wordPairs[i].getEnglishOrFrench(input_language).equals(word)) {
                return i;
            }
        }
        return -1;
    }


    // EFFECT: returns the number of mistakes made in the game by the player
    // @return int mistakes made so far
    public int getMistakes() {
        return mistakes;
    }

    // @eakbarib
    // Returns the latest board filled with initial words and user-input words
    // Could be used for updating the Sudoku board in the UI
    // @return displayBoard at the current state
    public String[][] getUnSolvedBoard() {
        return displayBoard;
    }


    // EFFECT: adds a the fre or eng word to the location on the board array
    // @param int x - x position on board
    //        int y - y position on board
    //        String word - word to insert
    // @throw RuntimeException if cell isn't empty
    //                         word isn't found in the list of wordPairs
    public void insertWord(int x, int y, String word) {
        if(!insertAllowedInBoard[x][y]) {
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
        displayBoard[x][y] =
                wordPairs[convertSudokuNumberToIndex(input)].getEnglishOrFrench(input_language);
        boolean isSafe = checkValidity(x, y, input);

        // if validity is failed
        if (!isSafe)
            // add the total number of mistakes by 1
            mistakes++;
    }

    // EFFECT: checks for wins
    // @return true if game is won by comparing to solution board else false
    public boolean checkWin() {
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                // if there is at least one different cell that is not equal
                // between solutions board and board the game is not won
                if (board[x][y] != solutions[x][y])
                    return false;
            }
        }
        // else game is won and return true
        return true;
    }


    /**
     * The generateGame() method is inspired by the following source:
     *      https://www.geeksforgeeks.org/program-sudoku-generator/
     *
     *      Steps:
     *          1. Fills the diagonal 3x3 matrices
     *          2. recursively fill the rest of the non-diagonal
     *             for each cell fill them by trying every possible value until
     *             it's valid within (row, col, box)
     *          3. once everything is filled remove n elements randomly to generate a game
     *             based on numToRemove
     *
     */

    // EFFECT: prepare a List of Coordinates where the initial positions of the words are
    public void generateGame() {

        // 1. fill the diagonal sub-boxes from top left to bottom right
        completeDiagonal();

        // 2. complete the remaining non-diagonal sub-boxes
        completeRemaining(0, BOX_LENGTH);

        // take a copy of the board of int before modifying for game generation
        solutions = takeCopy(board, solutions);

        // 3. remove the values of some cells based on numToRemove to start the game
        removeCellsByDifficulty();


    }

    // EFFECT: generates the word puzzle according to the selected board language
    //          in string form
    private void GenerateWordPuzzle() {
        // Simply fill the display board with the corresponding word in the word pair.
        // It is mapping the board int array to the string array
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                // check if the cell is not for the user to fill
                if (board[x][y] != 0) {
                    // set the same location on the displayBoard to word
                    // translated to the assigned language
                    displayBoard[x][y] =
                            wordPairs[convertSudokuNumberToIndex(board[x][y])]
                                    .getEnglishOrFrench(board_language);
                }

                // set all the cells (including the empty ones) to a word
                // or their correct translation
                displayBoard_Solved[x][y] =
                        wordPairs[convertSudokuNumberToIndex(solutions[x][y])]
                                .getEnglishOrFrench(input_language);
            }
        }
    }

    // EFFECT: fill the diagonal line in the board
    private void completeDiagonal() {
        // fills all the diagonal cells for every diagonal box
        for (int k = 0; k < dim; k = k + BOX_LENGTH) {
            completeBox(k, k);
        }
    }

    // EFFECT: Recursively fill the remaining cells when all diagonals are filled check if the
    //         position is safe before inserting the randomly generated value
    // @param: int x - x position on board
    //         int y - y position on board
    // @return true when all cell is filled and false otherwise
    private boolean completeRemaining(int x, int y) {

        // if the last cell at the column is complete and x is not at the cell in the row
        if (y >= dim && x < dim - 1) {
            // move to the next column
            x = x + 1;
            y = 0;
        }

        // if x and y are at the bottom right of the puzzle
        if (y >= dim && x >= dim) {
            // return true to indicate the remaining cells are filled successfully
            return true;
        }


        // if the position of the current column is less than the box length
        if (x < BOX_LENGTH) {
            // if the current cell in the column less than box length
            if (y < BOX_LENGTH) {
                // set the y position at the last cell box
                y = BOX_LENGTH;
            }

        // if the current row index is less than the difference between
        // the dimension of the board and the box length
        } else if (x < dim - BOX_LENGTH) {

            // if the current column index is equal to the product of the box length
            // and the current row index divided by the box length
            if (y == (int) (x/ BOX_LENGTH) * BOX_LENGTH) {
                // move the column index by one box length
                y = y + BOX_LENGTH;
            }
        }
        else
        {
            // else if column index is one box length away from the end index
            if (y == dim - BOX_LENGTH) {
                // move to the next row index
                x = x + 1;
                // move the column index to the beginning
                y = 0;
                // if x is at the last row index
                if (x >= dim)
                    // row is filled
                    return true;
            }
        }


        // for all possible values from 1 to 9
        for (int val = 1; val <= dim; val++)
        {
            // check the values validity at the given x and y position
            if (checkValidity(x, y, val)) {
                // if position is valid set the given board index to that value
                board[x][y] = val;
                // if remaining cells are filled
                if (completeRemaining(x, y + 1)) {
                    // all cells are filled successfully
                    return true;
                }

                // a valid value is not found yet keep looping
                board[x][y] = 0;
            }
        }
        // all cells are not filled so return false
        return false;
    }

    // EFFECT: check if it is safe to put the value in the given position
    //         else false
    // @param: int x - x position on board
    //         int y - y position on board
    //         int val - the int identifier inserted
    // @return true if value is not in the box, row column and false otherwise
    private boolean checkValidity(int x, int y, int val) {
        // the row, box and column for similar values
        return (isNotInRow(x, val) &&
                isNotInCol(y, val) &&
                isNotInBox((x - (x % BOX_LENGTH)), (y - (y % BOX_LENGTH)), val));
    }

    // EFFECT: fill a 3x3 matrix
    // @param int row - position of the row
    //        int col - position of the column
    private void completeBox(int row, int col) {

        // generate random value from 1-9
        int val = generateRandomValue(dim);

        // loop through the cells in the box
        for (int x = 0; x < BOX_LENGTH; x++) {
            for (int y = 0; y < BOX_LENGTH; y++) {

                // as long as a similar value is in the box
                while (!isNotInBox(row, col, val)) {
                    // generate a new random value from 1-9
                    val = generateRandomValue(dim);
                }
                // put the random value into the board cell after checking its validity
                board[row + x][col + y] = val;
            }
        }
    }

    // EFFECT: check if the same value exists in the box of 3x3
    // @param int x_start - starting x position
    //        int y_start - starting y position
    //        int val - value inserted
    // @return true if value is not in the box else false
    private boolean isNotInBox(int x_start, int y_start, int val) {

        // loop through a box
        for (int i = 0; i < BOX_LENGTH; i++) {
            for (int j = 0; j < BOX_LENGTH; j++) {
                // check if the value is within the box given the start x and y positions
                if (board[x_start + i][y_start + j] == val)
                    // return false if there is a similar value in the box
                    return false;
            }
        }

        // else return true since value is not in the box
        return true;
    }

    // EFFECT: check if the save value exists in the same column,
    //         return false if it does, true otherwise
    // @param int y - y position
    //        int val - value inserted
    // @return true if value is not in the given column else false
    private boolean isNotInCol(int y, int val) {

        // loop through the column at y-direction
        for (int i = 0; i < dim; i++) {
            // if there is similar value at the given y position
            if (board[i][y] == val)
                // position is invalid return false
                return false;
        }

        // else column is clean position is safe, return true
        return true;
    }


    // EFFECT: check if the same value exists in the given row
    // @param int x - x position
    //        int val - value inserted
    // @return true if value is not in the given row else false
    private boolean isNotInRow(int x, int val) {
        // loop through the column at x-direction
        for (int j = 0; j < dim; j++) {
            // if there is similar value at the given x position
            if (board[x][j] == val)
                // position is invalid return false
                return false;
        }
        // else column is clean position is safe, return true
        return true;
    }

    // EFFECT: remove n digits from the board where n = k_difficulty
    private void removeCellsByDifficulty() {

        int num = numToRemove;

        // as long as there are more cells to remove
        while (num != 0) {
            // generate a random int from 0-80
            int loc = generateRandomValue(dim * dim) - 1;

            // divide random value by dimension of the board
            int x = loc / dim;
            // the remainder of x when divided by dimension is y to
            // ensure y is within the range of the column size
            int y = loc % dim;

            // if the cell is filled
            if (board[x][y] != 0) {
                // there is one less cell to remove next decrease num one 1
                num-=1;
                // set the ell to 0 for gam
                board[x][y] = 0;
            }


        }
    }


    //EFFECT: generate a random number given max value i
    // @param int i - max number to be randomly generated
    // @return random int from 0 to i
    private int generateRandomValue(int i) {
        return (int) Math.floor(((Math.random() * i) + 1));
    }




    // EFFECT: take copy of an array without taking a reference
    // @param int [][] copiedForm - the array to be copied
    //        int [][] copiedInto - the new array that takes the copied values from copied board
    // @return copiedInto board
    private int [][] takeCopy(int [][] copiedFrom, int [][] copiedInto) {
        for (int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                //set every index of the new array to the index of the copied array
                copiedInto[i][j] = copiedFrom[i][j];
            }
        }

        // return the new copy of the array
        return copiedInto;
    }

    //  <For Testing Purposes Only>

    //EFFECT: prints the board board on console given the array
    public void printSudoku_int(int [][] print_board) {
        // print int [][] board for testing on console
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
        // print String [][] board for testing on console
        System.out.println("PRINTING: ");
        for (int x = 0; x < dim; x++)
        {
            for (int y = 0; y < dim; y++)
                System.out.print(print_board[x][y] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // </For Testing Purposes Only>
}
