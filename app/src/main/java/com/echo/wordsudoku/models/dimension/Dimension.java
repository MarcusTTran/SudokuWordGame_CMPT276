package com.echo.wordsudoku.models.dimension;

import com.echo.wordsudoku.models.json.Writable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Dimension class represents the x,y dimensions.
 * It can represent the number of rows and columns in a 2D array (e.g. a CellBox object) or
 * CellBox2DArray object which is a 2D array of CellBox objects.
 * It also contains the methods to get the number of rows and columns of the board.
 * It improves the readability of the code when we are dealing with the number of rows and columns
 * of a 2D array and when we want to get a specific cell from the 2D array.
 *
 * Usage:
 * Dimension dimension = new Dimension(rows, columns); // or Dimension dimension = new Dimension(new int[]{rows, columns});
 * dimension.getRows();
 * dimension.getColumns();
 *
 * @author eakbarib
 *
 * @version 1.0
 */

public class Dimension implements Writable {

    // rows can be used in two ways:
    // x coordinate of a cell in a 2D array.
    // or the number of rows of the 2D array of cells.
    private int rows;


    // columns can be used in two ways:
    // y coordinate of a cell in a 2D array.
    // or the number of columns of the 2D array of cells.
    private int columns;

    public Dimension(Dimension dimension) {
        this.rows = dimension.getRows();
        this.columns = dimension.getColumns();
    }

    /*
     * @constructor
     * Constructs a Dimension object with the given number of rows and columns (or x,y).
     * @param rows: the number of rows of the 2D array of cells or the x coordinate of a cell in a 2D array.
     * @param columns: the number of columns of the 2D array of cells or the y coordinate of a cell in a 2D array.
     *  Usage: Dimension dimension = new Dimension(rows, columns);
     * */
    public Dimension(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }


    /*
     * @constructor
     * Constructs a Dimension object with the given number of rows and columns (or x,y) with an array of two integers as the input.
     * @param dimensionArray: an array of two integers which represent the number of rows and columns of the 2D array of cells or the (x,y) coordinates of a cell in a 2D array.
     * Usage: Dimension dimension = new Dimension(new int[]{rows, columns});
     * */
    public Dimension(int[] dimensionArray) {
        if (dimensionArray.length == 2) {
            this.rows = dimensionArray[0];
            this.columns = dimensionArray[1];
        }
        else {
            throw new IllegalArgumentException("Invalid dimension array");
        }
    }

    // Getters and setters

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("rows", this.getRows());
        json.put("columns", this.getColumns());
        return json;
    }

    // End of getters and setters


}
