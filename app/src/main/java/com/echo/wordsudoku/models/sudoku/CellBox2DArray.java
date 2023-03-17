package com.echo.wordsudoku.models.sudoku;

/*
 * CellBox2DArray class represents a 2D array of CellBox objects
 * It is used to represent a sudoku puzzle.
 * It contains the CellBoxes (cellBoxes: CellBox[][]), the number of rows and the number of columns of the 2D array of CellBoxes in a Dimension object (boxDimensions: Dimension) and the number of rows and the number of columns of the cells in a CellBox in a Dimension object (cellDimensions: Dimension).
 *
 * Usage:
 * CellBox2DArray cellBox2DArray = new CellBox2DArray(rowsOfBoxes, columnsOfBoxes, rowsOfCells, columnsOfCells);
 * cellBox2DArray.getCellBoxes();
 * cellBox2DArray.setCellBoxes(cellBoxes);
 * cellBox2DArray.getBoxDimensions();
 * cellBox2DArray.setBoxDimensions(boxDimensions);
 * cellBox2DArray.getCellDimensions();
 * cellBox2DArray.setCellDimensions(cellDimensions);
 * cellBox2DArray.getCellBox(rowOfBox, columnOfBox);
 * cellBox2DArray.setCellBox(rowOfBox, columnOfBox, cellBox);
 * cellBox2DArray.getCell(rowOfBox, columnOfBox, rowOfCell, columnOfCell);
 * cellBox2DArray.setCell(rowOfBox, columnOfBox, rowOfCell, columnOfCell, cell);
 * cellBox2DArray.getRows();
 * cellBox2DArray.getColumns();
 *
 * Below is an example of a 4x4 sudoku puzzle with 2x2 cells in each CellBox and one CellBox2DArray object representing the puzzle as a whole:
 * The CellBox2DArray object contains 4 CellBox objects in a 2x2 2D array.
 * Each CellBox object contains 4 Cell objects in a 2x2 2D array.
 * Each Cell object contains a WordPair object (content) and a boolean value (isEditable).
 * Each puzzle is a CellBox2DArray object.
 * -----------------------------------------
 * 〣                                       〣
 * 〣                                       〣
 * 〣                                       〣
 * 〣          CellBox2DArray               〣
 * 〣                                       〣
 * 〣                                       〣
 * 〣                                       〣
 * -----------------------------------------
 *
 *
 * CellBox2DArray = {cellBoxes: 'The 2D array of CellBox objects',
 * boxDimensions: 'The number of rows and the number of columns of the 2D array of CellBoxes',
 * cellDimensions: 'The number of rows and the number of columns of the cells in a CellBox'}
 *
 *
 * @author eakbarib
 *
 * @version 1.0
 *
 * */

import com.echo.wordsudoku.models.json.Writable;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class CellBox2DArray implements Writable {

    // The 2D array of CellBox objects
    private CellBox[][] cellBoxes;

    // The number of rows and the number of columns of the 2D array of CellBoxes
    private Dimension boxDimensions;

    // The number of rows and the number of columns of the cells in a CellBox
    private Dimension cellDimensions;


    /*
     * @constructor
     * Takes an array of CellBox objects and the number of rows and the number of columns of
     *  the 2D array of CellBoxes and the number of rows and the number of columns of the cells in
     * a CellBox as parameters and initializes the CellBox2DArray object.
     * It makes a deep copy of the array of CellBox objects and sets it using the setCellBoxes method.
     * @param cellBoxes: The 2D array of CellBox objects
     * @param boxes: The number of rows and the number of columns of the 2D array of CellBoxes
     * in a Dimension object
     * @param cells: The number of rows and the number of columns of the cells in a CellBox in a Dimension object
     * */
    public CellBox2DArray(CellBox[][] cellBoxes, Dimension boxes, Dimension cells) {
        setBoxDimensions(boxes);
        setCellDimensions(cells);
        setCellBoxes(cellBoxes);
    }

    /*
     * @constructor
     * Takes the number of rows and the number of columns of the 2D array of CellBoxes and the number of rows and the number of columns of the cells in a CellBox as parameters and the language to set cells in and initializes the CellBox2DArray object with empty CellBox objects.
     * @param boxes: The number of rows and the number of columns of the 2D array of CellBoxes in a Dimension object
     * @param cells: The number of rows and the number of columns of the cells in a CellBox in a Dimension object
     * @param language: The language to set cells in
     * */
    public CellBox2DArray(Dimension boxes, Dimension cells,int language) {
        initializeCellBox(boxes, cells, new CellBox(cells.getRows(), cells.getColumns(),language));
    }


    /*
     * @constructor
     * Takes the number of rows and the number of columns of the 2D array of CellBoxes and the number of rows and the number of columns of the cells in a CellBox as parameters and initializes the CellBox2DArray object with empty CellBox objects.
     * @param boxes: The number of rows and the number of columns of the 2D array of CellBoxes in a Dimension object
     * @param cells: The number of rows and the number of columns of the cells in a CellBox in a Dimension object
     */
    public CellBox2DArray(Dimension boxes, Dimension cells) {
        initializeCellBox(boxes, cells, new CellBox(cells.getRows(), cells.getColumns()));
    }

    /*
     * @constructor
     * Takes the appropriate sudoku puzzle dimensions and the language to set cells in and initializes the CellBox2DArray object with empty CellBox objects.
     * @param puzzleDimensions: The appropriate sudoku puzzle dimensions passed as a PuzzleDimensions object
     * @param language: The language to set cells in
     * */
    public CellBox2DArray(PuzzleDimensions puzzleDimensions, int language) {
        this(puzzleDimensions.getBoxesInPuzzleDimension(), puzzleDimensions.getEachBoxDimension(),language);
        setBoxDimensions(puzzleDimensions.getBoxesInPuzzleDimension());
        setCellDimensions(puzzleDimensions.getEachBoxDimension());
    }

    /*
     * @constructor
     * Takes the appropriate sudoku puzzle dimensions and initializes the CellBox2DArray object with empty CellBox objects (language is set to English as a default).
     * @param puzzleDimensions: The appropriate sudoku puzzle dimensions passed as a PuzzleDimensions object
     */
    public CellBox2DArray(PuzzleDimensions puzzleDimensions) {
        this(puzzleDimensions.getBoxesInPuzzleDimension(), puzzleDimensions.getEachBoxDimension());
        setBoxDimensions(puzzleDimensions.getBoxesInPuzzleDimension());
        setCellDimensions(puzzleDimensions.getEachBoxDimension());
    }

    /*@constructor
     * Makes a deep copy of the CellBox2DArray object passed as a parameter.
     * */
    public CellBox2DArray(CellBox2DArray cellBox2DArray) {
        this(cellBox2DArray.getCellBoxes(), cellBox2DArray.getBoxDimensions(), cellBox2DArray.getCellDimensions());
    }

    // Getters and setters
    public CellBox[][] getCellBoxes() {
        return cellBoxes;
    }

    public void setCellBoxes(CellBox[][] cellBoxes) {
        int rowsOfBoxes = this.boxDimensions.getRows();
        int columnsOfBoxes = this.boxDimensions.getColumns();
        CellBox[][] puzzle = new CellBox[rowsOfBoxes][columnsOfBoxes];
        try {
            for (int i = 0; i < rowsOfBoxes; i++) {
                for (int j = 0; j < columnsOfBoxes; j++) {
                    puzzle[i][j] = new CellBox(cellBoxes[i][j]);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        this.cellBoxes = puzzle;
    }


    public Dimension getBoxDimensions() {
        return boxDimensions;
    }

    public void setBoxDimensions(Dimension boxDimensions) {
        this.boxDimensions = boxDimensions;
    }

    public Dimension getCellDimensions() {
        return cellDimensions;
    }

    public void setCellDimensions(Dimension cellDimensions) {
        this.cellDimensions = cellDimensions;
    }

    // End of getters and setters


    /* @method
     * Returns the CellBox object at the given row and column of the 2D array of CellBoxes.
     * @param i: The row of the CellBox object
     * @param j: The column of the CellBox object
     * @return: The CellBox object at the given row and column of the 2D array of CellBoxes
     * Usage: CellBox cellBox = cellBox2DArray.getCellBox(0,0);
     */
    public CellBox getCellBox(int i, int j) {
        return cellBoxes[i][j];
    }

    /* @method
     * Overloaded method that takes a Dimension object as a parameter and returns the CellBox object at the given row and column of the 2D array of CellBoxes.
     * @param dimension: The row and column of the CellBox object in a Dimension object
     * @return: The CellBox object at the given row and column of the 2D array of CellBoxes
     * Usage: CellBox cellBox = cellBox2DArray.getCellBox(new Dimension(0,0));
     */
    public CellBox getCellBox(Dimension dimension) {
        return getCellBox(dimension.getRows(), dimension.getColumns());
    }

    /* @method
     * Returns the Cell object at the given row and column of the 2D array of CellBoxes.
     * This method returns a Cell (Not a CellBox) by using the getCellBox method and then getCell method of the CellBox class. Please refer to the example below.
     * @param i: The row of the Cell object
     * @param j: The column of the Cell object
     * @return: The Cell object at the given row and column of the 2D array of CellBoxes
     * Usage: Cell cell = cellBox2DArray.getCellFromBigArray(0,0);
     * Example:
     * CellBox2DArray cellBox2DArray = new CellBox2DArray(new Dimension(3,3), new Dimension(3,3)); // Creates a 3x3 CellBox2DArray object with 3x3 CellBox objects (this is a 9x9 sudoku puzzle)
     * Cell cell = cellBox2DArray.getCellFromBigArray(0,0); // Returns the Cell object at the first row and first column of the puzzle
     * Cell cell2 = cellBox2DArray.getCellFromBigArray(5,4); // Returns the Cell object at the fifth row and forth column of the puzzle
     */
    public Cell getCellFromBigArray(int i, int j) {
        int boxRow = i / cellDimensions.getRows();
        int boxColumn = j / cellDimensions.getColumns();
        int inBoxRow = i % cellDimensions.getRows();
        int inBoxColumn = j % cellDimensions.getColumns();
        return getCellBox(boxRow, boxColumn).getCell(inBoxRow, inBoxColumn);
    }


    /* @method
     * Overloaded method that takes a Dimension object as a parameter and returns the Cell object at the given row and column of the 2D array of CellBoxes.
     * @param dimension: The row and column of the Cell object in a Dimension object
     * @return: The Cell object at the given row and column of the 2D array of CellBoxes
     * Usage: Cell cell = cellBox2DArray.getCellFromBigArray(new Dimension(0,0));
     * Example:
     * CellBox2DArray cellBox2DArray = new CellBox2DArray(new Dimension(3,3), new Dimension(3,3)); // Creates a 3x3 CellBox2DArray object with 3x3 CellBox objects (this is a 9x9 sudoku puzzle)
     * Cell cell = cellBox2DArray.getCellFromBigArray(new Dimension(0,0)); // Returns the Cell object at the first row and first column of the puzzle
     * Cell cell2 = cellBox2DArray.getCellFromBigArray(new Dimension(5,4)); // Returns the Cell object at the fifth row and forth column of the puzzle
     */
    public Cell getCellFromBigArray(Dimension dimension) {
        return getCellFromBigArray(dimension.getRows(), dimension.getColumns());
    }


    /* @method
     * Sets the content of the Cell object at the given row and column of the 2D array of CellBoxes.
     * This method sets the content of a Cell (Not a CellBox) by using the getCellBox method and then setCell method of the CellBox class. Please refer to the example below.
     * @param i: The row of the Cell object
     * @param j: The column of the Cell object
     * @param word: The WordPair object that will be set to the Cell object
     * Usage: cellBox2DArray.setCellFromBigArray(0,0, new WordPair("Hello", "Bonjour"));
     * // Sets the content of the Cell object at the first row and first column of the puzzle to a WordPair object corresponding "Hello" in English and "Bonjour" in French
     * */
    public void setCellFromBigArray(int i, int j, WordPair word) {
        getCellFromBigArray(i,j).setContent(word);
    }

    /* @method
     * Overloaded method that takes a Dimension object as a parameter and sets the content of the Cell object at the given row and column of the 2D array of CellBoxes.
     * @param dimension: The row and column of the Cell object in a Dimension object
     * @param word: The WordPair object that will be set to the Cell object
     * Usage: cellBox2DArray.setCellFromBigArray(new Dimension(0,0), new WordPair("Hello", "Bonjour"));
     * // Sets the content of the Cell object at the first row and first column of the puzzle to a WordPair object corresponding "Hello" in English and "Bonjour" in French
     */
    public void setCellFromBigArray(Dimension dimension, WordPair word) {
        setCellFromBigArray(dimension.getRows(), dimension.getColumns(), word);
    }


    /* @method
     * This method sets a Cell object to empty by using the getCellBox method and then setCell method of the CellBox class.
     * Because no WordPair object is passed as a parameter, the Cell object's content will be set to null by calling the clear method of the Cell class.
     * @param i: The row of the Cell object
     * @param j: The column of the Cell object
     * Usage: cellBox2DArray.setCellFromBigArray(3,8);
     * // Sets the content of the Cell object at the fourth row and ninth column of the puzzle to null
     */
    public void setCellFromBigArray(int i, int j) {
        getCellFromBigArray(i,j).clear();
    }

    /* @method
     * Returns the total number of rows of the Cells (not CellBoxes). This is the number of rows of the CellBox2DArray object multiplied by the number of rows of the CellBox objects.
     * @return: The total number of rows of the Cells
     * Example:
     * CellBox2DArray cellBox2DArray = new CellBox2DArray(new Dimension(3,3), new Dimension(3,3)); // Creates a 3x3 CellBox2DArray object with 3x3 CellBox objects (this is a 9x9 sudoku puzzle)
     * int rows = cellBox2DArray.getRows(); // Returns 9
     */
    public int getRows() {
        return boxDimensions.getRows() * cellDimensions.getRows();
    }

    /* @method
     * Returns the total number of columns of the Cells (not CellBoxes). This is the number of columns of the CellBox2DArray object multiplied by the number of columns of the CellBox objects.
     * @return: The total number of columns of the Cells
     * Example:
     * CellBox2DArray cellBox2DArray = new CellBox2DArray(new Dimension(4,4), new Dimension(4,4)); // Creates a 4x4 CellBox2DArray object with 4x4 CellBox objects (this is a 16x16 sudoku puzzle)
     * int columns = cellBox2DArray.getColumns(); // Returns 16
     */
    public int getColumns() {
        return boxDimensions.getColumns() * cellDimensions.getColumns();
    }

    //NOT USED
//    /* @method
//     * Sets the language of all cells to the given language.
//     * @param language: The language that will be set to all cells
//     * Usage: cellBox2DArray.setCellsLanguage(BoardLanguage.ENGLISH);
//     */
//    public void setCellsLanguage(int language){
//        for (int i = 0; i < getRows(); i++) {
//            for (int j = 0; j < getColumns(); j++) {
//                getCellBox(i,j).setCellsLanguage(language);
//            }
//        }
//    }

    /* @method
     *  Checks if all cells are filled. If all cells are filled, this method returns true. Otherwise, it returns false.
     *  If it encounters a cell that has a null content, it returns false.
     *  @return: True if all cells are filled, false otherwise
     */
    public boolean isFilled() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (getCellFromBigArray(i,j).getContent()== null) {
                    return false;
                }
            }
        }
        return true;
    }

    /* @helper method
     *  This method is used to initialize the 2D array of CellBoxes. It copies each cellBox object into each cell of the 2D array to avoid shallow copying.
     *  Used in the constructors.
     *  Used in the setCellBox method.
     */
    private void initializeCellBox(Dimension boxes, Dimension cells, CellBox cellBox) {
        int rowsOfBoxes = boxes.getRows();
        int columnsOfBoxes = boxes.getColumns();
        CellBox[][] puzzle = new CellBox[rowsOfBoxes][columnsOfBoxes];
        for (int i = 0; i < rowsOfBoxes; i++) {
            for (int j = 0; j < columnsOfBoxes; j++) {
                puzzle[i][j] = new CellBox(cellBox);
            }
        }
        this.cellBoxes = puzzle;
        setBoxDimensions(boxes);
        setCellDimensions(cells);
    }

    /* @method to covert the CellBoxes Object into json and each Dimension into json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("cellBoxes", convert2DCellBoxesToJson());
        json.put("boxDimensions", this.getBoxDimensions().toJson());
        json.put("cellDimensions", this.getCellDimensions().toJson());

        return json;
    }


    /* @utility to covert the CellBoxes Object into json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    private JSONArray convert2DCellBoxesToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (CellBox[] cellBoxes: this.getCellBoxes()) {
            jsonArray.put(convertCellBoxToJson(cellBoxes));
        }

        return jsonArray;
    }

    /* @utility to covert the every single CellBox Object into json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    private JSONArray convertCellBoxToJson(CellBox[] cellBoxes) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (CellBox c : cellBoxes) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellBox2DArray that = (CellBox2DArray) o;

        boolean boxDimensionEqual = boxDimensions.equals(that.boxDimensions);
        boolean cellDimensionsEqual = cellDimensions.equals(that.cellDimensions);
        boolean cellBoxesEqual = areCellBoxesEqual(this.getCellBoxes(), that.getCellBoxes());

        return cellBoxesEqual && boxDimensionEqual && cellDimensionsEqual;
    }


    public boolean areCellBoxesEqual(CellBox[][] one, CellBox[][] two) {

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
