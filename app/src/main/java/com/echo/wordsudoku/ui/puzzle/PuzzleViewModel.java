package com.echo.wordsudoku.ui.puzzle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;

import java.util.List;

public class PuzzleViewModel extends ViewModel {
    private final MutableLiveData<List<WordPair>> boardWordPairs = new MutableLiveData<>();
    private final MutableLiveData<Integer> boardLanguage = new MutableLiveData<>();
    private final MutableLiveData<Puzzle> puzzle = new MutableLiveData<>();

    public LiveData<List<WordPair>> getWordPairs() {
        return boardWordPairs;
    }
    public void setWordPairs(List<WordPair> wordPairs) {
        this.boardWordPairs.setValue(wordPairs);
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
}
