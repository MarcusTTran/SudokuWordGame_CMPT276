package com.echo.wordsudoku.models.Memory;

import static com.echo.wordsudoku.file.FileUtils.inputStreamToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import com.echo.wordsudoku.models.json.WordPairJsonReader;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.words.WordPair;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Unit test for WordPairReader
 * @author Kousha Amouzesh
 * @version 1.0
 */
public class WordPairReaderTest {


    private WordPairJsonReader wordPairJsonReader;

    // the sample json string
    private String jsonString;


    @BeforeEach
    void setUp() {

        //give readable value to jsonString
        this.jsonString = "{\"words\":["
                + "{\"word\":\"eng1\",\"french\":\"fre1\",\"chinese\":\"ch1\",\"spanish\":\"sp1\",\"arabic\":\"ar1\"},"
                + "{\"word\":\"eng2\",\"french\":\"fre2\",\"chinese\":\"ch2\",\"spanish\":\"sp2\",\"arabic\":\"ar2\"},"
                + "{\"word\":\"eng3\",\"french\":\"fre3\",\"chinese\":\"ch3\",\"spanish\":\"sp3\",\"arabic\":\"ar3\"},"
                + "{\"word\":\"eng4\",\"french\":\"fre4\",\"chinese\":\"ch4\",\"spanish\":\"sp4\",\"arabic\":\"ar4\"},"
                + "{\"word\":\"eng5\",\"french\":\"fre5\",\"chinese\":\"ch5\",\"spanish\":\"sp5\",\"arabic\":\"ar5\"},"
                + "{\"word\":\"eng6\",\"french\":\"fre6\",\"chinese\":\"ch6\",\"spanish\":\"sp6\",\"arabic\":\"ar6\"},"
                + "{\"word\":\"eng7\",\"french\":\"fre7\",\"chinese\":\"ch7\",\"spanish\":\"sp7\",\"arabic\":\"ar7\"},"
                + "{\"word\":\"eng8\",\"french\":\"fre8\",\"chinese\":\"ch8\",\"spanish\":\"sp8\",\"arabic\":\"ar8\"},"
                + "{\"word\":\"eng9\",\"french\":\"fre9\",\"chinese\":\"ch9\",\"spanish\":\"sp9\",\"arabic\":\"ar9\"}]}";

        try {
            // set up a sample file
            InputStream jsonFile = new ByteArrayInputStream(jsonString.getBytes());

            // instantiate the wordPairJsonReader with the sample file
            this.wordPairJsonReader = new WordPairJsonReader(inputStreamToString(jsonFile));


            //catch the IOException (should not happen)
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    // test getting the word pairs from the json file
    @Test
    void testGetRandomWords() {
        int numberOfWords = 7;
        List<WordPair> wordPairs = this.wordPairJsonReader.getRandomWords(numberOfWords, BoardLanguage.ENGLISH, BoardLanguage.FRENCH);
        assertNotNull(wordPairs);
        assertEquals(numberOfWords, wordPairs.size());
    }

    // test invalid json format
    @Test
    void testInvalidJsonObject() {
        // set up an invalid json string
        String invalidJsonString = "invalidFormat :[]";

        try {
            this.wordPairJsonReader = new WordPairJsonReader(invalidJsonString);
            // should throw an exception
            fail("Should have thrown an exception");
        } catch (Exception e) {
            // expected
        }

        int numberOfWords = 2;
        List<WordPair> wordPairs = this.wordPairJsonReader.getRandomWords(numberOfWords, BoardLanguage.ENGLISH, BoardLanguage.FRENCH);
        assertNotNull(wordPairs);
        assertEquals(numberOfWords, wordPairs.size());
    }

}
