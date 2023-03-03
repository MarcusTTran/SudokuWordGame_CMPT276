package com.echo.wordsudoku.models.Memory;

import com.echo.wordsudoku.models.sudoku.Puzzle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class JsonWriter {


    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    public JsonWriter (String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(destination);
    }

    /*
     * @method write the Puzzle as json object to the file
     */
    public void writePuzzle(Puzzle puzzle) throws JSONException {
        JSONObject json = puzzle.toJson();
        this.saveFile(json.toString(TAB));
    }

    // @method closes the PrintWriter
    public void close() {
        this.writer.close();
    }

    // @method writes the String json to destination file
    private void saveFile(String json) {
        this.writer.print(json);
    }

}
