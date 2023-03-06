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
    private final MutableLiveData<Integer> boardLanguage = new MutableLiveData<>();
    private final MutableLiveData<Puzzle> puzzle = new MutableLiveData<>();
    private  WordPairReader wordPairReader;

    public List<WordPair> getWordPairs() {
        return puzzle.getValue().getWordPairs();
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
        this.puzzle.setValue(new Puzzle(puzzle));
    }

    public WordPairReader getWordPairReader() {
        return wordPairReader;
    }
    public void setWordPairReader(WordPairReader wordPairReader) {
        this.wordPairReader = wordPairReader;
    }
}
