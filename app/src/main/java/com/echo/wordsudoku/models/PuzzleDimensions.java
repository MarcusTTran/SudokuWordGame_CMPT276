package com.echo.wordsudoku.models;

public class PuzzleDimensions {
    private int puzzleDimension;
    private Dimension eachBoxDimension;
    private Dimension boxesInPuzzleDimension;

    public PuzzleDimensions(int puzzleDimension) {
        setPuzzleDimension(puzzleDimension);
    }
    public int getPuzzleDimension() {
        return puzzleDimension;
    }

    public void setPuzzleDimension(int puzzleDimension) {
        if (MathUtils.isPrimeNumber(puzzleDimension))
            throw new IllegalArgumentException("Puzzle dimension cannot be a prime number");
        if (Puzzle.ACCEPTABLE_DIMENSIONS.contains(puzzleDimension) == false)
            throw new IllegalArgumentException("Invalid puzzle dimension. Check Puzzle.ACCEPTABLE_DIMENSIONS for acceptable dimensions");
        this.puzzleDimension = puzzleDimension;
        setEachBoxAndBoxesInPuzzleDimension();
    }

    public Dimension getEachBoxDimension() {
        return eachBoxDimension;
    }


    public Dimension getBoxesInPuzzleDimension() {
        return boxesInPuzzleDimension;
    }


    // Get the dimension of each box in puzzle. A 6x6 puzzle has 2x3 boxes.
    private Dimension getBoxDimension(int puzzleDimension) {
        if (MathUtils.isPerfectSquare(puzzleDimension)) {
            return new Dimension((int) Math.sqrt(puzzleDimension),(int) Math.sqrt(puzzleDimension));
        } else {
            return new Dimension(MathUtils.getMiddleFactors(puzzleDimension));
        }
    }

    // Get the dimension of boxes in puzzle. A 6x6 puzzle has 3x2 of 2x3 boxes.
    // So the return value is {3,2}
    private Dimension getBoxesInPuzzleDimension(int puzzleDimension) {
        Dimension boxDimension = getBoxDimension(puzzleDimension);
        return new Dimension(puzzleDimension / boxDimension.getRows(), puzzleDimension / boxDimension.getColumns());
    }

    private void setEachBoxAndBoxesInPuzzleDimension() {
        this.eachBoxDimension = getBoxDimension(puzzleDimension);
        this.boxesInPuzzleDimension = getBoxesInPuzzleDimension(puzzleDimension);
    }
}
