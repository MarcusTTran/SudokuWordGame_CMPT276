package com.echo.wordsudoku.models.Puzzle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.sudoku.CellBox;
import com.echo.wordsudoku.models.sudoku.CellBox2DArray;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PuzzleUtilityTest extends PuzzleTest{

    private final int illegalWordPairListSize = 7;


    @BeforeAll
    public static void init() throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        int size = TestUtils.getRandomIntElement(legalPuzzleSizeInt);
        List<WordPair> wordPairList = WordPairTest.makeRandomWordPairList(size);
        puzzle = new Puzzle(wordPairList, size, TestUtils.getRandomIntElement(legalPuzzleLanguage), size);
        puzzle.unlockCells();
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
    public void testToStringArray() throws IllegalWordPairException, IllegalDimensionException {
        String[][] stringArray = puzzle.toStringArray();
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

        puzzle.setTextToSpeechOn(true);
        stringArray = puzzle.toStringArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = puzzle.getUserBoard().getCellFromBigArray(i,j);
                if(cell.getContent()!=null)
                    assertTrue(puzzle.getWordPairs().indexOf(cell.getContent())+1==Integer.parseInt(stringArray[i][j]));
                else
                    assertTrue(stringArray[i][j].equals(""));
            }
        }

        puzzle.solve();
        stringArray = puzzle.toStringArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = puzzle.getUserBoard().getCellFromBigArray(i,j);
                if(cell.getContent()!=null)
                    if (!cell.isEditable())
                        assertTrue(puzzle.getWordPairs().indexOf(cell.getContent())+1==Integer.parseInt(stringArray[i][j]));
                    else
                        assertTrue(cell.getContent().doesContain(stringArray[i][j]));
                else
                    assertTrue(stringArray[i][j].equals(""));
            }
        }
    }

    @Test
    public void testIsSudokuValid() throws IllegalWordPairException, IllegalDimensionException {
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        // Testing invalid cases
        assertThrows(IllegalWordPairException.class, () -> Puzzle.isSudokuValid(puzzle.getUserBoard(),null));
        // The size of wordPairList is not equal to the size of the puzzle
        assertThrows(IllegalWordPairException.class, () -> Puzzle.isSudokuValid(puzzle.getSolutionBoard(),WordPairTest.makeRandomWordPairList(illegalWordPairListSize)));
        assertThrows(NullPointerException.class, () -> Puzzle.isSudokuValid(null,WordPairTest.makeRandomWordPairList(size)));
        assertThrows(IllegalDimensionException.class,()-> Puzzle.isSudokuValid(new CellBox2DArray(new Dimension(legalPuzzleSizeInt[0],legalPuzzleSizeInt[0]),new Dimension(illegalWordPairListSize,illegalWordPairListSize)),WordPairTest.makeRandomWordPairList(legalPuzzleSizeInt[0]*illegalWordPairListSize)));

        // Testing valid cases
        makeOneCellInvalid(-1,-1);
        assertFalse(Puzzle.isSudokuValid(puzzle.getUserBoard(),puzzle.getWordPairs()));
        for (int i = 1; i < size-1; i++) {
            makeRowsInvalid(i);
            assertFalse(Puzzle.isSudokuValid(puzzle.getUserBoard(),puzzle.getWordPairs()));
            makeColumnsInvalid(i);
            assertFalse(Puzzle.isSudokuValid(puzzle.getUserBoard(),puzzle.getWordPairs()));
            makeBoxesInvalid(i);
            assertFalse(Puzzle.isSudokuValid(puzzle.getUserBoard(),puzzle.getWordPairs()));
        }
    }

    public void makeRowsInvalid(int numberOfRows) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.solve();
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        List<Integer> rowsToInvalidate = TestUtils.getRandomElements(TestUtils.makeListOfPuzzleSizeInt(size),numberOfRows);
        for (Integer i : rowsToInvalidate) {
            WordPair wordPair = puzzle.getWordPairs().get(i);
            for (int j = 0; j < size; j++) {
                puzzle.setCell(i,j,wordPair.getLang1());
            }
        }
    }

    public void makeColumnsInvalid(int numberOfColumns) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.solve();
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        List<Integer> columnsToInvalidate = TestUtils.getRandomElements(TestUtils.makeListOfPuzzleSizeInt(size),numberOfColumns);
        for (Integer i : columnsToInvalidate) {
            WordPair wordPair = puzzle.getWordPairs().get(i);
            for (int j = 0; j < size; j++) {
                puzzle.setCell(j,i,wordPair.getLang1());
            }
        }
    }

    public void makeBoxesInvalid(int numberOfBoxes) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.solve();
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        List<Integer> boxesToInvalidate = TestUtils.getRandomElements(TestUtils.makeListOfPuzzleSizeInt(size),numberOfBoxes);
        for (Integer i : boxesToInvalidate) {
            WordPair wordPair = puzzle.getWordPairs().get(i);
            for (int j = 0; j < size; j++) {
                Dimension boxesDimension= puzzle.getPuzzleDimensions().getBoxesInPuzzleDimension();
                Dimension cellsDimension = puzzle.getPuzzleDimensions().getEachBoxDimension();
                puzzle.setCell((i/boxesDimension.getColumns())*cellsDimension.getRows()+j/cellsDimension.getColumns(),i/boxesDimension.getRows()+j%cellsDimension.getColumns(),wordPair.getLang1());
            }
        }
    }

    public void makeOneCellInvalid(int i, int j) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.solve();
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        int row,column;
        if(i==-1)
            row = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
        else
            row = i;
        if(j==-1)
            column = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
        else
            column = j;
        WordPair wordPair = puzzle.getUserBoard().getCellFromBigArray(row,(column+1)%size).getContent();
        puzzle.setCell(row,column,wordPair.getLang1());
    }

    @Test
    public void testIsJthColumnValid() throws IllegalWordPairException, IllegalDimensionException {
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        for (int i = 0; i < size; i++) {
            puzzle.solve();
            makeOneCellInvalid(-1,i);
            assertFalse(Puzzle.isJthColumnValid(puzzle.getUserBoard(),i));
        }
    }

    @Test
    public void testIsIthRowValid() throws IllegalWordPairException, IllegalDimensionException {
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        for (int i = 0; i < size; i++) {
            puzzle.solve();
            makeOneCellInvalid(i,-1);
            assertFalse(Puzzle.isIthRowValid(puzzle.getUserBoard(),i));
        }
    }

    @Test
    public void testIsCellBoxValid() throws IllegalWordPairException, IllegalDimensionException {
        int size = puzzle.getPuzzleDimensions().getPuzzleDimension();
        for (int i = 0; i < size; i++) {
            puzzle.solve();
            CellBox2DArray board = puzzle.getUserBoard();
            int rowIndex = i/ board.getCellDimensions().getRows();
            int columnIndex = i/ board.getCellDimensions().getColumns();
            CellBox cellBox = board.getCellBox(rowIndex,columnIndex);
            makeACellBoxInvalid(cellBox);
            assertFalse(Puzzle.isCellBoxValid(cellBox));
        }
    }

    public void makeACellBoxInvalid(CellBox cellBox) {
        int size = cellBox.getCells().length;
        int row = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
        int column = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
        int anotherRow,anotherColumn;
        WordPair wordPair = cellBox.getCell(row,column).getContent();
        do {
            anotherRow = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
            anotherColumn = MathUtils.getRandomNumberBetweenIncluding(0,size-1);
        } while (anotherRow==row || anotherColumn==column);
        cellBox.getCell(anotherRow,anotherColumn).setContent(wordPair);
    }

    @Test
    public void testFindEmptyCell() throws IllegalWordPairException, IllegalDimensionException {
        puzzle.solve();
        assertNull(Puzzle.findEmptyCell(puzzle.getUserBoard()));
        puzzle.resetPuzzle(false);
        assertNotNull(Puzzle.findEmptyCell(puzzle.getUserBoard()));
    }

}
