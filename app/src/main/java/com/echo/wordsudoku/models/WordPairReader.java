package com.echo.wordsudoku.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordPairReader {
    private List<WordPair> mWordPairs;
    private static JSONObject mJSONObject;
    private final int mPuzzleDimension;

    public WordPairReader(String jsonStr,int mPuzzleDimension) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            this.mJSONObject = jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.mPuzzleDimension = mPuzzleDimension;
    }

    public WordPairReader(InputStream inputStream, int mPuzzleDimension) throws IOException {
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String jsonStr = new String(buffer, "UTF-8");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            this.mJSONObject = jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.mPuzzleDimension = mPuzzleDimension;
    }

    public int getPuzzleDimension() {
        return mPuzzleDimension;
    }

    public void collectWord() throws JSONException {
        JSONArray allWords = mJSONObject.getJSONArray("words");
        mWordPairs = new ArrayList<WordPair>();
        List<Integer> randomWordPairIndexes = generateRandomArray(mPuzzleDimension, allWords.length()-1, 0);
        for (int i = 0; i < mPuzzleDimension; i++) {
            try {
                JSONObject wordPair = allWords.getJSONObject(randomWordPairIndexes.get(i));
                mWordPairs.add(new WordPair(wordPair.getString("word"), wordPair.getString("translation")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public WordPair[] getWords() throws JSONException {
        collectWord();
        return mWordPairs.toArray(new WordPair[0]);
    }



    // HELPER METHODS
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
            // get a random number ranged from min to max
            int randomNumber = random.nextInt(max-min+1) + min;
            // while the random number is picked before
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
