package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class PuzzleGettersTest extends PuzzleTest{

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
    public void checkGetUserBoard() {
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                WordPair content = puzzle.getUserBoard().getCellFromBigArray(new Dimension(i,j)).getContent();
                if(content!=null)
                    assertTrue(puzzle.getWordPairs().indexOf(content)!=-1);
            }
        }
        // To make sure we don't get a filled board
        puzzle.resetPuzzle(false);
        assertFalse(puzzle.getUserBoard().isFilled());
    }

    @Test
    public void checkGetSolutionBoard() throws IllegalDimensionException {
        CellBox2DArray solutionBoard = puzzle.getSolutionBoard();
        puzzle.setUserBoard(solutionBoard);
        assertTrue(puzzle.getGameResult().getResult());
        assertTrue(solutionBoard.isFilled());
    }

    @Test
    public void checkGetMistakes() throws IllegalWordPairException, IllegalDimensionException {
        puzzle.fillUserBoardRandomly();
        Puzzle copyPuzzle = new Puzzle(puzzle);
        assertEquals(puzzle.getMistakes(),copyPuzzle.getMistakes());
    }

    @Test
    public void checkGetPuzzleDimension() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        PuzzleDimensions puzzleDimensions = TestUtils.getRandomElements(Arrays.asList(legalPuzzleSize),1).get(0);
        Puzzle newPuzzle = new Puzzle(WordPairTest.makeRandomWordPairList(puzzleDimensions.getPuzzleDimension()),puzzleDimensions.getPuzzleDimension(),TestUtils.getRandomIntElement(legalPuzzleLanguage),Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,TestUtils.getRandomIntElement(legalPuzzleDifficulty));
        assertEquals(newPuzzle.getPuzzleDimensions(),puzzleDimensions);
    }

    @Test
    public void checkGetPuzzleLanguage() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int language = TestUtils.getRandomIntElement(legalPuzzleLanguage);
        Puzzle newPuzzle = new Puzzle(WordPairTest.makeRandomWordPairList(puzzle.getPuzzleDimensions().getPuzzleDimension()),puzzle.getPuzzleDimensions().getPuzzleDimension(),language,Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,TestUtils.getRandomIntElement(legalPuzzleDifficulty));
        assertEquals(newPuzzle.getLanguage(),language);
    }

    @Test
    public void checkGetWordPairs() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size);
        Puzzle newPuzzle = new Puzzle(wordPairList,size,TestUtils.getRandomIntElement(legalPuzzleLanguage),Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,TestUtils.getRandomIntElement(legalPuzzleDifficulty));
        assertEquals(newPuzzle.getWordPairs(),wordPairList);
    }

    @Test
    public void checkGetTimer() throws NegativeNumberException {
        int timer = 1200;
        puzzle.setTimer(timer);
        assertEquals(puzzle.getTimer(),timer);
    }
}


