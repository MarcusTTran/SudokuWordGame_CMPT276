package com.echo.wordsudoku.models.sudoku;

import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.Memory.Writable;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/* This class is used to store the puzzle dimensions
 *  It uses CellBox2DArray to store the puzzle (one puzzle as the completely solved puzzle and the other as the user's puzzle)
 *  It holds the number of mistakes the user has made by comparing the user's entered word with the solved puzzle each time the user enters a word
 *  It also holds the language of the puzzle
 *  It has a constructor that takes the dimension of the puzzle as a parameter
 *  In its constructor is does the following:
 *  1. Sets the puzzle dimension and box dimension
 *  2. Sets the language
 *  3. Creates the solution board
 *  4. Creates the user board by removing a certain number of cells from the solution board
 *  5. Locks the cells that are not removed from the solution board so the user cannot change them
 *
 *  Usage:
 *  Puzzle puzzle = new Puzzle(wordPairs,dimension,language,numberOfStartCells); // Creates a puzzle with the given dimension, language and number of start cells and initializes the solution board and the user board
 *  puzzle.getUserBoard(); // Returns the user board
 *  puzzle.getSolutionBoard(); // Returns the solution board
 *  puzzle.getWordPairs(); // Returns the word pairs
 *
 * */

public class Puzzle implements Writable {

    // Acceptable dimensions for the puzzle, you cannot create a puzzle with a dimension that is not in this list
    public static final List<Integer> ACCEPTABLE_DIMENSIONS = Arrays.asList(4,6,9,12);

    // The user board is the board that the user sees and enters the words in
    private CellBox2DArray userBoard;

    // The solution board is the board that contains the solution to the puzzle
    private CellBox2DArray solutionBoard;


    // The word pairs are the words that are used to in the puzzle and only they are legal to be entered in the puzzle
    private final List<WordPair> mWordPairs;

    // The puzzle dimension is the dimension of the puzzle stored in a PuzzleDimensions object which holds all the legal dimensions for the puzzle
    private final PuzzleDimensions puzzleDimension;

    // The language of the puzzle, by default it is set to the default language of English
    private int language = BoardLanguage.defaultLanguage();

    // The number of mistakes the user has made
    private int mistakes = 0;

    /* @constructor
     * @param wordPairs: the word pairs that are used in the puzzle
     * @param dimension: the dimension of the puzzle
     * @param language: the language of the puzzle
     * @param numberOfStartCells: the number of cells that are not removed from the solution board and user starts with them
     */
    public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells) {

        // If the word pairs are null, we throw an exception
        if (wordPairs == null)
            throw new IllegalArgumentException("Word pairs cannot be null");
        // If the word pairs are less than the dimension, we throw an exception
        if (wordPairs.size()<dimension)
            throw new IllegalArgumentException("Word pairs cannot be less than puzzle dimension");
        // Otherwise we set the word pairs
        this.mWordPairs = wordPairs;

        // If the dimension is not in the list of acceptable dimensions, we throw an exception
        if (ACCEPTABLE_DIMENSIONS.contains(dimension) == false)
            throw new IllegalArgumentException("Invalid dimension");
        this.puzzleDimension = new PuzzleDimensions(dimension);

        // End of setting the puzzle dimension and box dimension

        // Setting the language
        setLanguage(language);

        // We set the language of the board opposite to the language of the puzzle
        this.solutionBoard = new CellBox2DArray(puzzleDimension);

        // First we create a solved board
        SolveBoard(this.solutionBoard);

        // Calculate the number of cells to remove from the solution board
        int numberOfCellsToRemove = getSolutionBoard().getRows()*getSolutionBoard().getColumns() - numberOfStartCells;

        // Then we remove a certain number of cells from the solution board and set the user board to the result
        CellBox2DArray userBoard = getTrimmedBoard(numberOfCellsToRemove);
        setUserBoard(userBoard);

        // We lock the cells that are not empty so the user cannot change them
        lockCells();
    }

    /* @constructor
    a puzzle constructor that directly takes all the fields
    used by JSONReader to create a puzzle from a JSON file and resume the game state
    @param userBoard: the user board
    @param solutionBoard: the solution board
    @param wordPairs: the word pairs
    @param language: the language
    @param mistakes: the number of mistakes
     */
    public Puzzle(CellBox2DArray userBoard, CellBox2DArray solutionBoard, List<WordPair> wordPairs, PuzzleDimensions puzzleDimension, int language, int mistakes) {
        this.userBoard = userBoard;
        this.solutionBoard = solutionBoard;
        this.mWordPairs = wordPairs;
        this.puzzleDimension = puzzleDimension;
        this.language = language;
        this.mistakes = mistakes;
    }


    // Getters and setters

    public CellBox2DArray getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(CellBox2DArray userBoard) {
        if (userBoard.getBoxDimensions()!=(puzzleDimension.getBoxesInPuzzleDimension()) || userBoard.getCellDimensions() != (puzzleDimension.getEachBoxDimension()))
            throw new IllegalArgumentException("Invalid dimensions for the user board");
        this.userBoard = new CellBox2DArray(userBoard);
    }

    public CellBox2DArray getSolutionBoard() {
        return solutionBoard;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setSolutionBoard(CellBox2DArray solutionBoard) {
        if (solutionBoard.getBoxDimensions()!=(puzzleDimension.getBoxesInPuzzleDimension()) || solutionBoard.getCellDimensions() != (puzzleDimension.getEachBoxDimension()))
            throw new IllegalArgumentException("Invalid dimensions for the user board");
        this.solutionBoard = new CellBox2DArray(solutionBoard);
    }

    public PuzzleDimensions getPuzzleDimension() {
        return puzzleDimension;
    }

    public int getLanguage() {
        return language;
    }

    public List<WordPair> getWordPairs() {
        return mWordPairs;
    }

    public void setLanguage(int language) throws IllegalArgumentException {
        if(!BoardLanguage.isValidLanguage(language))
            throw new IllegalArgumentException("Invalid language name");
        this.language = language;
    }


    // End of getters and setters


    /* @method
     * Checks if the puzzle is filled. If there is an empty cell, it returns false, otherwise it returns true
     * @return: true if the puzzle is filled, false otherwise
     */
    public boolean isPuzzleFilled() {
        return userBoard.isFilled();
    }

    /* @method
     * Checks if the puzzle is solved. If there is a cell that is not equal to the solution board, it returns false, otherwise it returns true
     * @return: true if the puzzle is solved, false otherwise
     */
    private boolean isPuzzleSolved() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (!userBoard.getCellFromBigArray(i,j).isEqual(solutionBoard.getCellFromBigArray(i,j)))
                    return false;
            }
        }
        return true;
    }

    public GameResult getGameResult() {
        GameResult result = new GameResult();
        if (isPuzzleSolved())
            result.setResult(true);
        else {
            result.setResult(false);
            result.setMistakes(mistakes);
        }
        return result;
    }


    /* @method
     * Inserts a word pair in a cell that is not locked into the userBoard and increments the number of mistakes if the word pair is not equal to the corresponding one in the solution board
     * @param i: the row of the cell
     * @param j: the column of the cell
     * @param word: the word pair to be inserted
     * @throws IllegalArgumentException: if the word pair is not in the list of word pairs, if the cell coordinates are invalid or if the cell is locked
     */
    public void setCell(int i, int j, WordPair word) throws IllegalArgumentException {
        if (mWordPairs.contains(word) == false)
            throw new IllegalArgumentException("Word pair not found in the list of word pairs");
        if (i < 0 || i >= userBoard.getRows() || j < 0 || j >= userBoard.getColumns())
            throw new IllegalArgumentException("Invalid cell coordinates");
        if (!userBoard.getCellFromBigArray(i,j).isEditable())
            throw new RuntimeException("Trying to change a locked cell");
        userBoard.setCellFromBigArray(i,j,word);
        if (solutionBoard.getCellFromBigArray(i,j).isEqual(new Cell(word)) == false)
            mistakes++;
    }

    /* @method
     *  Returns a String[][] representation of the user board
     *  Can be used to update the UI
     * @return: a String[][] representation of the user board
     */
    public String[][] toStringArray() {
        String[][] stringBoard = new String[userBoard.getRows()][userBoard.getColumns()];
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                Cell cell = userBoard.getCellFromBigArray(i,j);
                if (cell.getContent() != null)
                    stringBoard[i][j] = cell.getContent().getEnglishOrFrench(cell.getLanguage());
                else
                    stringBoard[i][j] = "";
            }
        }
        return stringBoard;
    }


    // Core methods for the puzzle generation which uses backtrack sudoku solving algorithm


    /* @method
     *  Checks to see if the ith row of the board contains the word pair passed as parameter
     * @param cellBox2DArray: the board to be checked
     * @param i: the row to be checked
     * @param wordPair: the word pair to be checked
     * @return: true if the row contains the word pair, false otherwise
     * @throws IllegalArgumentException: if the row is invalid
     */

    public static boolean isIthRowContaining(CellBox2DArray cellBox2DArray ,int i, WordPair wordPair) {
        if (i < 0 || i >= cellBox2DArray.getRows())
            throw new IllegalArgumentException("Invalid row number");
        int columns = cellBox2DArray.getColumns();
        for (int j = 0; j < columns; j++) {
            WordPair wordPair1 = cellBox2DArray.getCellFromBigArray(i,j).getContent();
            if(wordPair1 !=null) {
                if (wordPair1.isEqual(wordPair)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* @method
     *  Checks to see if the jth column of the board contains the word pair passed as parameter
     * @param cellBox2DArray: the board to be checked
     * @param j: the column to be checked
     * @param wordPair: the word pair to be checked
     * @return: true if the column contains the word pair, false otherwise
     * @throws IllegalArgumentException: if the column is invalid
     */
    public static boolean isJthColumnContaining(CellBox2DArray cellBox2DArray,int j,WordPair wordPair) {
        if (j < 0 || j >= cellBox2DArray.getColumns())
            throw new IllegalArgumentException("Invalid column number");
        int rows = cellBox2DArray.getRows();
        for (int i = 0; i < rows; i++) {
            WordPair wordPair1 = cellBox2DArray.getCellFromBigArray(i,j).getContent();
            if (wordPair1!=null) {
                if (wordPair1.isEqual(wordPair)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* @method
     *  Solves a board using backtracking algorithm
     *  Uses the isIthRowContaining and isJthColumnContaining methods to check if the word pair can be inserted in the cell and isContaining method to check if the cell is already contained in the CellBox
     *  Uses the findEmptyCell method to find the next empty cell
     *  Uses the setCellFromBigArray method to insert the word pair in the cell
     *  @param board: the board to be solved
     *  @return: true if the board is solved, false otherwise
     *
     *  Inspired by https://www.geeksforgeeks.org/sudoku-backtracking-7/
     */
    public boolean SolveBoard(CellBox2DArray board) {
        Dimension emptyCell = findEmptyCell(board);
        if (emptyCell == null) {
            // Puzzle is solved
            return true;
        }
        int x = emptyCell.getRows();
        int y = emptyCell.getColumns();
        for (WordPair word: mWordPairs) {
            if (isIthRowContaining(board,x,word) && isJthColumnContaining(board,y,word) &&
                    (board.getCellBox(x/board.getCellDimensions().getRows(),y/board.getCellDimensions().getColumns()).isContaining(word)==false)) {
                board.setCellFromBigArray(emptyCell, word);
                if (SolveBoard(board)) {
                    return true;
                }
                board.setCellFromBigArray(emptyCell.getRows(), emptyCell.getColumns());
            }
        }
        return false;
    }

    /* @method
     *  Finds the next empty cell in the board
     * @param puzzle: the board to be checked
     * @return: a Dimension object containing the coordinates of the next empty cell
     * @return: null if there are no empty cells (Puzzle is solved)
     */
    private Dimension findEmptyCell(CellBox2DArray puzzle) {
        for (int i = 0; i < puzzle.getRows(); i++) {
            for (int j = 0; j < puzzle.getColumns(); j++) {
                if (puzzle.getCellFromBigArray(i,j).isEmpty())
                    return new Dimension(i,j);
            }
        }
        return null;
    }


    /* @method
     *  Returns a trimmed version of the solution board which is suitable to be presented to the user
     * The trimmed version is obtained by removing a random number of cells from the solution board
     * @param cellsToRemove: the number of cells to be removed from the solution board
     * @return: a trimmed version of the solution board
     */
    private CellBox2DArray getTrimmedBoard(int cellsToRemove) {
        // Create a copy of the solution board
        CellBox2DArray result = new CellBox2DArray(getSolutionBoard());
        if (cellsToRemove < 0)
            throw new IllegalArgumentException("Number of cells to remove cannot be negative");
        if (cellsToRemove > result.getRows() * result.getColumns())
            throw new IllegalArgumentException("Number of cells to remove cannot be greater than the number of cells in the board");
        for (int i = 0; i < cellsToRemove; i++) {
            Dimension cellToRemove = new Dimension(
                    (int) (Math.random() * result.getRows()),
                    (int) (Math.random() * result.getColumns())
            );
            while (result.getCellFromBigArray(cellToRemove).isEmpty()) {
                cellToRemove = new Dimension(
                        (int) (Math.random() * result.getRows()),
                        (int) (Math.random() * result.getColumns())
                );
            }
            Cell cellThatIsGoingToBeRemoved = result.getCellFromBigArray(cellToRemove);
            cellThatIsGoingToBeRemoved.setLanguage(BoardLanguage.getOtherLanguage(getLanguage()));
            cellThatIsGoingToBeRemoved.clear();
        }
        return result;
    }

    /* @method
     *  Locks the cells of the user board
     *  User cannot insert into the cells that are part of the initial board.
     *  This must be called immediately after the user board is created.
     */
    private void lockCells() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEmpty() == false)
                    userBoard.getCellFromBigArray(i,j).setEditable(false);
            }
        }
    }


    /*
     * @method convert the puzzle object into json and every single field
     * @returns Json object to be written into the json file
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("userBoard", this.getUserBoard().toJson());
        json.put("solutionBoard", this.getSolutionBoard().toJson());
        json.put("wordPairs", convertWordPairsToJson());
        json.put("puzzleDimensions", this.getPuzzleDimension().toJson());
        json.put("language", this.getLanguage());
        json.put("mistakes", this.getMistakes());

        return json;
    }


    // @utility convert all wordPairs into json Object
    // @return JSONArray which is an array of json-converted wordPairs
    private JSONArray convertWordPairsToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (WordPair w : this.getWordPairs()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
