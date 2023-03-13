package com.echo.wordsudoku.models.words;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

public class WordPairReader {
    // The list of WordPair objects
    private List<WordPair> mWordPairs;

    // Defined as a static variable so it would be only stored once in the memory
    private static JSONObject mJSONObject;


    // @constructor WordPairReader
    // @param jsonStr: the json file as a string
    // @throws RuntimeException if the json string is invalid
    // sets the mJSONObject to the json object created from the json string
    // sets the mPuzzleDimension to the given puzzle dimension
    public WordPairReader(String jsonStr) {
        try {
            // Create a json object from the json string
            JSONObject jsonObject = new JSONObject(jsonStr);
            // Set the mJSONObject to the json object created from the json string
            this.mJSONObject = jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // @constructor WordPairReader
    // @param inputStream: the input stream of the json file
    // @throws RuntimeException if the string read from JSON file is invalid
    // sets the mJSONObject to the json object created from the json string read from inputStream
    // sets the mPuzzleDimension to the given puzzle dimension
    public WordPairReader(InputStream inputStream) throws IOException {
        // Read the file size and create a buffer
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        // Read the whole file into the buffer
        inputStream.read(buffer);
        // Close the input stream
        inputStream.close();
        // Convert the buffer into a string
        String jsonStr = new String(buffer, "UTF-8");
        try {
            // Create a json object from the json string
            JSONObject jsonObject = new JSONObject(jsonStr);
            // Set the mJSONObject to the json object created from the json string
            this.mJSONObject = jsonObject;
        } catch (JSONException e) {
            // If the json string is invalid, throw a runtime exception
            throw new RuntimeException(e);
        }
        // Set the mPuzzleDimension to the given puzzle dimension
    }


    // @method getWordPairs
    // @return the list of WordPair objects
    // calls the collectWord method to generate a list of WordPair objects
    public List<WordPair> getRandomWords( int numberOfWords) throws JSONException {
        collectWord(numberOfWords);
        // Convert the list of WordPair objects to an array of WordPair objects and return it
        return mWordPairs;
    }



    // HELPER METHODS


    // @method collectWord
    // @throws JSONException if the json object is invalid
    // sets the mWordPairs to a list of WordPair objects generated from the json file
    // the size of the list is the same as the puzzle dimension
    // the list is generated randomly using the generateRandomArray method
    private void collectWord(int numberOfWords) throws JSONException {
        // gets all the words from the json file
        JSONArray allWords = mJSONObject.getJSONArray("words");
        // creates a new list of WordPair objects
        mWordPairs = new ArrayList<>();
        // generates a random list of indexes between 0 (inclusive) and the length of the allWords array (exclusive)
        List<Integer> randomWordPairIndexes = generateRandomArray(numberOfWords, allWords.length()-1, 0);
        // for each index in the randomWordPairIndexes list
        for (int i = 0; i < numberOfWords; i++) {
            try {
                // get the JSON Object at the index i that is from the randomIndexesList and add it to the mWordPairs list
                JSONObject wordPair = allWords.getJSONObject(randomWordPairIndexes.get(i));
                // create a new WordPair object and add it to the mWordPairs list
                mWordPairs.add(new WordPair(wordPair.getString("translation"), wordPair.getString("word")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    // generate a random number given max value i
    // @param int num which is number of wordPairs requested
    // @param int max which is the max value of the random number (inclusive)
    // @param int min which is the min value of the random number (inclusive)
    // @return WordPair[] which is an array of WordPair objects
    private List<Integer> generateRandomArray(int size,int max, int min) {
        // initialize Random object
        Random random = new Random();
        // make an result array to collect the random indexes
        ArrayList<Integer> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            // get a random number ranged from min to max (inclusive)
            int randomNumber = random.nextInt(max-min+1) + min;
            // if the random number is picked before
            while (result.contains(randomNumber)) {
                // try another random number
                randomNumber = random.nextInt(max-min+1) + min;
            }

            // since randomNumber is not repeated, assign it to the ith index of the result
            result.add(randomNumber);
        }
        // return the random indexes as int[]
        return result;
    }

    // END OF HELPER METHODS
}
