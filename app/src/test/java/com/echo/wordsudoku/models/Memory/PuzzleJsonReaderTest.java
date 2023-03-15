package com.echo.wordsudoku.models.Memory;

import static org.junit.Assert.*;

import android.content.Context;

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

    private final String PUZZLE_JSON_SAVE_FILENAME = "wsudoku_puzzleTest.json";

    private final int TAB = 4;

    private Context context;

    private File file_saved;


    @BeforeEach
    void setUpContext() {
        this.context = Mockito.mock(Context.class);
        // set a temporary directory for testing
        Mockito.when(context.getFilesDir()).thenReturn(new File("/tmp"));

        // test if the directory is not null
        assertNotNull(context.getFilesDir());

        // create file
        this.file_saved = new File(context.getFilesDir(),PUZZLE_JSON_SAVE_FILENAME);
    }

    Puzzle constructPuzzle(int dimension){

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
        Puzzle puzzle = new Puzzle(wordPairs,puzzleDimension,puzzleLanguage,-1,difficulty);

        return puzzle;
    }

    @Test
    void testFourByFourPuzzle () {

        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(FOUR_BY_FOUR)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }

    }


    @Test
    void testInvalidAddress() {

    }

    @Test
    void testNineByNinePuzzle () {
        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(NINE_BY_NINE)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }

    }

    @Test
    void testSixBySixPuzzle () {
        try {
            assertTrue(isPuzzleTheReadPuzzleTheSameAfterSave(constructPuzzle(SIX_BY_SIX)));
        } catch (JSONException e) {
            fail("json must be valid");

        } catch (IOException e) {
            fail("file should be found");
        }
    }
    @Test
    void testTwelveByTwelvePuzzle () {

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
            //expected

        }

    }


    // test improperly formatted json puzzle
    @Test
    void testInvalidPuzzleJsonObject() {
        try {
            PuzzleJsonReader puzzleJsonReader = new PuzzleJsonReader("{puzzleInvalid} : bad");
            Puzzle puzzle = puzzleJsonReader.readPuzzle();
        } catch (Exception e){ // JsonException is caught and RunTimeException is Thrown
            //expected
            e.printStackTrace();

        }

    }


    // test if a valid puzzle saved correctly
    boolean isPuzzleTheReadPuzzleTheSameAfterSave(Puzzle puzzle) throws IOException, JSONException {

        // create a JsonObject out of puzzle
        JSONObject jsonObject = puzzle.toJson();

        // write the string Object into the file
        FileUtils.stringToPrintWriter(new PrintWriter(file_saved),jsonObject.toString(TAB));

        // file loaded
        File file_loaded = new File(context.getFilesDir(),PUZZLE_JSON_SAVE_FILENAME);

        Puzzle puzzleSaved;

        // retrieving the saved puzzle
        PuzzleJsonReader puzzleJsonReader =
                new PuzzleJsonReader(FileUtils.inputStreamToString(new FileInputStream(file_loaded)));

        puzzleSaved = puzzleJsonReader.readPuzzle();

        // the loaded Puzzle must be the same as what was saved
        return (puzzle.equals(puzzleSaved));
    }


}
