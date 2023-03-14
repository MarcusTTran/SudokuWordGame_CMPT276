package com.echo.wordsudoku.ui.puzzleParts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    private boolean isCustomPuzzle = false;

    private boolean isPuzzleSaved = true;


    private List<WordPair> customWordPairs;
    private WordPairJsonReader mWordPairJsonReader;

    public List<WordPair> getWordPairs() {
        if (isCustomPuzzle) {
            return customWordPairs;
        } else {
            return puzzle.getWordPairs();
        }
    }

    public boolean hasSetCustomWordPairs() {
        return customWordPairs != null;
    }

    public void setCustomWordPairs(List<WordPair> customWordPairs) {
        this.customWordPairs = customWordPairs;
    }

    private void setPuzzleView(String[][] content) {
        puzzleView.setValue(content);
    }


    private void setPuzzle(Puzzle puzzle) {
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

    public boolean isPuzzleSaved() {
        return isPuzzleSaved;
    }

    public void puzzleSaved() {
        isPuzzleSaved = true;
    }

    public void newPuzzle(int puzzleSize, int boardLanguage, int difficulty) throws JSONException {
       setPuzzle(new Puzzle(mWordPairJsonReader.getRandomWords(puzzleSize),puzzleSize,boardLanguage,-1,difficulty));
       isPuzzleSaved = false;
    }

    public void newCustomPuzzle(int puzzleLanguage, int difficulty) {
        setPuzzle(new Puzzle(customWordPairs, customWordPairs.size(),puzzleLanguage,-1,difficulty));
        isCustomPuzzle = true;
        isPuzzleSaved = false;
    }

    public void loadPuzzle(Puzzle puzzle) {
        postPuzzle(puzzle);
    }

    public void insertWord(Dimension dimension,String word) {
        puzzle.setCell(dimension, word);
        setPuzzleView(puzzle.toStringArray());
        isPuzzleSaved = false;
    }

    public void resetPuzzle(boolean isRetry) {
        if (!puzzle.isPuzzleBlank()) {
            puzzle.resetPuzzle(isRetry);
            setPuzzleView(puzzle.toStringArray());
            isPuzzleSaved = false;
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

    public int getPuzzleInputLanguage() {
        return BoardLanguage.getOtherLanguage(puzzle.getLanguage());
    }

    public LiveData<Integer> getTimer() {
        return timer;
    }

    public int getPuzzleTimer() {
        return puzzle.getTimer();
    }

    public void postTimer(int seconds) {
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
        return puzzle.getPuzzleDimension();
    }
}
