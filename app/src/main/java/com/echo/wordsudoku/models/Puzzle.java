package com.echo.wordsudoku.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Puzzle {

    // Acceptable dimensions for the puzzle
    public static final List<Integer> ACCEPTABLE_DIMENSIONS = Arrays.asList(4,6,9,12);

    private CellBox2DArray userBoard;
    private CellBox2DArray solutionBoard;

    private final List<WordPair> mWordPairs;

    private final PuzzleDimensions puzzleDimension;
    private int language;
    private int mistakes;

    public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells) {

        if (wordPairs == null)
            throw new IllegalArgumentException("Word pairs cannot be null");
        if (wordPairs.size()<dimension)
            throw new IllegalArgumentException("Word pairs cannot be less than puzzle dimension");
        this.mWordPairs = wordPairs;

        // Setting the puzzle dimension and box dimension

        if (ACCEPTABLE_DIMENSIONS.contains(dimension) == false)
            throw new IllegalArgumentException("Invalid dimension");
        this.puzzleDimension = new PuzzleDimensions(dimension);

        // End of setting the puzzle dimension and box dimension

        // Setting the language
        setLanguage(language);

        // We set the language of the board opposite to the language of the puzzle
        this.solutionBoard = new CellBox2DArray(puzzleDimension);
        SolveBoard(this.solutionBoard);

        int numberOfCellsToRemove = getSolutionBoard().getRows()*getSolutionBoard().getColumns() - numberOfStartCells;

        CellBox2DArray userBoard = getTrimmedBoard(numberOfCellsToRemove);
        setUserBoard(userBoard);
        // We lock the cells that are not empty so the user cannot change them
        lockCells();
    }

    // Getters and setters

    public CellBox2DArray getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(CellBox2DArray userBoard) {
        this.userBoard = userBoard;
    }

    public CellBox2DArray getSolutionBoard() {
        return solutionBoard;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setSolutionBoard(CellBox2DArray solutionBoard) {
        this.solutionBoard = solutionBoard;
    }

    public PuzzleDimensions getPuzzleDimension() {
        return puzzleDimension;
    }

    public int getLanguage() {
        return language;
    }


    public void setLanguage(int language) throws IllegalArgumentException {
        if(!BoardLanguage.isValidLanguage(language))
            throw new IllegalArgumentException("Invalid language name");
        this.language = language;
    }


    // End of getters and setters


    public boolean isPuzzleFilled() {
        return userBoard.isFilled();
    }

    public boolean isPuzzleSolved() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (!userBoard.getCellFromBigArray(i,j).isEqual(solutionBoard.getCellFromBigArray(i,j)))
                    return false;
            }
        }
        return true;
    }

    public void setCell(int i, int j, WordPair word) throws IllegalArgumentException {
        if (i < 0 || i >= userBoard.getRows() || j < 0 || j >= userBoard.getColumns())
            throw new IllegalArgumentException("Invalid cell coordinates");
        if (!userBoard.getCellFromBigArray(i,j).isEditable())
            throw new RuntimeException("Trying to change a locked cell");
        userBoard.setCellFromBigArray(i,j,word);
        if (solutionBoard.getCellFromBigArray(i,j).isEqual(new Cell(word)) == false)
            mistakes++;
    }

    public String[][] toStringArray() {
        String[][] stringBoard = new String[userBoard.getRows()][userBoard.getColumns()];
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                Cell cell = userBoard.getCellFromBigArray(i,j);
                if (cell.getContent() != null)
                    stringBoard[i][j] = cell.getContent().getEnglishOrFrench(language);
                else
                    stringBoard[i][j] = "";
            }
        }
        return stringBoard;
    }


    // Core methods

    public static boolean isIthRowContaining(CellBox2DArray cellBox2DArray ,int i, WordPair wordPair) {
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

    public static boolean isJthColumnContaining(CellBox2DArray cellBox2DArray,int j,WordPair wordPair) {
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

    private Dimension findEmptyCell(CellBox2DArray puzzle) {
        for (int i = 0; i < puzzle.getRows(); i++) {
            for (int j = 0; j < puzzle.getColumns(); j++) {
                if (puzzle.getCellFromBigArray(i,j).isEmpty())
                    return new Dimension(i,j);
            }
        }
        return null;
    }

    private CellBox2DArray getTrimmedBoard(int cellsToRemove) {
        CellBox2DArray result = new CellBox2DArray(getSolutionBoard().getCellBoxes(),getSolutionBoard().getBoxDimensions(),getSolutionBoard().getCellDimensions());
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
            cellThatIsGoingToBeRemoved.clear();
        }

        return result;
    }

    private void lockCells() {
        for (int i = 0; i < userBoard.getRows(); i++) {
            for (int j = 0; j < userBoard.getColumns(); j++) {
                if (userBoard.getCellFromBigArray(i,j).isEmpty() == false)
                    userBoard.getCellFromBigArray(i,j).setEditable(false);
            }
        }
    }



}
