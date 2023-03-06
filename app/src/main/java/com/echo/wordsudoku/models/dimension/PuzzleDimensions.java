package com.echo.wordsudoku.models.dimension;

/*
 * PuzzleDimensions class represents the dimensions of the puzzle.
 * A PuzzleDimensions object always holds legal values for the puzzle dimension, the dimension of each box in the puzzle, and the dimension of the boxes in the puzzle.
 * For example, if the puzzle dimension is 4, the dimension of each box in the puzzle is 2x2, and the dimension of the boxes in the puzzle is 2x2.
 * As another example, if the puzzle dimension is 9, the dimension of each box in the puzzle is 3x3, and the dimension of the boxes in the puzzle is 3x3.
 * As another example, if the puzzle dimension is 12, the dimension of each box in the puzzle is 3x4, and the dimension of the boxes in the puzzle is 4x3.
 * By dimension of boxes in puzzle, we are referring to the arrangement of the actual boxes in the puzzle.
 * By dimension of each box in the puzzle, we are referring to the number of rows and columns of each box in the puzzle.
 *
 * Please use the below graph to understand the difference between the dimension of the puzzle, the dimension of each box in the puzzle, and the dimension of the boxes in the puzzle in a
 * 6x6 sudoku puzzle where each box in the puzzle is a 2x3 box:
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
 * ║                   ║                   ║
 * ----  CellBox  ----------  CellBox  -----
 * ║                   ║                   ║
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯                                           ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯                                                          -----------------------
 * ║                   ║                   ║               Let us take a CellBox       ║                    ║           Let us break this CellBox into 6 Cells         |  Cell | Cell | Cell |
 * ----  CellBox  ---------  CellBox  ------               ---------->>>>>>>>>>>       ----   CellBox  ------            ---------------->>>>>>>>>>>>>>>>>>>>          -----------------------
 * ║                   ║                   ║                                           ║                    ║                                                          |  Cell | Cell | Cell |
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯                                           ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯                                                          -----------------------
 * ║                   ║                   ║
 * ----  CellBox  ----------  CellBox  -----
 * ║                   ║                   ║
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
 *
 * It contains the dimension of the puzzle (puzzleDimension: int), the dimension of each box in the puzzle (eachBoxDimension: Dimension),
 * and the dimension of the boxes in the puzzle (boxesInPuzzleDimension: Dimension).
 *
 * PuzzleDimensions class is immutable.
 * PuzzleDimensions = {puzzleDimension: "The dimension of the puzzle"
 * , eachBoxDimension: "The dimension of each box in the puzzle"
 * , boxesInPuzzleDimension: "The dimension of the boxes in the puzzle"
 * }
 *
 * @author: eakbarib
 *
 * @version: 1.0
 *
 */

import com.echo.wordsudoku.models.MathUtils;
import com.echo.wordsudoku.models.Memory.Writable;
import com.echo.wordsudoku.models.sudoku.Puzzle;

import org.json.JSONException;
import org.json.JSONObject;

public class PuzzleDimensions implements Writable {

    // The dimension of the puzzle. A 6x6 puzzle has a puzzle dimension of 6.
    private int puzzleDimension;

    // The dimension of each box in the puzzle. A 6x6 puzzle has a dimension of 2x3 for each box in the puzzle.
    private Dimension eachBoxDimension;

    // The dimension of the boxes in the puzzle. A 6x6 puzzle has a dimension of 3x1 for the boxes in the puzzle. (To make the boxes fit perfectly it needs to be opposite of eachBoxDimension)
    private Dimension boxesInPuzzleDimension;

    public PuzzleDimensions(PuzzleDimensions puzzleDimensions) {
        this.puzzleDimension = puzzleDimensions.puzzleDimension;
        this.eachBoxDimension = new Dimension(puzzleDimensions.eachBoxDimension);
        this.boxesInPuzzleDimension = new Dimension(puzzleDimensions.boxesInPuzzleDimension);
    }

    /* @constructor
     * Prepares the correct dimensions for the requested puzzle dimension.
     * @param: puzzleDimension: int
     * @throws: IllegalArgumentException if puzzleDimension is a prime number or if puzzleDimension is not in Puzzle.ACCEPTABLE_DIMENSIONS = {4,6,9,12}
     */
    public PuzzleDimensions(int puzzleDimension) {
        setPuzzleDimension(puzzleDimension);
    }


    // @constructor used by JSON reader to directly construct the PuzzleDimensions object from a JSONObject
    // Prepares the correct dimensions for the requested puzzle dimension.
    // @param: puzzleDimension: int
    // @param: eachBoxDimension: Dimension
    // @param: boxesInPuzzleDimension: Dimension
    public PuzzleDimensions(int puzzleDimension, Dimension eachBoxDimension, Dimension boxesInPuzzleDimension) {
        this.puzzleDimension = puzzleDimension;
        this.eachBoxDimension = eachBoxDimension;
        this.boxesInPuzzleDimension = boxesInPuzzleDimension;
    }


    // Getters
    // No setters because we want this class to be immutable.
    // Otherwise the dimension of the puzzle will be changed and there might be no legal puzzle for those dimensions.
    public int getPuzzleDimension() {
        return puzzleDimension;
    }

    public Dimension getEachBoxDimension() {
        return eachBoxDimension;
    }


    public Dimension getBoxesInPuzzleDimension() {
        return boxesInPuzzleDimension;
    }

    // End of getters

    //@method
    // Set the puzzle dimension and prepare the correct dimensions for the requested puzzle dimension by calling the helper methods that calculate the dimensions using math.
    // @param: puzzleDimension: int (The dimension of the puzzle)
    // @throws: IllegalArgumentException if puzzleDimension is a prime number or if puzzleDimension is not in Puzzle.ACCEPTABLE_DIMENSIONS = {4,6,9,12}
    public void setPuzzleDimension(int puzzleDimension) {
        if (MathUtils.isPrimeNumber(puzzleDimension))
            throw new IllegalArgumentException("Puzzle dimension cannot be a prime number");
        if (Puzzle.ACCEPTABLE_DIMENSIONS.contains(puzzleDimension) == false)
            throw new IllegalArgumentException("Invalid puzzle dimension. Check Puzzle.ACCEPTABLE_DIMENSIONS for acceptable dimensions");
        this.puzzleDimension = puzzleDimension;
        this.eachBoxDimension = getBoxDimension(puzzleDimension);
        this.boxesInPuzzleDimension = getBoxesInPuzzleDimension(puzzleDimension);
    }

    /* @utility method
     * Get the dimension of cells in each box in puzzle. A 6x6 puzzle has 2x3 boxes. The way to get the dimension of each box is to find the factors of the puzzle dimension. Then we select the two middle factors as the dimension for boxes.
     * @param: puzzleDimension: int
     * @return: Dimension
     * */
    private Dimension getBoxDimension(int puzzleDimension) {
        if (MathUtils.isPerfectSquare(puzzleDimension)) {
            return new Dimension((int) Math.sqrt(puzzleDimension),(int) Math.sqrt(puzzleDimension));
        } else {
            return new Dimension(MathUtils.getMiddleFactors(puzzleDimension));
        }
    }

    /* @utility method
     * Get the dimension of boxes in puzzle. A 6x6 puzzle has 3x2 of 2x3 boxes. The way to get the dimension of boxes in puzzle is to divide the puzzle dimension by the dimension of each box to make them fit.
     * @param: puzzleDimension: int
     * @return: Dimension
     * */
    private Dimension getBoxesInPuzzleDimension(int puzzleDimension) {
        Dimension boxDimension = getBoxDimension(puzzleDimension);
        return new Dimension(puzzleDimension / boxDimension.getRows(), puzzleDimension / boxDimension.getColumns());
    }

    /* @method to covert the PuzzleDimension Object into json and each Dimension into json
     * @returns JSONObject the json object used to write into .json file within the puzzle
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("puzzleDimension", this.getPuzzleDimension());
        json.put("eachBoxDimension", this.getEachBoxDimension().toJson());
        json.put("boxesInPuzzleDimension", this.getBoxesInPuzzleDimension().toJson());
        return json;
    }
}
