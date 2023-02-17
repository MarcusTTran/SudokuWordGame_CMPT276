package com.echo.wordsudoku.models;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;



/**
 *  ===================================== CSVReader =====================================
    CSVReader locates the dictionary.csv in raw folder and takes n random words
    for generating the game. the constructor takes an InputStream field to instantiate
    the Object for accessing the csv file on local project folder
    ===================================== CSVReader =====================================
 */


public class CSVReader {


    // InputStream
    private InputStream is;

    // number of rows in csv file
    private static final int ROWS = 98;

    // all wordPairs
    private WordPair[] wordPairs;

    // number of wordPairs needed
    private int num_wordPairs;


    // @param InputStream and number of wordPairs needed
    // constructor
    public CSVReader(InputStream is, int num_wordPairs) {
        this.is = is;
        this.num_wordPairs = num_wordPairs;
        this.wordPairs = new WordPair[this.num_wordPairs];
    }


    // collects WordPairs from the local csv file
    public void collectWords() {

        // initialize the list of wordPair to return later
        WordPair[] wordSet = new WordPair[num_wordPairs];

        // pick the row indexes in random
        int [] arr;
        // give arr a list of random indexes ranged
        // in the total number of words available
        arr = generateRandomArray(num_wordPairs);

        // the index used to allocate elements in array of indexes from csv
        int picked_index = 0;

        // instantiate a BufferedReader for iterating through csv
        BufferedReader reader = new BufferedReader(
                // set the language to UTF-8 and pass the InputStream field
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        // initial line in csv
        String line = "";

        // try accessing the file
        try {

            reader.readLine(); // skip headers

            // index used to iterate through csv and
            // for comparison with index_csv when picking the words
            int index = 0;

            // while there are more lines to read
            while ( (line = reader.readLine()) != null) {
                // if the index on csv is equal to the random picked index
                if (index == arr[picked_index]) {
                    // split the row into two sections [0] and [1]
                    String [] row_element = line.split(",");

                    //remove the quotation marks at first and end of the strings
                    String eng = row_element[0].substring(1, row_element[0].length()-1);
                    String fre = row_element[1].substring(1, row_element[1].length()-1);

                    //create word pair by getting first(eng) and second(fre) column at the row
                    WordPair wp = new WordPair(eng , fre);

                    // assign the wordSet at given index to the new WordPair
                    wordSet[picked_index] = wp;

                    // if all collectable indexes are picked
                    if (picked_index == num_wordPairs-1)
                        //end the loop
                        break;

                    // increment index (go to the next row) and picked_index
                    index++;
                    picked_index++;


                } else {
                    // else  the index is not collectable, just go to next row
                    index++;
                }
            }


        // if can't file the line catch the IOException when thrown
        } catch (IOException e) {
            // log a message
            Log.wtf("Can't read a line ", e);
            // print the StackTrace from Exception
            e.printStackTrace();
        }


        wordPairs = wordSet;

    }

    // @param int num which is number of wordPairs requested
    // generate a random number given max value i
    public static int[] generateRandomArray(int num) {
        // initialize Random object
        Random random = new Random();
        // make an result array to collect the random indexes
        int[] result = new int[num];

        for (int i = 0; i < num; i++) {
            // get a random number ranged from 1 to 100
            int randomNumber = random.nextInt(ROWS-1) + 1;
            // while the random number is picked before
            while (contains(result, randomNumber)) {
                // try another random number
                randomNumber = random.nextInt(ROWS-1) + 1;
            }

            // since randomNumber is not repeated, assign it to the ith index of the result
            result[i] = randomNumber;
        }

        // sort the result
        Arrays.sort(result);


        // return the random indexes as int[]
        return result;
    }

    // @return true if  the arr contains the given int value else false
    private static boolean contains(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            // if the ith index of array is equal to the given value
            if (arr[i] == value) {
                // return true if the array contains the value
                return true;
            }
        }
        // if the array does not contain the value return false
        return false;
    }


    // @return the wordPairs member variable
    public WordPair[] getWords() {
        return wordPairs;
    }
}
