package com.echo.wordsudoku.models;

public class Dimension {

    private int rows;
    private int columns;

    public Dimension(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public Dimension(int[] dimensionArray) {
        if (dimensionArray.length == 2) {
            this.rows = dimensionArray[0];
            this.columns = dimensionArray[1];
        }
        else {
            throw new IllegalArgumentException("Invalid dimension array");
        }
    }

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

}
