package com.echo.wordsudoku.models;

import java.util.ArrayList;
import java.util.List;

public class CellBox {
    private Cell[][] cells;
    private final Dimension dimension;

    public CellBox(WordPair content,int rows, int columns,int language) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(content,language);
            }
        }
    }

    public CellBox(WordPair content,int rows, int columns) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(content);
            }
        }
    }

    public CellBox(int rows, int columns,int language) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(language);
            }
        }
    }

    public CellBox(CellBox cellBox) {
        this.dimension = new Dimension(cellBox.dimension.getRows(), cellBox.dimension.getColumns());
        this.cells = new Cell[cellBox.dimension.getRows()][cellBox.dimension.getColumns()];
        for (int i = 0; i < cellBox.dimension.getRows(); i++) {
            for (int j = 0; j < cellBox.dimension.getColumns(); j++) {
                cells[i][j] = new Cell(cellBox.getCell(i, j));
            }
        }
    }

    public CellBox(int rows, int columns) {
        this.dimension = new Dimension(rows, columns);
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public CellBox(Dimension dimension) {
        this(dimension.getRows(), dimension.getColumns());
    }

    public void setCells(Cell[][] cells) throws IllegalArgumentException {
        // Check if the given cells is a square and has the same dimension as the cellSquareArray
        if (cells.length != dimension.getRows() || cells[0].length != dimension.getColumns())
            throw new IllegalArgumentException("Invalid dimension");
        this.cells = cells;
    }

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

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public void setCellsLanguage(int language) {
        for (int i = 0; i < this.dimension.getRows(); i++) {
            for (int j = 0; j < this.dimension.getColumns(); j++) {
                cells[i][j].setLanguage(language);
            }
        }
    }

    private boolean hasEmptyCells() {
        for (int i = 0; i < this.dimension.getRows(); i++) {
            for (int j = 0; j < this.dimension.getColumns(); j++) {
                if (cells[i][j].isEmpty())
                    return true;
            }
        }
        return false;
    }
}
