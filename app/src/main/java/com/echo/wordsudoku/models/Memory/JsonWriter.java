package com.echo.wordsudoku.models.Memory;

import android.content.Context;

import com.echo.wordsudoku.models.sudoku.Puzzle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The JsonWriter class writes the Puzzle as json object to the file
 * @author kousha amouzesh
 * @version 1.0
 */

public class JsonWriter {



    // The number of spaces for indentation
    private static final int TAB = 4;

    // The file writer to write the JSON object to the file
    private PrintWriter writer;

    private String destination;

    public JsonWriter(Context context) {
        String filename = "puzzle.json";
        File file = new File(context.getFilesDir(), filename);
        this.destination = file.getAbsolutePath();
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /**
     * Writes the given puzzle as a JSON object to a file with the specified file name.
     *
     * @param puzzle The puzzle to write
     * @throws JSONException If there is an error converting the puzzle to a JSON object
     * @throws IOException If there is an error writing the JSON object to the file
     */
    public void writePuzzle(Puzzle puzzle) throws JSONException, IOException {
        JSONObject json = puzzle.toJson();
        saveFile(json.toString(TAB));
    }

    /**
     * Closes the file writer.
     *
     * @throws IOException If there is an error closing the file writer
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Writes the given JSON string to a file with the specified file name.
     *
     * @param json The JSON string to write
     * @throws IOException If there is an error writing the JSON string to the file
     */
    private void saveFile(String json) throws IOException {
        // Write the JSON string to the file
        writer.print(json);

    }
}
