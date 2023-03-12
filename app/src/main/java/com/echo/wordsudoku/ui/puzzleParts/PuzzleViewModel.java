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

    private boolean isGameSaved = false;

    private List<WordPair> CustomWordPair;
    private WordPairReader wordPairReader;

    public List<WordPair> getWordPairs() {
        return puzzle.getWordPairs();
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

    public void setGameSaved(boolean gameSaved) {
        isGameSaved = gameSaved;
    }

    public boolean isGameSaved() {
        return isGameSaved;
    }
}
