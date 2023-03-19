package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.TestUtils;
import com.echo.wordsudoku.models.WordPairTest;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.CellBox2DArray;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PuzzleSettersTest extends PuzzleTest{

    public static Dimension[] illegalBoxDimension = {new Dimension(3,5),new Dimension(7,7),new Dimension(0,0)};
    public static Dimension[] illegalNegativeBoxDimension = {new Dimension(-1,10),new Dimension(-9,-9)};
    public static Dimension[] legalBoxDimension = {new Dimension(2,2),new Dimension(2,3),new Dimension(3,3),new Dimension(3,4)};

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
    void setUserBoardInvalidDimensionCellBox2DArray() {
        Dimension illegalSizeBox = TestUtils.getRandomElements(Arrays.asList(illegalBoxDimension),1).get(0);
        Dimension illegalSizeEachBox = TestUtils.getRandomElements(Arrays.asList(illegalBoxDimension),1).get(0).conjugate();
        Dimension illegalSizeNegativeBox = TestUtils.getRandomElements(Arrays.asList(illegalNegativeBoxDimension),1).get(0);
        assertThrows(IllegalDimensionException.class, () -> puzzle.setUserBoard(new CellBox2DArray(illegalSizeBox,illegalSizeBox.conjugate())));
        assertThrows(IllegalDimensionException.class, () -> puzzle.setUserBoard(new CellBox2DArray(illegalSizeBox,illegalSizeEachBox)));
        assertThrows(NegativeNumberException.class, () -> puzzle.setUserBoard(new CellBox2DArray(illegalSizeNegativeBox,illegalSizeNegativeBox.conjugate())));

        Dimension anotherLegalSizeBox = TestUtils.getRandomElements(Arrays.asList(legalBoxDimension),1).get(0);
        while (anotherLegalSizeBox.equals(puzzle.getPuzzleDimensions().getEachBoxDimension())){
            anotherLegalSizeBox = TestUtils.getRandomElements(Arrays.asList(legalBoxDimension),1).get(0);
        }
        Dimension finalAnotherLegalSizeBox = anotherLegalSizeBox;
        assertThrows(IllegalDimensionException.class, () -> puzzle.setUserBoard(new CellBox2DArray(finalAnotherLegalSizeBox,finalAnotherLegalSizeBox.conjugate())));
    }

    @Test
    void setUserBoardValidDimensionCellBox2DArray() throws IllegalDimensionException, NegativeNumberException {
        PuzzleDimensions puzzleDimensions = puzzle.getPuzzleDimensions();
        puzzle.setUserBoard(new CellBox2DArray(puzzleDimensions.getBoxesInPuzzleDimension(),puzzleDimensions.getEachBoxDimension()));
        assertEquals(puzzle.getUserBoard().getCellDimensions(),puzzleDimensions.getEachBoxDimension());
        assertEquals(puzzle.getUserBoard().getBoxDimensions(),puzzleDimensions.getBoxesInPuzzleDimension());
    }


    @Test
    void setSafeUserBoardInvalidWordPairCellBox2DArray() throws NegativeNumberException {
        CellBox2DArray cellBox2DArrayWithInvalidWordPair = new CellBox2DArray(puzzle.getPuzzleDimensions());
        cellBox2DArrayWithInvalidWordPair.setCellFromBigArray(new Dimension(0,0),DUMMY_WORD_PAIR);
        assertThrows(IllegalWordPairException.class, () -> puzzle.setSafeUserBoard(cellBox2DArrayWithInvalidWordPair));
    }
/* Broken test
    @Test
    void setSafeUserBoardValidDimensionCellBox2DArray() throws IllegalDimensionException, NegativeNumberException, IllegalWordPairException {
        PuzzleDimensions puzzleDimensions = puzzle.getPuzzleDimensions();
        List<WordPair> wordPairList = puzzle.getWordPairs();
        CellBox2DArray cellBox2DArray = new CellBox2DArray(puzzleDimensions.getBoxesInPuzzleDimension(),puzzleDimensions.getEachBoxDimension());
        for (int i = 0;i<wordPairList.size();i++) {
            cellBox2DArray.setCellFromBigArray(new Dimension(i,0),wordPairList.get(i));
        }
        puzzle.setSafeUserBoard(cellBox2DArray);
        assertEquals(puzzle.getUserBoard().getCellDimensions(),puzzleDimensions.getEachBoxDimension());
        assertEquals(puzzle.getUserBoard().getBoxDimensions(),puzzleDimensions.getBoxesInPuzzleDimension());
        assertEquals(puzzle.getUserBoard().getAllWordPairs(),wordPairList);
    }
    */

    @Test
    void setLanguageInvalidLanguage() {
        assertThrows(IllegalLanguageException.class, () -> puzzle.setLanguage(illegalPuzzleLanguage));
    }

    @Test
    void setLanguageValidLanguage() throws IllegalLanguageException {
        for (int i : legalPuzzleLanguage) {
            puzzle.setLanguage(i);
            assertEquals(puzzle.getLanguage(),i);
        }
    }

    @Test
    void setTimerNegativeTime() {
        assertThrows(NegativeNumberException.class, () -> puzzle.setTimer(-1));
    }

    @Test
    void setTimerValidTime() throws NegativeNumberException {
        puzzle.setTimer(0);
        assertEquals(puzzle.getTimer(),0);
        puzzle.setTimer(1);
        assertEquals(puzzle.getTimer(),1);
        puzzle.setTimer(100);
        assertEquals(puzzle.getTimer(),100);
    }


}
