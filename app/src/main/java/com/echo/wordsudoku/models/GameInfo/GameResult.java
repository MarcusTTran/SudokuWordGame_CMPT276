package com.echo.wordsudoku.models.GameInfo;

public class GameResult {

    // The result of the game
    // true if the user won, false if the user lost
    private boolean result;

    // The number of mistakes the user made
    // Only used if the user lost
    private int mistakes = 0;

    public GameResult(boolean result, int mistakes) {
        this.result = result;
        this.mistakes = mistakes;
    }
    public GameResult(boolean result) {
        this.result = result;
    }

    // The default constructor
    // EFFECT: sets the result to true
    public GameResult() {
        this.result = true;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }
}
