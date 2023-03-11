package com.echo.wordsudoku.ui.puzzleParts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.words.WordPairReader;

import java.util.ArrayList;
import java.util.List;

public class PuzzleViewModel extends ViewModel {
    private final MutableLiveData<List<WordPair>> boardWordPairs = new MutableLiveData<>();
    private final MutableLiveData<Integer> boardLanguage = new MutableLiveData<>();
    private final MutableLiveData<Puzzle> puzzle = new MutableLiveData<>();
    private final MutableLiveData<WordPairReader> wordPairReader = new MutableLiveData<>();

    private final MutableLiveData<List<WordPair>> customWordPairs= new MutableLiveData<>();

    public LiveData<List<WordPair>> getWordPairs() {
        return boardWordPairs;
    }
    public void setWordPairs(List<WordPair> wordPairs) {
//        this.boardWordPairs.setValue(wordPairs);
        this.customWordPairs.setValue(wordPairs);
    }

    public LiveData<Integer> getBoardLanguage() {
        return boardLanguage;
    }
    public void setBoardLanguage(int language) {
        if (BoardLanguage.isValidLanguage(language))
            this.boardLanguage.setValue(language);
        else throw new IllegalArgumentException("Invalid language");
    }

    public LiveData<Puzzle> getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle.setValue(puzzle);
    }

    public LiveData<WordPairReader> getWordPairReader() {
        return wordPairReader;
    }
    public void setWordPairReader(WordPairReader wordPairReader) {
        this.wordPairReader.setValue(wordPairReader);
    }
}
