package com.echo.wordsudoku.ui.puzzleParts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.views.SudokuBoard;


public class PuzzleBoardFragment extends Fragment {

    private static final String TAG = "PuzzleBoardFragment";

    private SudokuBoard mSudokuBoard;
    private PuzzleViewModel mPuzzleViewModel;;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_puzzle_board, container, false);
        mSudokuBoard = root.findViewById(R.id.sudoku_board);
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        // Going to fill the puzzle with loading while the puzzle is being loaded
        for (int i = 0; i < mSudokuBoard.getBoardSize(); i++) {
            for (int j = 0; j < mSudokuBoard.getBoardSize(); j++) {
                mSudokuBoard.setWordOfCell(i+1, j+1, "loading");
            }
        }

//        mSudokuBoard.setNewPuzzleDimensions(6,2,3);
//        mSudokuBoard.setBoard(mPuzzleViewModel.getPuzzle().getValue().toStringArray());
        return root;
    }

    public void setPuzzleViewSize(PuzzleDimensions puzzleDimensions) {
        mSudokuBoard.setNewPuzzleDimensions(puzzleDimensions.getPuzzleDimension(), puzzleDimensions.getEachBoxDimension().getRows(), puzzleDimensions.getEachBoxDimension().getColumns());
    }

    public void insertWordInBoardView(String word) {
        mSudokuBoard.insertWord(word);
    }

    public void updateBoardWithPuzzleModel() {
        PuzzleDimensions puzzleDimensions = mPuzzleViewModel.getPuzzle().getPuzzleDimension();
        mSudokuBoard.setNewPuzzleDimensions(puzzleDimensions.getPuzzleDimension(), puzzleDimensions.getEachBoxDimension().getRows(), puzzleDimensions.getEachBoxDimension().getColumns());
        mSudokuBoard.setBoard(mPuzzleViewModel.getPuzzle().toStringArray());
    }

    public Dimension getSelectedCell() {
        return new Dimension(mSudokuBoard.getCurrentCellRow()-1, mSudokuBoard.getCurrentCellColumn()-1);
    }
}
