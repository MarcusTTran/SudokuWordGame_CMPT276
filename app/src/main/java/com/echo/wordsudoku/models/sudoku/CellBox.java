package com.echo.wordsudoku.models.sudoku;

/*
 * This class represents a 2D array of cells.
 * It is used to represent a box in a sudoku puzzle.
 * It contains the cells (cells: Cell[][]), the number of rows and the number of columns of the 2D array of cells in a Dimension object (dimension: Dimension).
 *
 * Usage:
 * CellBox cellBox = new CellBox(rows, columns);
 * cellBox.getCells();
 * cellBox.setCells(cells);
 * cellBox.getDimension();
 * cellBox.isContaining(word);
 * cellBox.getCell(row, column);
 * cellBox.setCell(row, column, cell);
 *
 * Below is an example of a 4x4 sudoku puzzle with 4 CellBox objects (2 of which are shown on the graph):
 * The CellBoxes each contain 4 cells.
 * The CellBoxes are arranged in a 2x2 grid.
 * In a 9x9 sudoku puzzle, there are 9 CellBoxes arranged in a 3x3 grid.
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯--------------------
 * ║                   ║         |         |
 * ----  CellBox  --------------------------
 * ║                   ║         |         |
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
 * |         |         ║                   ║
 * ------------------------  CellBox  ------
 * |         |         ║                   ║
 * --------------------⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
 *
 * CellBox = {cells: 'The 2D array of cells',
 * dimension: 'The number of rows and the number of columns of the 2D array of cells in a Dimension object'}
 * }
 *
 * author eakbarib
 *
 * version 1.0

 */

import com.echo.wordsudoku.models.json.Writable;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class CellBox implements Writable {

    // cells: the 2D array of cells.
    private Cell[][] cells;

    // dimension: the number of rows and the number of columns of the 2D array of cells in a Dimension object.
    // it is defined to be final so that we avoid the errors caused by changing the dimension of the cells while the array has not been resized.
    private final Dimension dimension;


    /*
     * @constructor
     * Constructs a CellBox object with the given number of rows and columns and set language and initializes the cells with the given content.
     * @param rows: the number of rows of the 2D array of cells.
     * @param columns: the number of columns of the 2D array of cells.
     * @param language: the language of the words in the cells.
     * @param content: the content of the cells. The content of all cells will be the same.
     *
     * */
//    public CellBox(WordPair content, int rows, int columns, int language) {
//        this.dimension = new Dimension(rows, columns);
//        this.cells = new Cell[rows][columns];
//        fillCellsWith(new Cell(content,language));
//    }

    /*
     * @constructor
     * Constructs a CellBox object with the given number of rows and columns and initializes the cells with the given content.
     * The language of the words in the cells will be the default language which is set by the Cell constructor.
     * @param rows: the number of rows of the 2D array of cells.
     * @param columns: the number of columns of the 2D array of cells.
     * @param content: the content of the cells. The content of all cells will be the same.
     * */

    public CellBox(WordPair content,int rows, int columns) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        fillCellsWith(new Cell(content));
    }


    /*
     * @constructor
     * Constructs a CellBox object with the given number of rows and columns and a set language and initializes the cells as empty (content set as null).
     * @param rows: the number of rows of the 2D array of cells.
     * @param columns: the number of columns of the 2D array of cells.
     * @param language: the language of the words in the cells.
     */
    public CellBox(int rows, int columns,int language) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        fillCellsWith(new Cell(language));
    }

    /*
     * @constructor
     * Constructs a CellBox object with the given number of rows and columns and initializes the cells as empty (content set as null).
     * The language of the words in the cells will be the default language which is set by the Cell constructor.
     * @param rows: the number of rows of the 2D array of cells.
     * @param columns: the number of columns of the 2D array of cells.
     */

    public CellBox(int rows, int columns) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        fillCellsWith(new Cell());
    }


    /*
     * @constructor
     * Constructs a CellBox object with the given dimension and initializes the cells as empty (content set as null).
     * The language of the words in the cells will be the default language which is set by the Cell constructor.
     * @param dimension: the dimension of the 2D array of cells.
     */
//    public CellBox(Dimension dimension) {
//        this(dimension.getRows(), dimension.getColumns());
//    }

    /*
     * @constructor
     * Makes a deep copy of the given CellBox object.
     * @param cellBox: the CellBox object to be copied.
     * */
    public CellBox(CellBox cellBox) {
        this.dimension = new Dimension(cellBox.dimension.getRows(), cellBox.dimension.getColumns());
        this.cells = new Cell[cellBox.dimension.getRows()][cellBox.dimension.getColumns()];
        for (int i = 0; i < cellBox.dimension.getRows(); i++) {
            for (int j = 0; j < cellBox.dimension.getColumns(); j++) {
                cells[i][j] = new Cell(cellBox.getCell(i, j));
            }
        }
    }

    /*
     * @constructor
     * Constructs a CellBox object with the given dimension and initializes the cells with the given content.
     * @param cells: the 2D array of cells.
     * @param dimension: the dimension of the 2D array of cells.
     */
    public CellBox (Cell [][] cells, Dimension dimension) {
        this.dimension = dimension;
        this.cells = cells;
    }

    // Getters and Setters
    // for the member variable Cell[][] cells
    public void setCells(Cell[][] cells) throws IllegalArgumentException {
        // Check if the given cells is a square and has the same dimension as the cellSquareArray
        if (cells.length != dimension.getRows() || cells[0].length != dimension.getColumns())
            throw new IllegalArgumentException("Invalid dimension");
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    // End of Getters and Setters



    /*
     * @method
     * Returns the cell at the given position.
     * @param i: the row number of the cell.
     * @param j: the column number of the cell.
     * @return the cell at the given position.
     * Used in the method isContaining(WordPair word) to check if the given word is already in the cellBox.
     * Used in the method setCellsLanguage(int language) to set the language of all cells.
     * Used in the method hasEmptyCells() to check if there is any empty cell in the cellBox.
     * Used in the method getEmptyCell() to get the first empty cell in the cellBox.
     * Usage: cellBox.getCell(i,j).setLanguage(language);
     * The above line of code changes the language of the cell at the given position (i,j).
     */
    public Cell getCell(int i, int j) {
        return cells[i][j];
    }


    /*
     * @method
     * Returns a boolean value indicating if the given word is already in the cellBox.
     * @param word: the word to be checked.
     * @return true if the given word is already in the cellBox, false otherwise.
     * Used in the method addWord(WordPair word) to check if the given word is already in the cellBox.
     * Usage:
     * If the given word is already in the cellBox, we will not insert it again because in sudoku, each word can only appear once in each row, column and box.
     */
    public boolean isContaining(WordPair word) {
        for (int i = 0; i < this.dimension.getRows(); i++) {
            for (int j = 0; j < this.dimension.getColumns(); j++) {
                WordPair content = cells[i][j].getContent();
                if(content!=null) {
                    if (word.isEqual(content))
                        return true;
                }
            }
        }
        return false;
    }

    /*
     * @method
     * Changes the language of all cells in the cellBox.
     * @param language: the language to be set.
     * Used in the method setLanguage(int language) to change the language of all cells in the cellBox.
     * Usage: cellBox.setCellsLanguage(language);
     */

    public void setCellsLanguage(int language) {
        for (int i = 0; i < this.dimension.getRows(); i++) {
            for (int j = 0; j < this.dimension.getColumns(); j++) {
                cells[i][j].setLanguage(language);
            }
        }
    }

    /*
     * Utility
     * @method
     * It is used to make a 2d array of cells with the given dimension and the given content.
     * @param content: the content to be set for all cells.
     * Used in the constructors to initialize the cells of the 2d array with new Cell objects.
     */
    private void fillCellsWith(Cell c) {
        for (int i = 0; i < this.dimension.getRows(); i++) {
            for (int j = 0; j < this.dimension.getColumns(); j++) {
                cells[i][j] = new Cell(c);
            }
        }
    }

    /* @method to covert the CellBox Object into json including the Dimension and all Cells
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("cells", allCells2DToJson());
        json.put("dimension", this.dimension.toJson());


        return json;
    }

    /* @utility to covert all Cells (array) into Json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    private JSONArray allCells2DToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Cell[] cells : this.getCells()) {
            jsonArray.put(cellsToJson(cells));
        }

        return jsonArray;
    }

    /* @utility to covert each Cell within list of Cells (array) into Json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    private JSONArray cellsToJson(Cell [] cells) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Cell c : cells) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


    // equals method for comparison in testing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellBox cellBox = (CellBox) o;

        boolean CellsEqual = areCellsEqual(this.getCells(), cellBox.getCells());

        return CellsEqual && dimension.equals(cellBox.dimension);
    }

    private boolean areCellsEqual(Cell[][] one, Cell[][] two) {

        if (one.length != two.length)
            return false;

        for (int n = 0; n < one.length; n++) {
            if (one[n].length != two[n].length) {
                return false;
            }
        }

        for (int i = 0; i < one.length; i++) {
            for (int j = 0; j < one[i].length; j++) {
                if (!one[i][j].equals(two[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }


}
