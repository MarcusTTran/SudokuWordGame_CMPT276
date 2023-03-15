package com.echo.wordsudoku.models.Memory;

import static com.echo.wordsudoku.file.FileUtils.inputStreamToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import com.echo.wordsudoku.models.json.WordPairJsonReader;
import com.echo.wordsudoku.models.words.WordPair;
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
                + "{\"word\":\"eng1\",\"translation\":\"fre1\"},"
                + "{\"word\":\"eng2\",\"translation\":\"fre2\"},"
                + "{\"word\":\"eng3\",\"translation\":\"fre3\"},"
                + "{\"word\":\"eng4\",\"translation\":\"fre4\"},"
                + "{\"word\":\"eng5\",\"translation\":\"fre5\"},"
                + "{\"word\":\"eng6\",\"translation\":\"fre6\"},"
                + "{\"word\":\"eng7\",\"translation\":\"fre7\"},"
                + "{\"word\":\"eng8\",\"translation\":\"fre8\"},"
                + "{\"word\":\"eng9\",\"translation\":\"fre9\"}]}";

        try {
            // set up a sample file
            InputStream jsonFile = new ByteArrayInputStream(jsonString.getBytes());

            // instantiate the wordPairJsonReader with the sample file
            this.wordPairJsonReader = new WordPairJsonReader(inputStreamToString(jsonFile));


            //catch the IOException (should not happen)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // test getting the word pairs from the json file
    @Test
    void testGetRandomWords() {
        int numberOfWords = 7;
        List<WordPair> wordPairs = this.wordPairJsonReader.getRandomWords(numberOfWords);
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
        List<WordPair> wordPairs = this.wordPairJsonReader.getRandomWords(numberOfWords);
        assertNotNull(wordPairs);
        assertEquals(numberOfWords, wordPairs.size());
    }

}
