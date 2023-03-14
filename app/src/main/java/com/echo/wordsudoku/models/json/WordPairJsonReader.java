package com.echo.wordsudoku.models.json;

import com.echo.wordsudoku.models.utility.MathUtils;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO: Refactor this class to use a remote database instead of a json file


// @class WordPairReader
// @author eakbarib
// @date    2023-2-20
// @brief   This class is used to read the json file and store the WordPair objects in a list
//         This class can also work with a remote database by changing the InputStream to a String
//          This class is also used to generate a random list of wordpairs from the json file
/*
*   It has two constructors:
*  1. WordPairReader(String jsonStr, int mPuzzleDimension)
* 2. WordPairReader(InputStream inputStream, int mPuzzleDimension)
*  The first constructor takes a json string and the puzzle dimension as parameters -> Good for getting the json string from a remote database or API
* The second constructor takes an input stream and the puzzle dimension as parameters
*  */

public class WordPairJsonReader {
    // The list of all word pairs
    private List<WordPair> mAllWordPairs = new ArrayList<>();

    // Defined as a static variable so it would be only stored once in the memory
    private static JSONObject mJSONObject;

    private final String WORD_ARRAY_KEY_IN_JSON_FILE = "words";

    private final String WORD_ENGLISH_ATTRIBUTE_VALUE_IN_JSON_FILE = "translation";
    private final String WORD_FRENCH_ATTRIBUTE_VALUE_IN_JSON_FILE = "word";

    // @constructor WordPairReader
    // @param jsonStr: the json file as a string
    // @throws RuntimeException if the json string is invalid
    // sets the mJSONObject to the json object created from the json string
    // sets the mPuzzleDimension to the given puzzle dimension
    public WordPairJsonReader(String jsonStr) {
        try {
            // Create a json object from the json string
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray allWordsJson = jsonObject.getJSONArray(WORD_ARRAY_KEY_IN_JSON_FILE);
            // Set the mJSONObject to the json object created from the json string
            for (int i = 0; i < allWordsJson.length(); i++) {
                // get the JSON Object at the index i that is from the randomIndexesList and add it to the mWordPairs list
                JSONObject wordPair = allWordsJson.getJSONObject(i);
                // create a new WordPair object and add it to the mWordPairs list
                mAllWordPairs.add(new WordPair(wordPair.getString(WORD_ENGLISH_ATTRIBUTE_VALUE_IN_JSON_FILE), wordPair.getString(WORD_FRENCH_ATTRIBUTE_VALUE_IN_JSON_FILE)));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    // @method getWordPairs
    // @return the list of WordPair objects
    // calls the collectWord method to generate a list of WordPair objects
    public List<WordPair> getRandomWords( int numberOfWords){
        List<Integer> randomWordPairIndexes = MathUtils.generateRandomIndexes(numberOfWords, mAllWordPairs.size()-1, 0);
        List<WordPair> randomWordPairs = new ArrayList<>();

        for (int i = 0; i < numberOfWords; i++) {
            randomWordPairs.add(mAllWordPairs.get(randomWordPairIndexes.get(i)));
        }
        // Convert the list of WordPair objects to an array of WordPair objects and return it
        return randomWordPairs;
    }
}
