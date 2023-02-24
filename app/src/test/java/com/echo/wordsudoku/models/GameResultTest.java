package com.echo.wordsudoku.models;

import static org.junit.jupiter.api.Assertions.*;

import com.echo.wordsudoku.models.GameInfo.GameResult;

import org.junit.jupiter.api.Test;

class GameResultTest {
    //Test constructor of GameResultTest with initial boolean and mistake value
    @Test
    void testConstructorBooleanAndMistakes() {
        int mistakes = 50;
        boolean result = true;
        GameResult testGameResult = new GameResult(result, mistakes);
        assertTrue(testGameResult.getResult() && testGameResult.getMistakes() == mistakes);
    }

    //Test constructor of GameResultTest with initial boolean value
    @Test
    void testConstructorBoolean() {
        boolean result = false;
        GameResult testGameResult = new GameResult(result);
        assertFalse(testGameResult.getResult());
    }

    //Test setters of GameResultTest
    @Test
    void testSetters() {
        int mistakes = 50;
        boolean result = true;
        GameResult testGameResult = new GameResult(result, mistakes);
        testGameResult.setResult(false);
        testGameResult.setMistakes(0);
        assertTrue(!testGameResult.getResult() && testGameResult.getMistakes() == 0);
    }

    //Test default constructor of GameResultTest with no initial values
    @Test
    void testDefaultConstructor() {
        GameResult testGameResult = new GameResult();
        assertTrue(testGameResult.getResult());
    }


}