package com.echo.wordsudoku.models.Puzzle;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import java.util.ArrayList;
import java.util.List;


public class PuzzleTest {
    public static int[] legalPuzzleSizeInt = {4,6,9,12};
    public static PuzzleDimensions[] legalPuzzleSize;

    public static Puzzle puzzle = null;

    public static Dimension ILLEGAL_NEGATIVE_ONE_DIMENSION = new Dimension(-1,-1);

    static {
        try {
            legalPuzzleSize = new PuzzleDimensions[]{new PuzzleDimensions(4),new PuzzleDimensions(6),new PuzzleDimensions(9),new PuzzleDimensions(12)};
        } catch (IllegalDimensionException e) {
            throw new RuntimeException(e);
        }
    }

    public static PuzzleDimensions[] illegalPuzzleSize = {new PuzzleDimensions(-1000000,new Dimension(-1000,1000),new Dimension(1000,-1000))
            ,new PuzzleDimensions(-1,new Dimension(-1,1),new Dimension(1,-1)),
            new PuzzleDimensions(0,new Dimension(0,0),new Dimension(0,0)),
            new PuzzleDimensions(3,new Dimension(3,3),new Dimension(3,3)),
            new PuzzleDimensions(9,new Dimension(5,4),new Dimension(4,5)),
            new PuzzleDimensions(1000000,new Dimension(1000,1000),new Dimension(1000,1000))
    };

    public static int[] illegalPuzzleSizeInt = {-1000000,-1,0,3,1000000};

    public static int[] legalPuzzleDifficulty = {1,2,3,4,5};
    public static int[] illegalPuzzleDifficulty = {-90,-1,0,9,140};
    public static int[] legalPuzzleLanguage = {0,1};
    public static int illegalPuzzleLanguage = -1;
    public static final int LEGAL_NUMBER_OF_START_CELLS = 1;
    public static final int ILLEGAL_NUMBER_OF_START_CELLS = -1;

    public static WordPair DUMMY_WORD_PAIR = new WordPair("dummy","dummy");

    /*

     */

    // <utility>

    public static int[] makeNumberOfStartCellsArray(int size,int flag) {
        int[] numberOfInitialCellsArray = null;

        if (flag == LEGAL_NUMBER_OF_START_CELLS) {
            numberOfInitialCellsArray = new int[size];
            for (int i = 0; i < size; i++) {
                numberOfInitialCellsArray[i] = MathUtils.getRandomNumberBetweenIncluding(i * size, (i + 1) * size);
            }
        } else if (flag == ILLEGAL_NUMBER_OF_START_CELLS) {
            numberOfInitialCellsArray = new int[3];
            numberOfInitialCellsArray[0] = -1;
            numberOfInitialCellsArray[1] = size * size + 1;
            numberOfInitialCellsArray[2] = size * size * 3;
        }
            /*
        else if (flag == HALF_LEGAL_NUMBER_OF_START_CELLS) {
            for (int i = 0; i < size; i++) {
                numberOfInitalCellsArray[i] = (int) Math.pow(1.5, (double) i);
            }
        }*/
        return numberOfInitialCellsArray;
    }

    public List<WordPair> makeDummyIncrementalWordPairList(int size) {
        List<WordPair> wordPairList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            wordPairList.add(new WordPair("eng"+i,"fre"+i));
        }
        return wordPairList;
    }

    // </utility>


}