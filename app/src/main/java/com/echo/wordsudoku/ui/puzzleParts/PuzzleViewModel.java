package com.echo.wordsudoku.ui.puzzleParts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.words.WordPairReader;

import java.util.List;

public class PuzzleViewModel extends ViewModel {
    private Puzzle puzzle;

    private List<WordPair> CustomWordPair;
    private WordPairReader wordPairReader;

    public List<WordPair> getWordPairs() {
        return puzzle.getWordPairs();
    }

    public void setCustomWordPair(List<WordPair> customWordPair) {
        this.CustomWordPair = customWordPair;
    }

    public List<WordPair> getCustomWordPair() {
        return CustomWordPair;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = new Puzzle(puzzle);
    }

    public WordPairReader getWordPairReader() {
        return wordPairReader;
    }
    public void setWordPairReader(WordPairReader wordPairReader) {
        this.wordPairReader = wordPairReader;
    }
}
