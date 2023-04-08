package com.echo.wordsudoku.models.Memory;

import static org.junit.Assert.*;

import android.content.Context;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.file.FileUtils;
import com.echo.wordsudoku.models.json.PuzzleJsonReader;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Test the PuzzleJsonReader class
 * @author Kousha Amouzesh
 * @version 1.0
 *
 */
public class PuzzleJsonReaderTest {

    final static int TWELVE_BY_TWELVE = 12;
    final static int NINE_BY_NINE = 9;
    final static int SIX_BY_SIX = 6;
    final static int FOUR_BY_FOUR = 4;


    Puzzle constructPuzzle(int dimension) throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {

        int difficulty = 1; // easy
        int puzzleLanguage = 1;
        int puzzleDimension = dimension;

        // arbitrary wordPairs for testing
        List<WordPair> wordPairs = new ArrayList<>();

        // add 9 word pairs
        for (int i = 0; i < dimension; i++) {
            wordPairs.add(new WordPair("eng"+ i,"fre"+ i));
        }

        // construct puzzle
        Puzzle puzzle = new Puzzle(wordPairs,puzzleDimension,puzzleLanguage,(dimension*dimension)-2,difficulty);

        return puzzle;
    }

    @Test
    void testFourByFourPuzzle () throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {

        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(FOUR_BY_FOUR)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }

    }


    @Test
    void testNineByNinePuzzle () throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(NINE_BY_NINE)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }

    }

    @Test
    void testSixBySixPuzzle () throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(SIX_BY_SIX)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }
    }
    @Test
    void testTwelveByTwelvePuzzle () throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {

        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(TWELVE_BY_TWELVE)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }
    }


    // test null string
    @Test
    void testInvalidJsonObjectNullObject() {
        try {
            PuzzleJsonReader puzzleJsonReader = new PuzzleJsonReader(null);
            fail("PuzzleJsonReader shouldn't read empty json string");
        } catch (NullPointerException e){
            // expected
        } catch (JSONException e) {
            fail("null pointer comes before json exception");
        }
    }


    // test improperly formatted json puzzle
    @Test
    void testInvalidPuzzleJsonObject() {
        assertThrows(JSONException.class, () -> {
            PuzzleJsonReader puzzleJsonReader = new PuzzleJsonReader("{puzzleInvalid} : bad");
            Puzzle puzzle = puzzleJsonReader.readPuzzle();
        });

    }


    // test if a valid puzzle saved correctly
    boolean isPuzzleTheReadPuzzleTheSameAfterSave(Puzzle puzzle) throws IOException, JSONException {

        // create a JsonObject out of puzzle
        JSONObject jsonObject = puzzle.toJson();

        // retrieving the saved puzzle
        PuzzleJsonReader puzzleJsonReader =
                new PuzzleJsonReader(jsonObject.toString());

        Puzzle puzzleSaved = puzzleJsonReader.readPuzzle();

        boolean equal = puzzle.equals(puzzleSaved);
        // the loaded Puzzle must be the same as what was saved
        return equal;
    }

}
