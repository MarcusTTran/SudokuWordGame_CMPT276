/*
* This is a component of the PuzzleFragment
* This is the fragment that contains the puzzle board view (as a SudokuBoard)
* */

package com.echo.wordsudoku.ui.puzzleParts;

import static com.echo.wordsudoku.models.language.BoardLanguage.ENGLISH;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.views.OnCellTouchListener;
import com.echo.wordsudoku.views.SudokuBoard;

import java.util.Locale;


public class PuzzleBoardFragment extends Fragment {

    private static final String TAG = "PuzzleBoardFragment";

    private SudokuBoard mSudokuBoard;
    private PuzzleViewModel mPuzzleViewModel;

    private final String LOADING_TEXT = "loading";

    private TextToSpeech toSpeech;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puzzle_board, container, false);
        mSudokuBoard = root.findViewById(R.id.sudoku_board);
        mSudokuBoard.setOnCellTouchListener((OnCellTouchListener)getActivity());
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        // Going to fill the puzzle with loading while the puzzle is being loaded
        loadingScreen();

        // This is used for the listening comprehension mode
        toSpeech = new TextToSpeech(this.getContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                try {
                    Locale language = (mPuzzleViewModel.getPuzzleInputLanguage() == ENGLISH) ? Locale.CANADA_FRENCH : Locale.ENGLISH;
                    toSpeech.setLanguage(language);
                } catch (IllegalLanguageException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // This is used to update the puzzle view when the puzzle view changes
        mPuzzleViewModel.getPuzzleView().observe(getViewLifecycleOwner(), puzzleView -> {
            if (puzzleView != null) {
                if(!updateView(puzzleView)) {
                    setPuzzleViewSize(mPuzzleViewModel.getPuzzleDimensions());
                    updateView(puzzleView);
                }
            }
        });
    }

    // This is used to update the puzzle view
    // Changes both the immutability of the cells and the content of the cells
    private boolean updateView(String[][] puzzleView) {
        return mSudokuBoard.setImmutability(mPuzzleViewModel.getImmutableCells()) &&
            mSudokuBoard.setBoard(puzzleView);
    }

    // This is used to fill the puzzle with loading while the puzzle is being loaded
    public void loadingScreen() {
        for (int i = 0; i < mSudokuBoard.getBoardSize(); i++) {
            for (int j = 0; j < mSudokuBoard.getBoardSize(); j++) {
                mSudokuBoard.setWordOfCell(i+1, j+1, LOADING_TEXT);
            }
        }
    }

    // This is used to set the size of the puzzle board
    // Resets the puzzle board and clears everything
    public void setPuzzleViewSize(PuzzleDimensions puzzleDimensions) {
        mSudokuBoard.setNewPuzzleDimensions(puzzleDimensions.getPuzzleDimension(), puzzleDimensions.getEachBoxDimension().getRows(), puzzleDimensions.getEachBoxDimension().getColumns());
    }

    // Returns the selected cell in the puzzle board
    // 0-indexed
    public Dimension getSelectedCell() {
        return new Dimension(mSudokuBoard.getCurrentCellRow()-1, mSudokuBoard.getCurrentCellColumn()-1);
    }

    // This is used to text-to-speech the word in the selected cell
    public void speakWordInCell(Dimension currentCellDimension) {
        if (toSpeech == null) {
            throw new IllegalStateException("speakWordInCell called with TTS being null!"); //TODO catch this unchecked exception from where its called
        } else {
            Cell selectedCell = mPuzzleViewModel.getPuzzle().getCellFromViewablePuzzle(currentCellDimension.getRows(), currentCellDimension.getColumns());
            if (!selectedCell.isEditable())
                speak(selectedCell);
        }
    }

    // Calls the text-to-speech engine to speak out a given string
    private void speak(Cell currentCell) {
        if(currentCell.isEmpty()) {
            return;
        }
        int languageToSpeak = mPuzzleViewModel.getPuzzle().getLanguage();
        String wordToSpeak = currentCell.getContent().getEnglishOrFrench(languageToSpeak);
        toSpeech.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, "");
    }
}
