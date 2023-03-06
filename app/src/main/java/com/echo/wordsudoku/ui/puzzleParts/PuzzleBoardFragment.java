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
        mSudokuBoard.setBoard(mPuzzleViewModel.getPuzzle().getValue().toStringArray());
        mPuzzleViewModel.getPuzzle().observe(getViewLifecycleOwner(), puzzle -> {
            mSudokuBoard.setBoard(puzzle.toStringArray());
        });
        return root;
    }

    public Dimension getSelectedCell() {
        return new Dimension(mSudokuBoard.getCurrentCellRow()-1, mSudokuBoard.getCurrentCellColumn()-1);
    }
}
