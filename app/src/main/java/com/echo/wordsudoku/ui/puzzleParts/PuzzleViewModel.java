/*
* Description: This class is the view model for the puzzle fragment.
* It is responsible for holding the puzzle data and the game result data.
* It also contains methods that allow the PuzzleFragment to interact with the puzzle data.
 */

package com.echo.wordsudoku.ui.puzzleParts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.exceptions.NegativeNumberException;
import com.echo.wordsudoku.exceptions.TooBigNumberException;
import com.echo.wordsudoku.models.language.BoardLanguage;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.json.WordPairJsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PuzzleViewModel extends ViewModel {
    private Puzzle puzzle;

    private boolean isNewGame = true;


    private final MutableLiveData<String[][]> puzzleView = new MutableLiveData<>();

    private final MutableLiveData<Integer> timer = new MutableLiveData<>();
    private List<WordPair> customWordPairs;
    private WordPairJsonReader mWordPairJsonReader;

    public boolean getIsNewGame() {
        return isNewGame;
    }

    public void setIsNewGame(boolean isNewGame) {
        this.isNewGame = isNewGame;
    }

    // returns the list of wordpairs in the puzzle (useful for creating buttons in PuzzleInputButtonsFragment and so on)
    public List<WordPair> getWordPairs() {
        return puzzle.getWordPairs();
    }

    // checks if the user has set custom word pairs
    public boolean hasSetCustomWordPairs() {
        return customWordPairs != null;
    }

    // sets the list of custom word pairs
    public void setCustomWordPairs(List<WordPair> customWordPairs) {
        this.customWordPairs = customWordPairs;
    }

    // returns the list of custom word pairs
    public List<WordPair> getCustomWordPairs() {
        return customWordPairs;
    }

    // updates the puzzle view (which is the 2D array of strings that is displayed in the PuzzleBoardFragment)
    private void setPuzzleView(String[][] content) {
        puzzleView.setValue(content);
    }


    // sets the puzzle to a new puzzle
    private void setPuzzle(Puzzle puzzle) throws NegativeNumberException {
        this.puzzle = new Puzzle(puzzle);
        setPuzzleView(puzzle.toStringArray());
        postTimer(puzzle.getTimer());
    }

    // Thread safe method for posting a puzzle
    private void postPuzzle(Puzzle puzzle,boolean textToSpeech,boolean updateTimer) {
        puzzle.setTextToSpeechOn(textToSpeech);
        this.puzzle = new Puzzle(puzzle);
        puzzleView.postValue(puzzle.toStringArray());
        if(updateTimer)
            timer.postValue(puzzle.getTimer());
    }

    // sets the word pair reader (used for getting random wordpairs for puzzle generation)
    public void setWordPairReader(WordPairJsonReader wordPairJsonReader) {
        this.mWordPairJsonReader = wordPairJsonReader;
    }

    // generates a new puzzle with random wordpairs with the given parameters
    public void newPuzzle(int puzzleSize, int boardLanguage,int inputLanguage, int difficulty, boolean textToSpeech) throws JSONException, IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
       setPuzzle(new Puzzle(mWordPairJsonReader.getRandomWords(puzzleSize,boardLanguage,inputLanguage),puzzleSize,Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,difficulty,textToSpeech));
       isNewGame = true;
    }

    // generates a new puzzle from user set custom wordpairs with the given parameters
    public void newCustomPuzzle(int difficulty, boolean textToSpeech) throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        setPuzzle(new Puzzle(customWordPairs, customWordPairs.size(),Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,difficulty,textToSpeech));
        isNewGame = true;
    }

    // this method is used when the user wants to load a puzzle from a json file
    public void loadPuzzle(Puzzle puzzle, boolean textToSpeech, boolean updateTimer) {
        postPuzzle(puzzle,textToSpeech,updateTimer);
        isNewGame = false;
    }

    // this method inserts a word into the puzzle
    public void insertWord(Dimension dimension,String word) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.setCell(dimension, word);
        setPuzzleView(puzzle.toStringArray());
    }

    // resets the puzzle
    public void resetPuzzle(boolean isRetry) {
        if (!puzzle.isPuzzleBlank()) {
            isNewGame = true;
            puzzle.resetPuzzle(isRetry);
            setPuzzleView(puzzle.toStringArray());
        }
    }

    // check to see if a cell is writable
    public boolean isCellWritable(Dimension dimension) {
        return puzzle.isWritableCell(dimension);
    }

    // check to see if the user has won the game
    public GameResult getGameResult() {
        return puzzle.getGameResult();
    }

    // check to see if the puzzle is complete
    public boolean isPuzzleComplete() {
        return puzzle.isPuzzleFilled();
    }

    // gets the timer and we can observe it
    public LiveData<Integer> getTimer() {
        return timer;
    }

    public void setTimer(int seconds) throws NegativeNumberException {
        puzzle.setTimer(seconds);
        timer.setValue(seconds);
    }

    // gets the timer value of the puzzle
    public int getPuzzleTimer() {
        return puzzle.getTimer();
    }

    // sets the timer value of the puzzle from background threads
    public void postTimer(int seconds) throws NegativeNumberException {
        puzzle.setTimer(seconds);
        timer.postValue(seconds);
    }

    // gets the puzzle as a json object
    public JSONObject getPuzzleJson() throws JSONException {
        return puzzle.toJson();
    }

    // checks to see if the puzzle is invalid (null or totally empty)
    public boolean isPuzzleNonValid() {
        return puzzle==null || puzzle.isPuzzleTotallyEmpty();
    }

    // gets the puzzle view (the 2D array of strings that is displayed in the PuzzleBoardFragment)
    public LiveData<String[][]> getPuzzleView() {
        return puzzleView;
    }

    // gets the immutability table of the puzzle
    public boolean[][] getImmutableCells() {
        return puzzle.getImmutabilityTable();
    }

    // gets the puzzle dimensions
    public PuzzleDimensions getPuzzleDimensions() {
        return puzzle.getPuzzleDimensions();
    }

    // gets the puzzle
    public Puzzle getPuzzle() {
        return puzzle;
    }
}
