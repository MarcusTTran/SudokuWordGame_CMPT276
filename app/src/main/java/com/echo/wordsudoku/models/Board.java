package com.echo.wordsudoku.models;

/**
 *  DESCRIPTION OF FIELDS AND FEATURES
    -   the Board class contains 4 parallel arrays 2 of them contain the numerical
        representation [1-9]
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
*/

public class Board {
    private int[][] board;
    private int [][] solutions;
    private String[][] displayBoard, displayBoard_Solved;
    private WordPair[] wordPairs;

    // change to enum later
    String board_language, input_language;
    private int difficulty_k;
    private int mistakes;
    private final int N = 9;
    private final String ENGLISH = "English";
    private final String FRENCH = "French";

    private final int BOX_LENGTH = 3;

    // constructor
    // EFFECTS: makes a 2D array list and adds empty string to each location on list
    public Board(WordPair[] wordPairs, String board_language, int difficulty_k) {
        this.board = new int[N][N];
        this.solutions = new int[N][N];
        this.displayBoard = new String[N][N];
        this.displayBoard_Solved = new String[N][N];
        this.wordPairs = wordPairs;
        this.board_language = board_language;
        this.input_language = this.board_language.equals(ENGLISH) ? FRENCH : ENGLISH;
        this.difficulty_k = difficulty_k;
        this.mistakes = 0;

        // generate the board and displayed board values
        this.generateGame();
        this.GenerateWordPuzzle();

        // UNCOMMENT FOR TESTING THE BOARD LAYOUT ON CONSOLE

//        this.printSudoku_int(this.getUnSolvedBoard());
//        this.printSudoku_int(this.getSolvedBoard());
    }


    // EFFECT: returns the solved numerical board
    public int[][] getUnSolvedBoard() {
        return board;
    }

    // EFFECT: returns the unsolved numerical Board
    public int[][] getSolvedBoard() {
        return solutions;
    }

    // EFFECT: returns the number of mistakes made in the game by the player
    public int getMistakes() {
        return mistakes;
    }

    // EFFECTS: adds a the fre or eng word to the location on the board array
    public void insertWord(int x, int y, int id) {
        int input = id;
        board[x][y] = input;
        displayBoard[x][y] = wordPairs[id - 1].getEnglishOrFrench(input_language);
        boolean isSafe = checkValidity(x, y, id);

        if (!isSafe)
            mistakes++;
    }

    // EFFECTS: checks for wins
    public boolean checkWin() {

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
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

    // EFFECTS: prepare a List of Coordinates where the initial positions of the words are
    public void generateGame() {
        // Algorithm
        // 1. Fills the diagonal 3x3 matrices
        // 2. recursively fill the rest of the non-diagonal
        //    for each cell fill them by trying every possible value until it's SAFE
        // 3. once everything is filled remove k elements randomely to generate a game

        arrangeDiagonal();

        arrangeRemaining(0, BOX_LENGTH);

        solutions = takeCopy(board, solutions);

        removeCellsByDifficulty(); //after we know the solution
    }

    // EFFECT: fill the diagonal line in the board
    private void arrangeDiagonal() {
        for (int k = 0; k < N; k = k + BOX_LENGTH) {
            fillBox(k, k); // to only fill the diagonal cells (k, k)
        }
    }

    // EFFECT: fill the remaining cells when all diagonals are filled check if the position is safe
    //            before inserting the randomly generated value
    private boolean arrangeRemaining(int i, int j) {

        if (j >= N && i < N - 1)
        {
            i = i + 1;
            j = 0;
        }
        if (j >= N && i >= N)
            return true;

        if (i < BOX_LENGTH) {
            if (j < BOX_LENGTH) {
                j = BOX_LENGTH;
            }

        } else if (i < N - BOX_LENGTH) {

            if (j == (int) (i/ BOX_LENGTH) * BOX_LENGTH) {
                j = j + BOX_LENGTH;
            }
        }
        else
        {
            if (j == N - BOX_LENGTH) {
                i = i + 1;
                j = 0;
                if (i >= N)
                    return true;
            }
        }

        for (int num = 1; num <= N; num++)
        {
            if (checkValidity(i, j, num)) {
                board[i][j] = num;
                if (arrangeRemaining(i, j + 1)) {
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
    private void fillBox(int row, int col) { ///!!!!!!!!!!!!
        int val;

        for (int x = 0; x < BOX_LENGTH; x++) {
            for (int y = 0; y < BOX_LENGTH; y++) {

                do {
                    val = generateRandomValue(N);
                }
                while (!isNotInBox(row, col, val));

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

        for (int i = 0; i < N; i++) {
            if (board[i][y] == val)
                return false;
        }

        return true;
    }

    // EFFECT: check if the same value exists in the given row
    private boolean isNotInRow(int x, int val) {
        for (int j = 0; j < N; j++) {
            if (board[x][j] == val)
                return false;
        }
        return true;
    }

    // EFFECT: remove n digits from the board where n = k_difficulty
    private void removeCellsByDifficulty() { ///!!!!!!!!!!!!

        int num = difficulty_k;

        while (num != 0) {
            int loc = generateRandomValue(N * N) - 1;

            int x = loc / N;
            int y = loc % N;

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
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(print_board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // EFFECT: prints the displayed sudoku containing french and english words
    public void printSudoku_String(String [][] print_board) {
        System.out.println("PRINTING: ");
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(print_board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }


    //EFFECT: take copy of an array without taking a reference
    private int [][] takeCopy(int [][] copiedFrom, int [][] copiedInto) {
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                copiedInto[i][j] = copiedFrom[i][j];
            }
        }

        return copiedInto;
    }

    // EFFECT: generates the word puzzle according to the selected board language
    private void GenerateWordPuzzle() {

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {

                if (board[x][y] == 0) {
                    displayBoard[x][y] = "  +  ";
                    displayBoard_Solved[x][y] =
                            wordPairs[solutions[x][y] - 1].getEnglishOrFrench(input_language);
                }

                if (board[x][y] != 0) {
                    displayBoard[x][y] =
                            wordPairs[board[x][y] - 1].getEnglishOrFrench(board_language);
                    displayBoard_Solved[x][y] =
                            wordPairs[solutions[x][y] - 1].getEnglishOrFrench(board_language);
                }
            }
        }

        // UNCOMMENT FOR TESTING THE BOARD LAYOUT
//         printSudoku_String(displayBoard);
//         printSudoku_String(displayBoard_Solved);
    }

}
