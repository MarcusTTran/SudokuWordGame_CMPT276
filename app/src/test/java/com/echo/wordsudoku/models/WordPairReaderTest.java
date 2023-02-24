package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;


import com.echo.wordsudoku.models.Words.WordPair;
import com.echo.wordsudoku.models.Words.WordPairReader;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.*;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

class WordPairReaderTest {
    //JSON FILES USED FOR TESTING ARE STORED IN src/main/assets/jsontestfiles

    //Dummy wordPair list identical to the wordPair objects stored in the testWords9x9.json file
    // Used to validate whether WordPairReader is parsing JSON files and objects correctly
    WordPair[] testWords9x9 = {
    new WordPair("yes", "oui"), new WordPair("lobby", "foyer"),
    new WordPair("school", "école"), new WordPair("untangle", "débrouiller"),
    new WordPair("red", "rouge"), new WordPair("risk", "risque"),
    new WordPair("welcome", "bienvenue"), new WordPair("bird", "oiseau"),
    new WordPair("evasion", "esquive")
    };


    //Try to read in a JSON file with a malformed String
    @Test
    void testConstructorJSONException() {
        InputStream targetStream;
        WordPairReader newReader;
        WordPair[] mWordPairs;

        try {
            File initialFile = new File("src/main/assets/jsontestfiles/testWordsMalformedString.json");
            targetStream = Files.newInputStream(initialFile.toPath());
            newReader = new WordPairReader(targetStream, 9);
            mWordPairs = newReader.getWords();
            assertEquals("Orange", mWordPairs[0].getEnglish());
        } catch (IOException | JSONException e) {
            fail();
        } catch (RuntimeException e) {
            return;
        }
        fail();


    }

    //Try to read in a JSON file with a malformed structure
    @Test
    void testReadInvalidJsonException() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWordsMalformedStructure.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            return;
        }
    }

    //Test constructor can correctly read in all JSON objects with an InputStream
    @Test
    void testConstructorJSONInputStream() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();
            int counter = 0;
            for (int i = 0; i < dim; i++) {
                for (int k = 0; k < dim; k++) {
                    if (testWords9x9[i].getEnglish().equals(mWordPairs[k].getEnglish()) ) {
                        counter++;
                    }
                }
            }
            assertEquals(dim, counter);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            return;
        }
    }


    //Test that Exception is thrown when Invalid JSON string is passed
    @Test
    void testConstructorJSONAsStringException() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/assets/jsontestfiles/testWords9x9.json")));
            //Invalidate the JSON String by adding some string to it
            json = "2" + json;
            testReader = new WordPairReader(json, dim);
            mWordPairs = testReader.getWords();
        } catch (IOException | JSONException e) {
            fail();
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    //Test constructor can correctly read in all JSON objects with a string
    @Test
    void testConstructorJSONAsString() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/assets/jsontestfiles/testWords9x9.json")));
            testReader = new WordPairReader(json, dim);
            mWordPairs = testReader.getWords();
            int counter = 0;
            for (int i = 0; i < dim; i++) {
                for (int k = 0; k < dim; k++) {
                    if (testWords9x9[i].getEnglish().equals(mWordPairs[k].getEnglish()) ) {
                        counter++;
                    }
                }
            }
            assertEquals(dim, counter);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            return;
        }
    }

    //Test that all English words are read from the test JSONFile to the wordPair list
    @Test
    void testGetAllEnglishWords() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();
            int counter = 0;
            for (int i = 0; i < dim; i++) {
                for (int k = 0; k < dim; k++) {
                    if (testWords9x9[i].getEnglish().equals(mWordPairs[k].getEnglish()) ) {
                        System.out.println(mWordPairs[k].getEnglish() + " and " + testWords9x9[i].getEnglish());
                        counter++;
                    }
                    System.out.println(mWordPairs[k].getEnglish());
                }
            }
            assertEquals(dim, counter);
        } catch (IOException | JSONException e) {
            fail(e);
        }
    }

    //Test that all English words are read from the test JSONFile to the wordPair list
    @Test
    void testGetAllFrenchWords() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();
            int counter = 0;
            for (int i = 0; i < dim; i++) {
                for (int k = 0; k < dim; k++) {
                    if (testWords9x9[i].getEnglish().equals(mWordPairs[k].getEnglish()) ) {
                        System.out.println(mWordPairs[k].getFrench() + " and " + testWords9x9[i].getFrench());
                        counter++;
                    }
                    System.out.println(mWordPairs[k].getEnglish());
                }
            }
            assertEquals(dim, counter);
        } catch (IOException | JSONException e) {
            fail(e);
        }
    }

    //Test that JSON reader can parse large amounts of words without throwing exception by
    // reading in 500 wordpair objects
    @Test
    void testWordPairMax() {
        InputStream testStream;
        WordPairReader testReader;
        WordPair[] mWordPairs;

        int dim = 500;

        int counter = 0;
        try {
            File testFile = new File("src/main/assets/words.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);
            mWordPairs = testReader.getWords();
            for (int i = 0; i < mWordPairs.length; i++) {
                counter++;
            }
            assertEquals(dim, counter);
        } catch (IOException e) {
            fail(e);
        } catch (JSONException e) {
            return;
        }
    }

    //Test Constructor sets Puzzle dimension properly
    @Test
    void testConstructorGetPuzzleDimension() {

        InputStream testStream;
        WordPairReader testReader;


        int dim = 9;

        try {
            File testFile = new File("src/main/assets/jsontestfiles/testWords9x9.json");
            testStream = Files.newInputStream(testFile.toPath());
            testReader = new WordPairReader(testStream, dim);


            assertEquals(dim, testReader.getPuzzleDimension());


        } catch (IOException e) {
            fail(e);
        }
    }

}