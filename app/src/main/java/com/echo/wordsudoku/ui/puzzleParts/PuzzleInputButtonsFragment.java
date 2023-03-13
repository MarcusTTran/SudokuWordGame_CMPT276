package com.echo.wordsudoku.ui.puzzleParts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.destinations.PuzzleFragment;

import java.util.List;

public class PuzzleInputButtonsFragment extends Fragment {

    private PuzzleViewModel mPuzzleViewModel;
    private LinearLayout mLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puzzle_input_buttons, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PuzzleDimensions puzzleDimension = mPuzzleViewModel.getPuzzleDimensions();
            if (puzzleDimension != null) {
                mLinearLayout = view.findViewById(R.id.board_input_buttons);
                if (mLinearLayout.getChildCount() > 0) {
                    mLinearLayout.removeAllViews();
                }
                Button[] buttons = new Button[puzzleDimension.getPuzzleDimension()];
                int rows_of_button = Math.max(puzzleDimension.getEachBoxDimension().getRows(), puzzleDimension.getEachBoxDimension().getColumns());
                int columns_of_button = Math.min(puzzleDimension.getEachBoxDimension().getRows(), puzzleDimension.getEachBoxDimension().getColumns());
                for (int i = 0; i < rows_of_button; i++) {
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    for (int j = 0; j < columns_of_button; j++) {
                        Button button = new Button(getContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.width = 0;
                        params.weight = 1;
                        button.setLayoutParams(params);
                        linearLayout.addView(button);
                        buttons[i * columns_of_button + j] = button;
                    }
                    mLinearLayout.addView(linearLayout);
                }
                List<WordPair> wordPairs = mPuzzleViewModel.getWordPairs();

                if (buttons.length != wordPairs.size()) {
                    throw new IllegalArgumentException("The number of buttons must match the number of word pairs");
                } else {
                    for (int i = 0; i < wordPairs.size(); i++) {
                        buttons[i].setText(wordPairs.get(i).getEnglishOrFrench(mPuzzleViewModel.getPuzzleInputLanguage()));
                        buttons[i].setOnClickListener(v -> {
                            PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                            Button button = (Button) v;
                            puzzleFragment.enterWordInBoard(button.getText().toString());
                        });
                    }

                }
            }

    }
}