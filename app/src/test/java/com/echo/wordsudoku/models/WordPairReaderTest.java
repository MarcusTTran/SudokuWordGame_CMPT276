package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;



import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.*;


import java.io.IOException;
import java.io.InputStream;

class WordPairReaderTest {

    WordPair[] testWords9x9 = {
    new WordPair("yes", "oui"), new WordPair("lobby", "foyer"),
    new WordPair("school", "école"), new WordPair("untangle", "débrouiller"),
    new WordPair("red", "rouge"), new WordPair("risk", "risque"),
    new WordPair("welcome", "bienvenue"), new WordPair("bird", "oiseau"),
    new WordPair("esquive","evasion")
    };



    @Test
    void testConstructor() {



        InputStream targetStream;
        WordPairReader newReader;
        WordPair[] mWordPairs;

        try {
            File initialFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            targetStream = new FileInputStream(initialFile);
            newReader = new WordPairReader(targetStream, 9);
            mWordPairs = newReader.getWords();
            assertEquals("Orange", mWordPairs[0].getEnglish());
        } catch (FileNotFoundException e) {
            fail(e);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            fail(e);
        }



    }

    //Tests getPuzzleDimension
    @Test
    void testGetPuzzleDimension() {

        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = new FileInputStream(testFile);
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();

            assertEquals(dim, testReader.getPuzzleDimension());


        } catch (FileNotFoundException e) {
            fail(e);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            fail(e);
        }
    }

    //Test that all words are read from JSONFile to wordPair list
    @Test
    void testGetWords() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = new FileInputStream(testFile);
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();



        } catch (FileNotFoundException e) {
            fail(e);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            fail(e);
        }


    }
}