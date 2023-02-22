package com.echo.wordsudoku.models;

import java.util.List;

public class CellBox2DArray{
    private CellBox[][] cellBoxes;
    private Dimension boxDimensions;

    private Dimension cellDimensions;

    public CellBox2DArray(CellBox[][] cellBoxes, Dimension boxes, Dimension cells) {
        int rowsOfBoxes = boxes.getRows();
        int columnsOfBoxes = boxes.getColumns();
        CellBox[][] puzzle = new CellBox[rowsOfBoxes][columnsOfBoxes];
        for (int i = 0; i < rowsOfBoxes; i++) {
            for (int j = 0; j < columnsOfBoxes; j++) {
                puzzle[i][j] = new CellBox(cellBoxes[i][j]);
            }
        }
        setCellBoxes(puzzle);
        setBoxDimensions(boxes);
        setCellDimensions(cells);
    }

    public CellBox2DArray(Dimension boxes, Dimension cells,int language) {
        int rowsOfBoxes = boxes.getRows();
        int columnsOfBoxes = boxes.getColumns();
        CellBox[][] puzzle = new CellBox[rowsOfBoxes][columnsOfBoxes];
        for (int i = 0; i < rowsOfBoxes; i++) {
            for (int j = 0; j < columnsOfBoxes; j++) {
                puzzle[i][j] = new CellBox(cells.getRows(), cells.getColumns(),language);
            }
        }
        setCellBoxes(puzzle);
        setBoxDimensions(boxes);
        setCellDimensions(cells);
    }

    public CellBox2DArray(Dimension boxes, Dimension cells) {
        int rowsOfBoxes = boxes.getRows();
        int columnsOfBoxes = boxes.getColumns();
        CellBox[][] puzzle = new CellBox[rowsOfBoxes][columnsOfBoxes];
        for (int i = 0; i < rowsOfBoxes; i++) {
            for (int j = 0; j < columnsOfBoxes; j++) {
                puzzle[i][j] = new CellBox(cells.getRows(), cells.getColumns());
            }
        }
        setCellBoxes(puzzle);
        setBoxDimensions(boxes);
        setCellDimensions(cells);
    }

    public CellBox2DArray(PuzzleDimensions puzzleDimensions,int language) {
        this(puzzleDimensions.getBoxesInPuzzleDimension(), puzzleDimensions.getEachBoxDimension(),language);
        setBoxDimensions(puzzleDimensions.getBoxesInPuzzleDimension());
        setCellDimensions(puzzleDimensions.getEachBoxDimension());
    }

    public CellBox2DArray(PuzzleDimensions puzzleDimensions) {
        this(puzzleDimensions.getBoxesInPuzzleDimension(), puzzleDimensions.getEachBoxDimension());
        setBoxDimensions(puzzleDimensions.getBoxesInPuzzleDimension());
        setCellDimensions(puzzleDimensions.getEachBoxDimension());
    }

    public CellBox[][] getCellBoxes() {
        return cellBoxes;
    }

    public void setCellBoxes(CellBox[][] cellBoxes) {
        this.cellBoxes = cellBoxes;
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

    public CellBox getCellBox(int i, int j) {
        return cellBoxes[i][j];
    }

    public CellBox getCellBox(Dimension dimension) {
        return getCellBox(dimension.getRows(), dimension.getColumns());
    }

    public Cell getCellFromBigArray(int i, int j) {
        int boxRow = i / cellDimensions.getRows();
        int boxColumn = j / cellDimensions.getColumns();
        int inBoxRow = i % cellDimensions.getRows();
        int inBoxColumn = j % cellDimensions.getColumns();
        return getCellBox(boxRow, boxColumn).getCell(inBoxRow, inBoxColumn);
    }

    public Cell getCellFromBigArray(Dimension dimension) {
        return getCellFromBigArray(dimension.getRows(), dimension.getColumns());
    }

    public void setCellFromBigArray(int i, int j, WordPair word) {
        getCellFromBigArray(i,j).setContent(word);
    }

    public void setCellFromBigArray(int i, int j) {
        setCellFromBigArray(i,j, null);
    }

    public void setCellFromBigArray(Dimension dimension, WordPair word) {
        setCellFromBigArray(dimension.getRows(), dimension.getColumns(), word);
    }

    public int getRows() {
        return boxDimensions.getRows() * cellDimensions.getRows();
    }

    public int getColumns() {
        return boxDimensions.getColumns() * cellDimensions.getColumns();
    }

    public void setCellsLanguage(int language){
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                getCellBox(i,j).setCellsLanguage(language);
            }
        }
    }

    public void setRow(int i, List<WordPair> words){
        for (int j = 0; j < getColumns(); j++) {
            getCellFromBigArray(i,j).setContent(words.get(j));
        }
    }

    public void setColumn(int j, List<WordPair> words){
        for (int i = 0; i < getRows(); i++) {
            getCellFromBigArray(i,j).setContent(words.get(i));
        }
    }

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

}
