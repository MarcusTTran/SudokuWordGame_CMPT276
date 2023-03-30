package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.TestUtils;
import com.echo.wordsudoku.models.WordPairTest;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PuzzleUtilityTest extends PuzzleTest{
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
    public void testIsPuzzleFilled() throws IllegalWordPairException, IllegalDimensionException {
        assertFalse(puzzle.isPuzzleFilled());
        puzzle.fillUserBoardRandomly();
        assertTrue(puzzle.isPuzzleFilled());
    }

    @Test
    public void testIsPuzzleSolved() throws IllegalWordPairException, IllegalDimensionException {
        assertFalse(puzzle.isPuzzleSolved());
        puzzle.fillUserBoardRandomly();
        assertFalse(puzzle.isPuzzleSolved());
        puzzle.solve();
        assertTrue(puzzle.isPuzzleSolved());
    }

    @Test
    public void testGetGameResult() throws IllegalWordPairException, IllegalDimensionException {
        // Test for a not solved puzzle which has no mistakes
        GameResult gameResult = puzzle.getGameResult();
        assertFalse(gameResult.getResult());
        assertTrue(gameResult.getMistakes()==0);
        // Test for a randomly filled puzzle which has mistakes
        puzzle.fillUserBoardRandomly();
        gameResult = puzzle.getGameResult();
        assertFalse(gameResult.getResult());
        assertTrue(gameResult.getMistakes()>0);
        // Test for a solved puzzle which has no mistakes
        puzzle.solve();
        gameResult = puzzle.getGameResult();
        assertTrue(gameResult.getResult());
        assertTrue(gameResult.getMistakes()==0);
    }

    @Test
    public void testToStringArray() {
        String[][] stringArray = puzzle.toStringArray(false);
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        assertTrue(stringArray.length == size);
        assertTrue(stringArray[0].length == size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = puzzle.getUserBoard().getCellFromBigArray(i,j);
                if(cell.getContent()!=null)
                    assertTrue(cell.getContent().doesContain(stringArray[i][j]));
                else
                    assertTrue(stringArray[i][j].equals(""));
            }
        }
    }

    //TODO : Add test for isSudokuValid
    //TODO: Add test for isJthColumnValid
    //TODO: Add test for isIthRowValid
    //TODO: Add test for isCellBoxValid
    //TODO: Add test for findEmptyCell


}
