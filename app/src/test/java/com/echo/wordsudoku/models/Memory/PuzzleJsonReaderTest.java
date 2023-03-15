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

public class PuzzleJsonReaderTest {

    final static int TWELVE = 12;
    final static int NINE = 9;
    final static int SIX = 6;
    final static int FOUR = 4;

    private final String PUZZLE_JSON_SAVE_FILENAME = "wsudoku_puzzleTest.json";

    private final int TAB = 4;

    private Context context;


    @BeforeEach
    void setUpContext() {
        this.context = Mockito.mock(Context.class);
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



    // test if a valid puzzle saved correctly
    @Test
    public void testPuzzleReaderWithValidPuzzle() throws IOException, JSONException {
        // set a temporary directory for testing
        Mockito.when(context.getFilesDir()).thenReturn(new File("/tmp"));

        // test if the directory is not null
        assertNotNull(context.getFilesDir());

        //construct the puzzle
        Puzzle puzzle = constructPuzzle(NINE);

        // create file
        File file_saved = new File(context.getFilesDir(),PUZZLE_JSON_SAVE_FILENAME);

        // create a JsonObject out of puzzle
        JSONObject jsonObject = puzzle.toJson();

        // write the string Object into the file
        FileUtils.stringToPrintWriter(new PrintWriter(file_saved),jsonObject.toString(TAB));


        // file loaded
        File file_loaded = new File(context.getFilesDir(),PUZZLE_JSON_SAVE_FILENAME);

        Puzzle puzzleSaved;
        // retrieving the saved puzzle
        PuzzleJsonReader puzzleJsonReader = new PuzzleJsonReader(FileUtils.inputStreamToString(new FileInputStream(file_loaded)));

        puzzleSaved = puzzleJsonReader.readPuzzle();

        assertTrue(puzzle.equals(puzzleSaved));

    }
}
