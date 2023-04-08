package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.TestUtils;
import com.echo.wordsudoku.models.PuzzleParts.WordPairTest;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.CellBox2DArray;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PuzzleCoreTest extends PuzzleTest {

    @BeforeAll
    public static void init() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size);
        puzzle = new Puzzle(wordPairList, size, TestUtils.getRandomIntElement(legalPuzzleLanguage), size);
    }

    @BeforeEach
    public void resetPuzzle() {
        puzzle.resetPuzzle(false);
    }

    @Test
    public void testSetCellInvalidWordPairString(){
        //Find the first editable cell
        Dimension dimension = puzzle.findEmptyCell(puzzle.getUserBoard());
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension, DUMMY_WORD_PAIR));
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension, DUMMY_WORD_PAIR.getLang1()));
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension, DUMMY_WORD_PAIR.getLang2()));
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension.getRows(),dimension.getColumns(), DUMMY_WORD_PAIR));
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension.getRows(),dimension.getColumns(), DUMMY_WORD_PAIR.getLang1()));
        assertThrows(IllegalWordPairException.class,()->puzzle.setCell(dimension.getRows(),dimension.getColumns(), DUMMY_WORD_PAIR.getLang2()));
    }

    @Test
    public void testSetCellInvalidDimension(){
        WordPair wordPair = puzzle.getWordPairs().get(0);
        //Find the first editable cell
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION,wordPair ));
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION,wordPair.getLang1() ));
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION,wordPair.getLang2() ));
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION.getRows(),ILLEGAL_NEGATIVE_ONE_DIMENSION.getColumns(),wordPair ));
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION.getRows(),ILLEGAL_NEGATIVE_ONE_DIMENSION.getColumns(),wordPair.getLang1() ));
        assertThrows(IllegalDimensionException.class,()->puzzle.setCell(ILLEGAL_NEGATIVE_ONE_DIMENSION.getRows(),ILLEGAL_NEGATIVE_ONE_DIMENSION.getColumns(),wordPair.getLang2() ));
    }

    @Test
    public void testSetCellLockedCell() {
        puzzle.resetPuzzle(true);
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        WordPair wordPair = puzzle.getWordPairs().get(0);

        //Find the first editable cell
        Dimension dimension = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!puzzle.getUserBoard().getCellFromBigArray(i,j).isEditable()) {
                    dimension = new Dimension(i, j);
                    break;
                }
            }
        }
        if(dimension!=null) {
            Dimension finalDimension = dimension;
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension, wordPair));
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension, wordPair.getLang1()));
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension, wordPair.getLang2()));
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension.getRows(), finalDimension.getColumns(), wordPair));
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension.getRows(), finalDimension.getColumns(), wordPair.getLang1()));
            assertThrows(IllegalDimensionException.class, () -> puzzle.setCell(finalDimension.getRows(), finalDimension.getColumns(), wordPair.getLang2()));
        }
    }

    @Test
    public void testSetCellValidWordPairValidDimension() throws IllegalWordPairException, IllegalDimensionException {
        WordPair wordPair = puzzle.getWordPairs().get(0);
        Dimension dimension = puzzle.findEmptyCell(puzzle.getUserBoard());
        puzzle.setCell(dimension, wordPair);
        assertTrue(puzzle.getUserBoard().getCellFromBigArray(dimension.getRows(),dimension.getColumns()).getContent().equals(wordPair));
        resetPuzzle();
        puzzle.setCell(dimension, wordPair.getLang1());
        assertTrue(puzzle.getUserBoard().getCellFromBigArray(dimension.getRows(),dimension.getColumns()).getContent().equals(wordPair));
        resetPuzzle();
        puzzle.setCell(dimension, wordPair.getLang2());
        assertTrue(puzzle.getUserBoard().getCellFromBigArray(dimension.getRows(),dimension.getColumns()).getContent().equals(wordPair));
        resetPuzzle();
        puzzle.setCell(dimension.getRows(),dimension.getColumns(), wordPair);
        assertTrue(puzzle.getUserBoard().getCellFromBigArray(dimension.getRows(),dimension.getColumns()).getContent().equals(wordPair));
        resetPuzzle();
        puzzle.setCell(dimension.getRows(),dimension.getColumns(), wordPair.getLang1());
        assertTrue(puzzle.getUserBoard().getCellFromBigArray(dimension.getRows(),dimension.getColumns()).getContent().equals(wordPair));
        resetPuzzle();
        puzzle.setCell(dimension.getRows(),dimension.getColumns(), wordPair.getLang2());
    }

    @Test
    public void testIsIthRowNotContainingInvalidDimension() {
        WordPair wordPair = puzzle.getWordPairs().get(0);
        // Should not accept negative numbers
        assertThrows(IllegalDimensionException.class,()->puzzle.isIthRowNotContaining(puzzle.getSolutionBoard(),illegalPuzzleSizeInt[1], wordPair));
        // The last legal row is size-1
        assertThrows(IllegalDimensionException.class,()->puzzle.isIthRowNotContaining(puzzle.getSolutionBoard(),puzzle.getPuzzleDimensions().getPuzzleDimension(),wordPair ));
    }

    @Test
    public void testIsIthRowNotContainingInvalidWordPair() {
        int row = puzzle.getPuzzleDimensions().getPuzzleDimension()-1;
        // Should not accept negative numbers
        assertThrows(IllegalWordPairException.class,()->puzzle.isIthRowNotContaining(puzzle.getSolutionBoard(),row, null));
        // The last legal row is size-1
        assertThrows(IllegalWordPairException.class,()->puzzle.isIthRowNotContaining(puzzle.getSolutionBoard(),row,null ));
    }

    @Test
    public void testIsIthRowNotContainingValidDimensionValidWordPair() throws IllegalWordPairException, IllegalDimensionException {
        int row = puzzle.getPuzzleDimensions().getPuzzleDimension()-1;
        WordPair wordPair = puzzle.getWordPairs().get(0);
        CellBox2DArray solutionBoard = puzzle.getSolutionBoard();
        assertFalse(Puzzle.isIthRowNotContaining(solutionBoard,row, wordPair));
        assertTrue(Puzzle.isIthRowNotContaining(solutionBoard,row, DUMMY_WORD_PAIR));
    }

    @Test
    public void testIsJthColumnNotContainingValidDimensionValidWordPair() throws IllegalWordPairException, IllegalDimensionException {
        int column = puzzle.getPuzzleDimensions().getPuzzleDimension()-1;
        WordPair wordPair = puzzle.getWordPairs().get(0);
        CellBox2DArray solutionBoard = puzzle.getSolutionBoard();
        assertFalse(Puzzle.isJthColumnNotContaining(solutionBoard,column, wordPair));
        assertTrue(Puzzle.isJthColumnNotContaining(solutionBoard,column, DUMMY_WORD_PAIR));
    }

    @Test
    public void testIsJthColumnNotContainingInvalidWordPair() {
        int column = puzzle.getPuzzleDimensions().getPuzzleDimension()-1;
        // Should not accept negative numbers
        assertThrows(IllegalWordPairException.class,()->puzzle.isJthColumnNotContaining(puzzle.getSolutionBoard(),column, null));
        // The last legal row is size-1
        assertThrows(IllegalWordPairException.class,()->puzzle.isJthColumnNotContaining(puzzle.getSolutionBoard(),column,null ));
    }

    @Test
    public void testIsJthColumnNotContainingInvalidDimension() {
        WordPair wordPair = puzzle.getWordPairs().get(0);
        // Should not accept negative numbers
        assertThrows(IllegalDimensionException.class,()->puzzle.isJthColumnNotContaining(puzzle.getSolutionBoard(),illegalPuzzleSizeInt[1], wordPair));
        // The last legal row is size-1
        assertThrows(IllegalDimensionException.class,()->puzzle.isJthColumnNotContaining(puzzle.getSolutionBoard(),puzzle.getPuzzleDimensions().getPuzzleDimension(),wordPair ));
    }

    // Makes sure that the puzzle is solved for all of the legal puzzle sizes
    @Test
    public void testSolveBoard() throws NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        CellBox2DArray board;
        for (int i = 0;i<legalPuzzleSize.length;i++) {
            PuzzleDimensions size = legalPuzzleSize[i];
            List<WordPair> wordPairs = WordPairTest.makeRandomWordPairList(size.getPuzzleDimension());
            board = new CellBox2DArray(size,WordPair.LANG1);
            assertTrue(Puzzle.SolveBoard(board,wordPairs));
            assertTrue(Puzzle.isSudokuValid(board,wordPairs));
        }
    }

    @Test
    public void testGetTrimmedBoardInvalidCellsToRemove() throws NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        CellBox2DArray board = new CellBox2DArray(legalPuzzleSize[0],WordPair.LANG1);
        Puzzle.SolveBoard(board,WordPairTest.makeRandomWordPairList(legalPuzzleSize[0].getPuzzleDimension()));
        // Should not accept negative numbers
        assertThrows(NegativeNumberException.class,()->puzzle.getTrimmedBoard(board,illegalPuzzleSizeInt[1],legalPuzzleLanguage[0]));
        // Should not accept numbers greater than the size of the board
        assertThrows(TooBigNumberException.class,()->puzzle.getTrimmedBoard(board,illegalPuzzleSizeInt[4],legalPuzzleLanguage[0]));
    }

    @Test
    public void testGetTrimmedBoardValidCellsToRemove() throws NegativeNumberException, IllegalWordPairException, IllegalDimensionException, IllegalLanguageException, TooBigNumberException {
        // We are going to create a board, solve it, and then remove a random number of cells
        PuzzleDimensions size = TestUtils.getRandomElements(Arrays.asList(legalPuzzleSize),1).get(0);
        CellBox2DArray board = new CellBox2DArray(size,WordPair.LANG1);
        List<WordPair> wordPairs = WordPairTest.makeRandomWordPairList(size.getPuzzleDimension());
        Puzzle.SolveBoard(board,wordPairs);
        int cellsToRemove = MathUtils.getRandomNumberBetweenIncluding(0,(int)Math.pow(size.getPuzzleDimension(),2));
        CellBox2DArray trimmedBoard = puzzle.getTrimmedBoard(board,cellsToRemove,WordPair.LANG2);

        // Check to see if the correct number of cells were removed
        int cellsRemoved = 0;
        for (int i = 0;i<size.getPuzzleDimension();i++) {
            for (int j = 0;j<size.getPuzzleDimension();j++) {
                if (trimmedBoard.getCellFromBigArray(i,j).getContent()==null) {
                    cellsRemoved++;
                }
            }
        }
        assertEquals(cellsRemoved,cellsToRemove);
    }



}
