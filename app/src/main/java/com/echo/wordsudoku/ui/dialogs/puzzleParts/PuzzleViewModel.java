package com.echo.wordsudoku.ui.dialogs.puzzleParts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.words.WordPairReader;

import java.util.List;

public class PuzzleViewModel extends ViewModel {
    private final MutableLiveData<Puzzle> puzzle = new MutableLiveData<>();

    private List<WordPair> CustomWordPair;
    private WordPairReader wordPairReader;

    public List<WordPair> getWordPairs() {
        return puzzle.getValue().getWordPairs();
    }

    public LiveData<Puzzle> getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle.setValue(new Puzzle(puzzle));
    }

    public void postPuzzle(Puzzle puzzle) {
        this.puzzle.postValue(new Puzzle(puzzle));
    }

    public WordPairReader getWordPairReader() {
        return wordPairReader;
    }
    public void setWordPairReader(WordPairReader wordPairReader) {
        this.wordPairReader = wordPairReader;
    }
}
