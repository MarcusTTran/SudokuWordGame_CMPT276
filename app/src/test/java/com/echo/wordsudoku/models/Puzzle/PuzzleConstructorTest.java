package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.PuzzleParts.DimensionTest;
import com.echo.wordsudoku.models.TestUtils;
import com.echo.wordsudoku.models.WordPairTest;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.Test;

import java.util.List;

public class PuzzleConstructorTest extends PuzzleTest{
// <constructor tests>

    public Puzzle puzzle;

    // Makes a new puzzle with random word pairs, random size, random language, random number of initial cells and random difficulty
    @Test
    void makeNewLegalPuzzle() throws IllegalWordPairException, IllegalDimensionException, IllegalLanguageException, TooBigNumberException, NegativeNumberException {
        for (int i = 0; i < legalPuzzleSizeInt.length; i++) {
            for (int j = 0; j < legalPuzzleDifficulty.length; j++) {
                int size = legalPuzzleSizeInt[i];
                // Make puzzle with random difficulty
                puzzle = new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, legalPuzzleDifficulty[j]);
                assertNotNull(puzzle);
                // Make puzzle with random number of initial cells
                int[] numberOfInitialCellsArray = makeNumberOfStartCellsArray(size,LEGAL_NUMBER_OF_START_CELLS);
                for(int k: numberOfInitialCellsArray) {
                    puzzle = new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), k);
                    assertNotNull(puzzle);
                }
            }
        }
    }

    // Makes a new randomized puzzle with number of initial cells equal to total number of cells, which means a solved puzzle
    @Test
    void checkInitialCellsArrayZero() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        // Make puzzle with random difficulty
        puzzle = new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), size*size);
        assertTrue(puzzle.getGameResult().getResult());
    }

    // Makes a new puzzle with **null word pairs**, random size, random language, random number of initial cells and random difficulty
    // Should throw IllegalArgumentException
    @Test
    void makeNewNullWordPairListPuzzle() {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        assertThrows(IllegalWordPairException.class,()->new Puzzle(null, size , TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty)));
    }

    @Test
    void makeNewRepeatedWordListPuzzle() {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size-1);
        wordPairList.add(wordPairList.get(0));
        assertThrows(IllegalWordPairException.class,()->new Puzzle(wordPairList, size , TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty)));
    }

    // Makes a new puzzle with **too small word pairs**, random size, random language, random number of initial cells and random difficulty
    // Should throw IllegalArgumentException
    @Test
    void makeNewTooSmallWordPairListPuzzle() {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        assertThrows(IllegalWordPairException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size- MathUtils.getRandomNumberBetweenIncluding(1,4)), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty)));
    }

    /* Broken test
    // Makes a new puzzle with random word pairs, **illegal size**, random language, random number of initial cells and random difficulty
    // Should throw IllegalArgumentException
    @Test
    void makeNewIllegalSizePuzzle() {
        int size = TestUtils.getRandomIntElement(illegalPuzzleSizeInt);
        assertThrows(IllegalDimensionException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty)), "Illegal size: " + size);
    }
     */

    // Makes a new puzzle with random word pairs, random size, **illegal language**, random number of initial cells and random difficulty
    // Should throw IllegalArgumentException
    @Test
    void makeNewIllegalLanguagePuzzle() {
        int language = illegalPuzzleLanguage;
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        assertThrows(IllegalLanguageException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, language, Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty)));
    }

    @Test
    void makeNewIllegalPuzzleDifficulty() {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        int difficulty = TestUtils.getRandomIntElement(illegalPuzzleDifficulty);
        assertThrows(IllegalArgumentException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, difficulty));
    }

    // Makes a new puzzle with random word pairs, random size, random language, **illegal number of initial cells**, random difficulty
    // Because in Puzzle constructor, we call getTrimmedBoard() which takes as an argument the number of cells to remove, the exceptions thrown are counter-intuitive
    // Should throw IllegalArgumentException
    @Test
    void makeNewIllegalNumberOfInitialCellsPuzzle() {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        int[] numberOfInitialCellsArray = makeNumberOfStartCellsArray(size,ILLEGAL_NUMBER_OF_START_CELLS);
        assertThrows(TooBigNumberException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), numberOfInitialCellsArray[0]));
        assertThrows(NegativeNumberException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), numberOfInitialCellsArray[1]));
        assertThrows(NegativeNumberException.class,()->new Puzzle(WordPairTest.makeRandomWordPairList(size), size, TestUtils.getRandomIntElement(legalPuzzleLanguage), numberOfInitialCellsArray[2]));
    }


    // Checks that the copy constructor makes a deep copy
    @Test
    void testCopyConstructorMakesDeepCopy() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size);
        Puzzle puzzle = new Puzzle(wordPairList, size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty));
        Puzzle puzzleCopy = new Puzzle(puzzle);
        puzzle.unlockCells(); // Make the puzzle editable
        Dimension cellToInsertIn = DimensionTest.makeRandomDimension(size);
        puzzle.setCell(cellToInsertIn, TestUtils.getRandomElements(wordPairList,1).get(0).getEnglish());
        assertNotEquals(puzzle.getUserBoard().getCellFromBigArray(cellToInsertIn), puzzleCopy.getUserBoard().getCellFromBigArray(cellToInsertIn));
    }


    // This constructor is used when we read saved game from a json file. We need to make sure that the puzzle is valid
    @Test
    void checkBareBonesConstructorMakesValidPuzzle() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size);
        Puzzle puzzle = new Puzzle(wordPairList, size, TestUtils.getRandomIntElement(legalPuzzleLanguage), Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY, TestUtils.getRandomIntElement(legalPuzzleDifficulty));
        Puzzle puzzleCopy = new Puzzle(puzzle.getUserBoard(),puzzle.getSolutionBoard(),puzzle.getWordPairs(),puzzle.getPuzzleDimensions(),puzzle.getLanguage(),puzzle.getMistakes(),puzzle.getTimer());
        assertTrue(puzzle.equals(puzzleCopy));
        Dimension cellToInsertIn = DimensionTest.makeRandomDimension(size);
        puzzle.unlockCells(); // Make the puzzle editable
        puzzle.setCell(cellToInsertIn, TestUtils.getRandomElements(wordPairList,1).get(0).getEnglish());
        assertNotEquals(puzzle.getUserBoard().getCellFromBigArray(cellToInsertIn), puzzleCopy.getUserBoard().getCellFromBigArray(cellToInsertIn));

        // Try to make a puzzle with invalid dimension and everything else valid
        assertThrows(IllegalDimensionException.class,()->new Puzzle(puzzle.getUserBoard(),puzzle.getSolutionBoard(),puzzle.getWordPairs(),illegalPuzzleSize[0],puzzle.getLanguage(),puzzle.getMistakes(),puzzle.getTimer()));
    }


    // </constructor tests>
}
