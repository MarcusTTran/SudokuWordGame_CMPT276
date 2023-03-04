package com.echo.wordsudoku.models.Memory;


import android.content.Context;
import android.os.Build;

import com.echo.wordsudoku.models.sudoku.Puzzle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {

    // The source file name
    private String source;

    // The file name to read the json object
    private final String FILENAME = "puzzle.json";

    private FileReader fileReader;

    private File file;

    /**
     * Constructs a new JsonReader that reads from the file with the given file name.
     *
     * @param context The context of the application
     */
    public JsonReader(Context context) throws FileNotFoundException {
        this.source = context.getFilesDir() + "/" + FILENAME;
        this.file = new File(context.getFilesDir(),FILENAME);
        this.fileReader = new FileReader(file);
        System.out.println(source);
    }


    public Puzzle readPuzzle() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePuzzle(jsonObject);
    }



    private String readFile(String source) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }

        bufferedReader.close();

        // This respond will have Json Format String
        String content = stringBuilder.toString();

        return content;
    }


    /**
     * Parses the given JSON object and returns a new Puzzle.
     *
     * @param jsonObject The JSON object to parse
     * @return The new Puzzle
     */
    private Puzzle parsePuzzle(JSONObject jsonObject) {
        return null;
    }




    // getter for the source file name
    public String getSrouce() {
        return this.source;
    }

}
