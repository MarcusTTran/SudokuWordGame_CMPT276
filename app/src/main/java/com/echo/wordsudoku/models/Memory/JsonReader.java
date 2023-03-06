package com.echo.wordsudoku.models.Memory;

import android.content.Context;

import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.sudoku.CellBox;
import com.echo.wordsudoku.models.sudoku.CellBox2DArray;
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

/**
 * The JsonReader class reads the Puzzle as json object from the file
 *
 * how to call use the JsonReader: ('this' is the context of the application)
 *
 *  !!! for use in Puzzle activity make sure the the mWordPairs is assigned to the loaded puzzle's word pairs
 *  !!! use get word pairs from the loaded puzzle to assign the word buttons in the puzzle activity
 *
 *          // read the puzzle from a json file
 *
 *         try {
 *             jsonReader = new JsonReader(this);
 *             mPuzzle = jsonReader.readPuzzle();
 *
 *         } catch (IOException e) {
 *             throw new RuntimeException(e);
 *         } catch (JSONException e) {
 *             throw new RuntimeException(e);
 *         }
 *
 * @author kousha amouzesh
 * @version 1.0
 */

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
        // The source file name accessed from the context
        this.source = context.getFilesDir() + "/" + FILENAME;
        // initialize the file given the name
        this.file = new File(context.getFilesDir(),FILENAME);
        // initialize the file reader
        this.fileReader = new FileReader(file);

    }


    /**
     * Reads the puzzle from the source file and returns it.
     *
     * @return The puzzle
     * @throws IOException If an I/O error occurs
     * @throws JSONException If the JSON object is invalid
     */
    public Puzzle readPuzzle() throws IOException, JSONException {

        // Read the file
        String jsonData = readFile(source);
        // Create a JSON object from the file (The BIG one)
        JSONObject jsonObject = new JSONObject(jsonData);

        // parse the whole puzzle and return it (many lines below)
        return parsePuzzle(jsonObject);
    }


    /**
     * Reads the file with the given file name and returns the content as a string.
     * Inspired by : https://medium.com/@nayantala259/android-how-to-read-and-write-parse-data-from-json-file-226f821e957a
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


        /**
         using this template used in writing the json object, we read the json object

         json.put("userBoard", this.getUserBoard().toJson());
         json.put("solutionBoard", this.getSolutionBoard().toJson());
         json.put("wordPairs", convertWordPairsToJson());
         json.put("puzzleDimension", this.getPuzzleDimension().toJson());
         json.put("language", this.getLanguage());
         json.put("mistakes", this.getMistakes());
         */

        try {

            // user board
            JSONObject userBoardJSON = jsonObject.getJSONObject("userBoard");
            CellBox2DArray userBoard = parseCellBox2DArray(userBoardJSON);

            // solution board
            JSONObject solutionBoardJSON = jsonObject.getJSONObject("solutionBoard");
            CellBox2DArray solutionBoard = parseCellBox2DArray(solutionBoardJSON);

            // word pairs
            JSONArray wordPairsJSON = (jsonObject.getJSONArray("wordPairs"));
            List<WordPair> wordPairs= parseWordPairs(wordPairsJSON);

            // PuzzleDimension
            JSONObject puzzleDimensionJSON = (jsonObject.getJSONObject("puzzleDimensions"));
            PuzzleDimensions puzzleDimensions = parsePuzzleDimension(puzzleDimensionJSON);

            // parse language
            int language = (jsonObject.getInt("language"));

            // parse mistakes
            int mistakes = (jsonObject.getInt("mistakes"));

            // create the puzzle
            Puzzle puzzle = new Puzzle(userBoard, solutionBoard, wordPairs, puzzleDimensions, language, mistakes);

            //return the puzzle with completely read fields
            return puzzle;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Parses the given JSON object and returns a new PuzzleDimensions.
     *
     * @param boardJson The JSON object to parse
     * @return The new PuzzleDimensions
     */
    private CellBox2DArray parseCellBox2DArray(JSONObject boardJson) {
        try {
            // get the boxDimensions of the board
            JSONObject boxDimensionsJSON = boardJson.getJSONObject("boxDimensions");
            Dimension boxDimensions = parseDimension(boxDimensionsJSON);

            // get the cellDimensions of the board
            JSONObject cellDimensionsJSON = boardJson.getJSONObject("cellDimensions");
            Dimension cellDimensions = parseDimension(cellDimensionsJSON);

            // get the cell boxes
            JSONArray cellBoxesJSON = boardJson.getJSONArray("cellBoxes");
            CellBox [][] cellBoxes = parse2DCellBoxes(cellBoxesJSON);

            // create the cell box 2D array
            CellBox2DArray cellBox2DArray = new CellBox2DArray(cellBoxes, boxDimensions, cellDimensions);

            return cellBox2DArray;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param cellBoxesJSON The JSON object to parse
     * @return The new Dimension
     */
    private CellBox [][] parse2DCellBoxes(JSONArray cellBoxesJSON) {
        try {

            // make a 2D array of cell boxes
            CellBox [][] cellBoxes = new CellBox[cellBoxesJSON.length()][cellBoxesJSON.length()];

            for (int i = 0; i < cellBoxesJSON.length(); i++) {
                // get the ith json array of cell boxes
                JSONArray anArray = cellBoxesJSON.getJSONArray(i);

                for (int j = 0; j < anArray.length(); j++) {
                    // get the jth json object of anArray
                    JSONObject cellBoxJSON = anArray.getJSONObject(j);
                    // parse the cell box
                    CellBox cellBox = parseCellBox(cellBoxJSON);
                    // add the cell box to the 2D array at the (i,j)th position
                    cellBoxes[i][j] = cellBox;
                }
            }

            return cellBoxes;

            // catch the JSON exception if the object is not parsed properly as json
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param cellBoxJSON The JSON object to parse
     * @return The new CellBox
     */
    private CellBox parseCellBox(JSONObject cellBoxJSON) {

        try {
            // get the cell
            JSONArray cellsJSON = cellBoxJSON.getJSONArray("cells");

            // make a 2D array of cells
            Cell [][] cells = new Cell[cellsJSON.length()][cellsJSON.length()];

            for (int i = 0; i < cellsJSON.length(); i++) {
                // get the ith json array of cells
                JSONArray anArrayJSON = cellsJSON.getJSONArray(i);

                for (int j = 0; j < anArrayJSON.length(); j++) {
                    // get the jth json object of anArray
                    JSONObject cellJSON = anArrayJSON.getJSONObject(j);
                    // parse the cell
                    Cell cell = parseCell(cellJSON);
                    // add the cell to the 2D array at the (i,j)th position
                    cells[i][j] = cell;
                }

            }

            // get the dimension
            Dimension dimension = parseDimension(cellBoxJSON.getJSONObject("dimension"));

            // create the cell box (Cell [][] cells, Dimension dimension)
            CellBox cellBox = new CellBox(cells, dimension);

            // return the cell box
            return cellBox;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param cellJSON The JSON object to parse
     * @return The new Dimension
     */
    private Cell parseCell(JSONObject cellJSON) throws JSONException {

        // get the language: important to be parsed first so that it is efficient with null content
        // as less lines are processed after 316
        int language = cellJSON.getInt("language");

        WordPair content;

        try {
            content = parseWordPair(cellJSON.getJSONObject("content"));
        } catch (JSONException e) {
            // wordPair is null if it is not found
            return new Cell(language);
        }

        // get the isEditable boolean
        boolean isEditable = cellJSON.getBoolean("isEditable");

        // get the isEmpty boolean
        boolean isEmpty = cellJSON.getBoolean("isEmpty");


        // create the cell with non-null content
        Cell cell = new Cell(content, isEditable, language, isEmpty);

        return cell;
    }


    /**
     * Parses the given JSON array and returns a list of WordPairs.
     *
     * @param wordPairs The JSON array to parse
     * @return The list of WordPairs
     * @throws JSONException If the JSON object is invalid
     */
    private List<WordPair> parseWordPairs(JSONArray wordPairs) throws JSONException {

        // prepare a list of word pairs
        List<WordPair> wordPairsList = new ArrayList<>();
        for (int i = 0; i < wordPairs.length(); i++) {
            // get the ith json object of wordPairs
            JSONObject wordPair_json = wordPairs.getJSONObject(i);

            // parse the word pair
            WordPair wordPair = parseWordPair(wordPair_json);

            // FOR TESTING
            //System.out.println(wordPair.getEnglish() + " " + wordPair.getFrench());

            // add the word pair to the list
            wordPairsList.add(wordPair);
        }


        return wordPairsList;
    }


    /**
     * Parses the given JSON object and returns a new WordPair.
     *
     * @param wordPairJSON The JSON object to parse
     * @return The new WordPair
     * @throws JSONException If the JSON object is invalid
     */
    private WordPair parseWordPair(JSONObject wordPairJSON) throws JSONException {

        // if the word pair is null, return null
        if (wordPairJSON == null) {
            return null;
        }

        // get the english and french words
        String eng = wordPairJSON.getString("eng");
        String fre = wordPairJSON.getString("fre");
        // create the word pair object
        WordPair wordPairObject = new WordPair(eng, fre);

        // return the word pair
        return wordPairObject;
    }


    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param dimension The JSON object to parse
     * @return The new Dimension
     * @throws JSONException If the JSON object is invalid
     */
    private PuzzleDimensions parsePuzzleDimension(JSONObject dimension) throws JSONException {

        // get the puzzle dimension, each box dimension, and boxes in puzzle dimension
        int puzzleDimension = dimension.getInt("puzzleDimension");
        JSONObject eachBoxDimensionJSON = dimension.getJSONObject("eachBoxDimension");
        JSONObject boxesInPuzzleDimensionJSON = dimension.getJSONObject("boxesInPuzzleDimension");

        // parse the dimensions
        Dimension eachBoxDimension = parseDimension(eachBoxDimensionJSON);
        Dimension boxesInPuzzleDimension = parseDimension(boxesInPuzzleDimensionJSON);


        // create the puzzle dimension object
        return new PuzzleDimensions(puzzleDimension, eachBoxDimension, boxesInPuzzleDimension);
    }

    /**
     * Parses the given JSON object and returns a new Dimension.
     *
     * @param eachBoxDimensionJSON The JSON object to parse
     * @return The new Dimension
     * @throws JSONException If the JSON object is invalid
     */
    private Dimension parseDimension(JSONObject eachBoxDimensionJSON) throws JSONException {
        // get the row and column
        int row = eachBoxDimensionJSON.getInt("rows");
        int col = eachBoxDimensionJSON.getInt("columns");
        // create the dimension object
        return new Dimension(row, col);
    }


    // getter for the source file name
    public String getSource() {
        return this.source;
    }

}
