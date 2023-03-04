package com.echo.wordsudoku.models.Memory;


import android.content.Context;

import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonReader {

    // The source file name
    private String source;

    // The file name to read the json object
    private final String FILENAME = "puzzle.json";

    // The file reader to read the JSON object from the file
    private FileReader fileReader;

    // The file to read the JSON object from the file
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


    /**
     * Reads the puzzle from the source file and returns it.
     *
     * @return The puzzle
     * @throws IOException If an I/O error occurs
     * @throws JSONException If the JSON object is invalid
     */
    public Puzzle readPuzzle() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePuzzle(jsonObject);
    }


    /**
     * Reads the file with the given file name and returns the content as a string.
     * inpired from : https://medium.com/@nayantala259/android-how-to-read-and-write-parse-data-from-json-file-226f821e957a
     * @param source The file name to read
     * @return The content of the file
     * @throws IOException If an I/O error occurs
     */
    private String readFile(String source) throws IOException {
        // Create a file reader
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        // Create a string builder
        StringBuilder stringBuilder = new StringBuilder();
        // Read the first line
        String line = bufferedReader.readLine();

        // Read the file line by line
        while (line != null){
            // Append the line to the string builder
            stringBuilder.append(line).append("\n");
            // Read the next line
            line = bufferedReader.readLine();
        }

        // Close the reader
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

        /*
        json.put("userBoard", this.getUserBoard().toJson());
        json.put("solutionBoard", this.getSolutionBoard().toJson());
        json.put("wordPairs", convertWordPairsToJson());
        json.put("puzzleDimension", this.getPuzzleDimension().toJson());
        json.put("language", this.getLanguage());
        json.put("mistakes", this.getMistakes());
         */

        try {
            // user board
            //JSONArray userBoardJSON = jsonObject.getJSONArray("userBoard");

            // solution board
            JSONArray wordPairsJSON = (jsonObject.getJSONArray("wordPairs"));
            List<WordPair> wordPairs= parseWordPairs(wordPairsJSON);
            // PuzzleDimension
            JSONObject puzzleDimensionJSON = (jsonObject.getJSONObject("puzzleDimensions"));
            PuzzleDimensions puzzleDimensions = parsePuzzleDimension(puzzleDimensionJSON);

            // parse language
            int language = (jsonObject.getInt("language"));
            // parse mistakes
            int mistakes = (jsonObject.getInt("mistakes"));



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null; // stub
    }

    /**
     * Parses the given JSON array and returns a list of WordPairs.
     *
     * @param wordPairs The JSON array to parse
     * @return The list of WordPairs
     * @throws JSONException If the JSON object is invalid
     */
    private List<WordPair> parseWordPairs(JSONArray wordPairs) throws JSONException {
        List<WordPair> wordPairsList = new ArrayList<>();
        for (int i = 0; i < wordPairs.length(); i++) {
            JSONObject wordPair_json = wordPairs.getJSONObject(i);
            String eng = wordPair_json.getString("eng");
            String fre = wordPair_json.getString("fre");
            WordPair wordPair = new WordPair(eng, fre);

            // FOR TESTING
            //System.out.println(wordPair.getEnglish() + " " + wordPair.getFrench());

            wordPairsList.add(wordPair);
        }


        return wordPairsList;
    }

    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param dimension The JSON object to parse
     * @return The new Dimension
     * @throws JSONException If the JSON object is invalid
     */
    private PuzzleDimensions parsePuzzleDimension(JSONObject dimension) throws JSONException {

        int puzzleDimension = dimension.getInt("puzzleDimension");
        JSONObject eachBoxDimensionJSON = dimension.getJSONObject("eachBoxDimension");
        JSONObject boxesInPuzzleDimensionJSON = dimension.getJSONObject("boxesInPuzzleDimension");

        Dimension eachBoxDimension = parseDimension(eachBoxDimensionJSON);
        Dimension boxesInPuzzleDimension = parseDimension(boxesInPuzzleDimensionJSON);



        return new PuzzleDimensions(puzzleDimension, eachBoxDimension, boxesInPuzzleDimension);
    }

    private Dimension parseDimension(JSONObject eachBoxDimensionJSON) throws JSONException {
        int row = eachBoxDimensionJSON.getInt("rows");
        int col = eachBoxDimensionJSON.getInt("columns");
        return new Dimension(row, col);
    }


    // getter for the source file name
    public String getSrouce() {
        return this.source;
    }

}
