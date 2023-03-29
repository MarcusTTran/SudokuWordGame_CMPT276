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

    private final MutableLiveData<String[][]> puzzleView = new MutableLiveData<>();

    private final MutableLiveData<Integer> timer = new MutableLiveData<>();

    private boolean textToSpeechStatus;
    // TODO move later!
    public boolean isTextToSpeechStatus() {
        return textToSpeechStatus;
    }

    public void setTextToSpeechStatus(boolean textToSpeechStatus) {
        this.textToSpeechStatus = textToSpeechStatus;
    }

    private List<WordPair> customWordPairs;
    private WordPairJsonReader mWordPairJsonReader;

    public List<WordPair> getWordPairs() {
        return puzzle.getWordPairs();
    }

    public boolean hasSetCustomWordPairs() {
        return customWordPairs != null;
    }

    public void setCustomWordPairs(List<WordPair> customWordPairs) {
        this.customWordPairs = customWordPairs;
    }

    public List<WordPair> getCustomWordPairs() {
        return customWordPairs;
    }

    private void setPuzzleView(String[][] content) {
        puzzleView.setValue(content);
    }


    private void setPuzzle(Puzzle puzzle) throws NegativeNumberException {
        this.puzzle = new Puzzle(puzzle);
        setPuzzleView(puzzle.toStringArray());
        postTimer(puzzle.getTimer());
    }

    private void postPuzzle(Puzzle puzzle) {
        this.puzzle = new Puzzle(puzzle);
        puzzleView.postValue(puzzle.toStringArray());
        timer.postValue(puzzle.getTimer());
    }

    public void setWordPairReader(WordPairJsonReader wordPairJsonReader) {
        this.mWordPairJsonReader = wordPairJsonReader;
    }
    // TODO maybe add TTS here?
    public void newPuzzle(int puzzleSize, int boardLanguage, int difficulty) throws JSONException, IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
       setPuzzle(new Puzzle(mWordPairJsonReader.getRandomWords(puzzleSize),puzzleSize,boardLanguage,Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,difficulty));
    }

    public void newCustomPuzzle(int puzzleLanguage, int difficulty) throws IllegalLanguageException, TooBigNumberException, NegativeNumberException, IllegalWordPairException, IllegalDimensionException {
        setPuzzle(new Puzzle(customWordPairs, customWordPairs.size(),puzzleLanguage,Puzzle.NO_NUMBER_OF_START_CELLS_USE_DIFFICULTY,difficulty));
    }

    public void loadPuzzle(Puzzle puzzle) {
        postPuzzle(puzzle);
    }

    public void insertWord(Dimension dimension,String word) throws IllegalWordPairException, IllegalDimensionException {
        puzzle.setCell(dimension, word);
        setPuzzleView(puzzle.toStringArray());
    }

    public void resetPuzzle(boolean isRetry) {
        if (!puzzle.isPuzzleBlank()) {
            puzzle.resetPuzzle(isRetry);
            setPuzzleView(puzzle.toStringArray());
        }
    }

    public boolean isCellWritable(Dimension dimension) {
        return puzzle.isWritableCell(dimension);
    }

    public GameResult getGameResult() {
        return puzzle.getGameResult();
    }

    public boolean isPuzzleComplete() {
        return puzzle.isPuzzleFilled();
    }

    public int getPuzzleInputLanguage() throws IllegalLanguageException {
        return BoardLanguage.getOtherLanguage(puzzle.getLanguage());
    }

    public LiveData<Integer> getTimer() {
        return timer;
    }

    public int getPuzzleTimer() {
        return puzzle.getTimer();
    }

    public void postTimer(int seconds) throws NegativeNumberException {
        puzzle.setTimer(seconds);
        timer.postValue(seconds);
    }

    public JSONObject getPuzzleJson() throws JSONException {
        return puzzle.toJson();
    }

    public boolean isPuzzleNonValid() {
        return puzzle==null || puzzle.isPuzzleTotallyEmpty();
    }

    public LiveData<String[][]> getPuzzleView() {
        return puzzleView;
    }

    public boolean[][] getImmutableCells() {
        return puzzle.getImmutabilityTable();
    }

    public PuzzleDimensions getPuzzleDimensions() {
        return puzzle.getPuzzleDimensions();
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
}
