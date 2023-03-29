package com.echo.wordsudoku.models.sudoku;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.json.Writable;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

    public final static int NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY = -2147483648;

    // Acceptable dimensions for the puzzle, you cannot create a puzzle with a dimension that is not in this list
    public static final List<Integer> ACCEPTABLE_DIMENSIONS = Arrays.asList(4,6,9,12);
    public static final List<PuzzleDimensions> ACCEPTABLE_PUZZLE_DIMENSIONS;

    static {
        try {
            ACCEPTABLE_PUZZLE_DIMENSIONS = new ArrayList<>(Arrays.asList(new PuzzleDimensions[]{
                    new PuzzleDimensions(4),
                    new PuzzleDimensions(6),
                    new PuzzleDimensions(9),
                    new PuzzleDimensions(12)
            }));
        } catch (IllegalDimensionException e) {
            throw new RuntimeException(e);
        }
    }

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

    private int timer = 0;

    /* @constructor
     * @param wordPairs: the word pairs that are used in the puzzle. no repetition is allowed
     * @param dimension: the dimension of the puzzle
     * @param language: the language of the puzzle
     * @param difficulty: the difficulty of the puzzle (1-5)
     * @param numberOfStartCells: the number of cells that are not removed from the solution board and user starts with them
     */
    public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells, int difficulty) throws IllegalDimensionException, IllegalWordPairException, IllegalLanguageException, TooBigNumberException, NegativeNumberException {

        // If the word pairs are null, we throw an exception
        if (wordPairs == null)
            throw new IllegalWordPairException();
        // If the word pairs are less than the dimension, we throw an exception
        if (wordPairs.size()<dimension)
            throw new IllegalWordPairException();
        if (WordPair.doesListContainRepeatingWordPairs(wordPairs))
            throw new IllegalWordPairException();
        // Otherwise we set the word pairs
        this.mWordPairs = wordPairs;

        // If the dimension is not in the list of acceptable dimensions, we throw an exception
        if (ACCEPTABLE_DIMENSIONS.contains(dimension) == false)
            throw new IllegalDimensionException();
        this.puzzleDimension = new PuzzleDimensions(dimension);

        // End of setting the puzzle dimension and box dimension

        // Setting the language
        setLanguage(language);

        // We set the language of the board opposite to the language of the puzzle
        this.solutionBoard = new CellBox2DArray(puzzleDimension,language);
        // TODO: Make the solution board cells all of them isEditable = false

        // First we create a solved board
        SolveBoard(this.solutionBoard,mWordPairs);

        int numberOfCellsToRemove;

        if(numberOfStartCells != NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY)
            numberOfCellsToRemove = solutionBoard.getRows()*solutionBoard.getColumns() - numberOfStartCells;
        else if (difficulty <= 5 && difficulty > 0)
            numberOfCellsToRemove = getCellsToRemoveWithDifficulty(difficulty);
        else
            throw new IllegalArgumentException("Invalid difficulty and number of start cells");
        // Calculate the number of cells to remove from the solution board

        // Then we remove a certain number of cells from the solution board and set the user board to the result
        CellBox2DArray userBoard = getTrimmedBoard(getSolutionBoard(),numberOfCellsToRemove,BoardLanguage.getOtherLanguage(language));
        setUserBoard(userBoard);

        // We lock the cells that are not empty so the user cannot change them
        lockCells();
    }




    public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells) throws IllegalWordPairException, IllegalDimensionException, IllegalLanguageException, TooBigNumberException, NegativeNumberException {
        this(wordPairs,dimension,language,numberOfStartCells,0);
    }

    /* @copy constructor
    a puzzle constructor that takes another puzzle and copies its fields
     */
    public Puzzle(Puzzle puzzle) {
        this.userBoard = new CellBox2DArray(puzzle.getUserBoard());
        this.solutionBoard = new CellBox2DArray(puzzle.getSolutionBoard());
        this.mWordPairs = new ArrayList<>(puzzle.getWordPairs());
        this.puzzleDimension = new PuzzleDimensions(puzzle.getPuzzleDimensions());
        this.language = puzzle.getLanguage();
        this.mistakes = puzzle.getMistakes();
        this.timer = puzzle.getTimer();
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
    public Puzzle(CellBox2DArray userBoard, CellBox2DArray solutionBoard, List<WordPair> wordPairs, PuzzleDimensions puzzleDimension, int language, int mistakes, int timer) throws IllegalDimensionException {
        if (ACCEPTABLE_PUZZLE_DIMENSIONS.contains(puzzleDimension) == false)
            throw new IllegalDimensionException();
        this.userBoard = new CellBox2DArray(userBoard);
        this.solutionBoard = new CellBox2DArray(solutionBoard);
        this.mWordPairs = new ArrayList<>(wordPairs);
        this.puzzleDimension = new PuzzleDimensions(puzzleDimension);
        this.language = language;
        this.mistakes = mistakes;
        this.timer = timer;
    }


    // Getters and setters

    public CellBox2DArray getUserBoard() {
        return userBoard;
    }

    public CellBox2DArray getSolutionBoard() {
        return solutionBoard;
    }

    public int getMistakes() {
        return mistakes;
    }

    public PuzzleDimensions getPuzzleDimensions() {
        return puzzleDimension;
    }

    public int getLanguage() {
        return language;
    }

    public List<WordPair> getWordPairs() {
        return mWordPairs;
    }

    public int getTimer() {
        return timer;
    }

    public void setLanguage(int language) throws IllegalArgumentException, IllegalLanguageException {
        if(!BoardLanguage.isValidLanguage(language))
            throw new IllegalLanguageException();
        this.language = language;
    }

    public void setTimer(int timer) throws NegativeNumberException {
        if (timer < 0)
            throw new NegativeNumberException("Timer cannot be negative");
        this.timer = timer;
    }
    public void setUserBoard(CellBox2DArray userBoard) throws IllegalDimensionException {
        if (userBoard.getBoxDimensions()!=(puzzleDimension.getBoxesInPuzzleDimension()) || userBoard.getCellDimensions() != (puzzleDimension.getEachBoxDimension()))
            throw new IllegalDimensionException();
        this.userBoard = new CellBox2DArray(userBoard);
    }

    public void setSafeUserBoard(CellBox2DArray userBoard) throws IllegalDimensionException, IllegalWordPairException {
        if (!new HashSet<>(userBoard.getAllWordPairs()).equals(new HashSet<>(this.userBoard.getAllWordPairs())))
            throw new IllegalWordPairException();
        setUserBoard(userBoard);
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
    public boolean isPuzzleSolved() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (!solutionBoard.getCellFromBigArray(i,j).isContentEqual(userBoard.getCellFromBigArray(i,j)))
                    return false;
            }
        }
        return true;
    }


    /* @method
     * Returns the game result. If the puzzle is solved, it returns a GameResult object with the result set to true, otherwise it returns a GameResult object with the result set to false and the number of mistakes
     * If the puzzle is not completed, the number of mistakes is the number of cells that have been inserted in the user board and are not equal to the corresponding ones in the solution board.
     * If the user has not entered any word pair, the number of mistakes is 0
     * @return: a GameResult object
     */
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
    public void setCell(int i, int j, WordPair word) throws IllegalDimensionException, IllegalWordPairException {
        if (mWordPairs.contains(word) == false)
            throw new IllegalWordPairException("Word pair not found in the list of word pairs");
        if (i < 0 || i >= userBoard.getRows() || j < 0 || j >= userBoard.getColumns())
            throw new IllegalDimensionException("Invalid cell coordinates");
        if (!userBoard.getCellFromBigArray(i,j).isEditable())
            throw new IllegalDimensionException("Trying to change a locked cell");
        userBoard.setCellFromBigArray(i,j,word);
        if (solutionBoard.getCellFromBigArray(i,j).isContentEqual(new Cell(word)) == false)
            mistakes++;
    }

    public void setCell(int i, int j, String word) throws IllegalWordPairException, IllegalDimensionException {
        for (WordPair wordPair : mWordPairs) {
            if (wordPair.doesContain(word)) {
                setCell(i, j, wordPair);
                return;
            }
        }
        throw new IllegalWordPairException("Word pair not found in the list of word pairs");
    }

    public void setCell(Dimension dimension, String word) throws IllegalWordPairException, IllegalDimensionException {
        if (dimension==null)
            throw new IllegalDimensionException("Dimension is null");
        setCell(dimension.getRows(), dimension.getColumns(), word);
    }

    public void setCell(Dimension dimension, WordPair word) throws IllegalWordPairException, IllegalDimensionException {
        if (dimension==null)
            throw new IllegalDimensionException("Dimension is null");
        setCell(dimension.getRows(), dimension.getColumns(), word);
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
                if (cell.getContent() != null){
                    if(!cell.isEditable()) {
                        stringBoard[i][j] = Integer.toString( mWordPairs.indexOf(cell.getContent()) + 1); // TODO Refactor this to be better
                    } else {
                        stringBoard[i][j] = cell.getContent().getEnglishOrFrench(cell.getLanguage());
                    }
                }
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

    public static boolean isIthRowNotContaining(CellBox2DArray cellBox2DArray , int i, WordPair wordPair) throws IllegalDimensionException, IllegalWordPairException {
        if (i < 0 || i >= cellBox2DArray.getRows())
            throw new IllegalDimensionException("Invalid row number");
        if (wordPair == null)
            throw new IllegalWordPairException("Word pair is null");
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
    public static boolean isJthColumnNotContaining(CellBox2DArray cellBox2DArray, int j, WordPair wordPair) throws IllegalWordPairException, IllegalDimensionException {
        if (j < 0 || j >= cellBox2DArray.getColumns())
            throw new IllegalDimensionException("Invalid column number");
        if (wordPair == null)
            throw new IllegalWordPairException("Word pair is null");
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

    public static boolean isJthColumnValid(CellBox2DArray cellBox2DArray,int j) throws IllegalDimensionException {
        if (j < 0 || j >= cellBox2DArray.getColumns())
            throw new IllegalDimensionException("Invalid column number");
        int rows = cellBox2DArray.getRows();
        List<WordPair> wordPairs = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            WordPair cellContent = cellBox2DArray.getCellFromBigArray(i,j).getContent();
            if(wordPairs.contains(cellContent))
                return false;
            wordPairs.add(cellContent);
        }
        return true;
    }
    public static boolean isIthRowValid(CellBox2DArray cellBox2DArray,int i) throws IllegalDimensionException {
        if (i < 0 || i >= cellBox2DArray.getColumns())
            throw new IllegalDimensionException("Invalid column number");
        int rows = cellBox2DArray.getRows();
        List<WordPair> wordPairs = new ArrayList<>();
        for (int j = 0; j < rows; j++) {
            WordPair cellContent = cellBox2DArray.getCellFromBigArray(i,j).getContent();
            if(wordPairs.contains(cellContent))
                return false;
            wordPairs.add(cellContent);
        }
        return true;
    }

    public static boolean isCellBoxValid(CellBox cellBox) {
        List<WordPair> wordPairs = new ArrayList<>();
        for (int i = 0; i < cellBox.getDimension().getRows(); i++) {
            for (int j = 0; j < cellBox.getDimension().getRows(); j++) {
                WordPair cellContent = cellBox.getCell(i,j).getContent();
                if(wordPairs.contains(cellContent))
                    return false;
                wordPairs.add(cellContent);
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
    public static boolean SolveBoard(CellBox2DArray board, List<WordPair> wordPairs) throws IllegalWordPairException, IllegalDimensionException {
        Dimension emptyCell = findEmptyCell(board);
        if (emptyCell == null) {
            // Puzzle is solved
            return true;
        }
        int x = emptyCell.getRows();
        int y = emptyCell.getColumns();
        for (WordPair word: wordPairs) {
            if (isIthRowNotContaining(board,x,word) && isJthColumnNotContaining(board,y,word) &&
                    (!board.getCellBox(x / board.getCellDimensions().getRows(), y / board.getCellDimensions().getColumns()).isContaining(word))) {
                board.setCellFromBigArray(emptyCell, word);
                if (SolveBoard(board,wordPairs)) {
                    return true;
                }
                board.clearCellFromBigArray(emptyCell.getRows(), emptyCell.getColumns());
            }
        }
        return false;
    }

    public static boolean isSudokuValid(CellBox2DArray board,List<WordPair> wordPairList) throws IllegalWordPairException, IllegalDimensionException {
        if (wordPairList == null)
            throw new IllegalWordPairException("Word pair list is null");
        if(wordPairList.size()!=board.getRows())
            throw new IllegalWordPairException("Word pair list size is not equal to board size");
        if (board == null)
            throw new NullPointerException("Board is null");
        if(board.getRows() != board.getColumns())
            throw new IllegalDimensionException("Board is not square");
        int size = board.getRows();
        boolean valid = true;
        for (int i =0;i<size;i++) {
            valid = valid && isIthRowValid(board,i) && isJthColumnValid(board,i) && isCellBoxValid(board.getCellBox(i/ board.getCellDimensions().getRows(),i/ board.getCellDimensions().getColumns()));
        }
        return valid;
    }



    /* @method
     *  Finds the next empty cell in the board
     * @param puzzle: the board to be checked
     * @return: a Dimension object containing the coordinates of the next empty cell
     * @return: null if there are no empty cells (Puzzle is solved)
     */
    public static Dimension findEmptyCell(CellBox2DArray puzzle) {
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
    public static CellBox2DArray getTrimmedBoard(CellBox2DArray solutionBoard,int cellsToRemove,int inputLanguage) throws IllegalLanguageException, NegativeNumberException, TooBigNumberException {
        // Create a copy of the solution board
        CellBox2DArray result = new CellBox2DArray(solutionBoard);
        if (cellsToRemove < 0)
            throw new NegativeNumberException("Number of cells to remove cannot be negative");
        if (cellsToRemove > result.getRows() * result.getColumns())
            throw new TooBigNumberException("Number of cells to remove cannot be greater than the number of cells in the board");
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
            cellThatIsGoingToBeRemoved.setLanguage(inputLanguage);
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

    public boolean isWritableCell(Dimension dimension) {
        return userBoard.getCellFromBigArray(dimension.getRows(),dimension.getColumns()).isEditable();
    }

    public void resetPuzzle(boolean resetTimer) {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEditable()) {
                    userBoard.getCellFromBigArray(i,j).clear();
                }
            }
        }
        mistakes = 0;
        if (resetTimer)
            timer = 0;
    }

    public boolean isPuzzleBlank() {
        boolean hasUserEnteredSomething = false;
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                Cell cell = userBoard.getCellFromBigArray(i,j);
                if (cell.isEditable() == true && cell.isEmpty() == false) {
                    hasUserEnteredSomething = true;
                    break;
                }
            }
        }
        return !hasUserEnteredSomething;
    }

    public boolean isPuzzleTotallyEmpty() {
        boolean puzzleIsNonEmpty = false;
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEmpty() == false) {
                    puzzleIsNonEmpty = true;
                    break;
                }
            }
        }
        return !puzzleIsNonEmpty;
    }

    public boolean[][] getImmutabilityTable() {
        boolean[][] result = new boolean[userBoard.getRows()][userBoard.getColumns()];
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                result[i][j] = !userBoard.getCellFromBigArray(i,j).isEditable();
            }
        }
        return result;
    }


    private int getCellsToRemoveWithDifficulty(int difficulty) {
        int size = puzzleDimension.getPuzzleDimension();
        return (difficulty+1)*size*size/8;
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
        json.put("puzzleDimensions", this.getPuzzleDimensions().toJson());
        json.put("language", this.getLanguage());
        json.put("mistakes", this.getMistakes());
        json.put("timer", this.getTimer());

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


    // equals method for comparison in testing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle = (Puzzle) o;

        boolean solBoardAreEqual = this.solutionBoard.equals(puzzle.solutionBoard);


        //boolean wordPairsAreEqual = new HashSet<>(this.getWordPairs()).equals(new HashSet<>(puzzle.getWordPairs()));
        boolean wordPairsAreEqual = areWordPairsEqual(puzzle.getWordPairs(), this.getWordPairs());
        boolean puzzleDimensionIsEqual = this.puzzleDimension.equals(puzzle.puzzleDimension);

        return language == puzzle.language && mistakes == puzzle.mistakes &&
                timer == puzzle.timer && puzzleDimensionIsEqual &&
                solBoardAreEqual && wordPairsAreEqual;
    }

    // @utility method to compare two wordPair lists
    // @param one wordPair list
    // @param two wordPair list
    // @return true if the two lists are equal, false otherwise
    private boolean areWordPairsEqual(List<WordPair> one, List<WordPair> two) {

        if (one.size() != two.size()) {
            return false;
        }

        for (int i = 0; i < one.size(); i++) {
            if (!one.get(i).equals(two.get(i))) {
                return false;
            }
        }
        return true;

    }


    // sets cells to Editable
    public void unlockCells() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                userBoard.getCellFromBigArray(i,j).setEditable(true);
            }
        }
    }

    public void fillUserBoardRandomly() throws IllegalWordPairException, IllegalDimensionException {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEditable()) {
                   setCell(i,j,getWordPairs().get(MathUtils.getRandomNumberBetweenIncluding(0,getWordPairs().size()-1)));
                }
            }
        }
    }

    public void solve() throws IllegalWordPairException, IllegalDimensionException {
        if(!isPuzzleSolved()) {
            for (int i = 0; i < userBoard.getRows(); i++) {
                for (int j = 0; j < userBoard.getColumns(); j++) {
                    if (userBoard.getCellFromBigArray(i, j).isEditable()) {
                        setCell(i, j, solutionBoard.getCellFromBigArray(i,j).getContent());
                    }
                }
            }
        }
    }

    public int numberOfFilledCells() {
        int result = 0;
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEmpty() == false)
                    result++;
            }
        }
        return result;
    }

    // Utility method
    public Cell getCellFromViewablePuzzle(int row, int col) {
        return userBoard.getCellFromBigArray(row, col);
    }

}
