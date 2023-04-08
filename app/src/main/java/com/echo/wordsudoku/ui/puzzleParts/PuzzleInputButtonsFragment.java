/*
* Description: This fragment is used to display the word buttons that the user can click to enter words into the puzzle.
* It is dynamically created based on the dimensions of the puzzle and the words that are to be entered from Puzzle.getWordPairs().
* */

package com.echo.wordsudoku.ui.puzzleParts;

import android.content.res.TypedArray;
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
import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.models.dimension.PuzzleDimensions;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.MainActivity;
import com.echo.wordsudoku.ui.destinations.PuzzleFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleInputButtonsFragment extends Fragment {

    private PuzzleViewModel mPuzzleViewModel;
    private LinearLayout mLinearLayout;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        view = inflater.inflate(R.layout.fragment_puzzle_input_buttons, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // If the puzzle has already been loaded, create the buttons
        if (mPuzzleViewModel.getWordPairs()!=null) {
            try {
                if (!createButtons())
                    ((MainActivity)requireActivity()).fatalErrorDialog("Error in creating buttons");
            } catch (IllegalLanguageException e) {
                throw new RuntimeException(e);
            }
        } else {
            // If the puzzle has not been loaded, wait for it to be loaded and then create the buttons
            mPuzzleViewModel.getPuzzleView().observe(getViewLifecycleOwner(), puzzleView -> {
                if (puzzleView != null) {
                    try {
                        if (!createButtons())
                            ((MainActivity)requireActivity()).fatalErrorDialog("Error in creating buttons");
                    } catch (IllegalLanguageException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    // Create the buttons for the puzzle
    // Returns true if successful, false otherwise
    public boolean createButtons() throws IllegalLanguageException {
        PuzzleDimensions puzzleDimension = mPuzzleViewModel.getPuzzleDimensions();
        if (puzzleDimension != null) {
            mLinearLayout = view.findViewById(R.id.board_input_buttons);
            if (mLinearLayout.getChildCount() > 0) {
                mLinearLayout.removeAllViews();
            }
            Button[] buttons = new Button[puzzleDimension.getPuzzleDimension()];
            int rows_of_button = Math.max(puzzleDimension.getEachBoxDimension().getRows(), puzzleDimension.getEachBoxDimension().getColumns());
            int columns_of_button = Math.min(puzzleDimension.getEachBoxDimension().getRows(), puzzleDimension.getEachBoxDimension().getColumns());

            //Retrieve resource ids for buttons (used by uiautomator)
            TypedArray typedButtonIds = getResources().obtainTypedArray(R.array.inputButtonIds);
            int[] intButtonIds = new int[typedButtonIds.length()];
            for (int i = 0; i < typedButtonIds.length(); i++) {
                intButtonIds[i] = typedButtonIds.getResourceId(i, 0);
            }
            typedButtonIds.recycle();

            int idCounter = 0;
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
                    // Set ID for every button (used by uiautomator)
                    button.setId(intButtonIds[idCounter++]);

                    linearLayout.addView(button);
                    buttons[i * columns_of_button + j] = button;
                }
                mLinearLayout.addView(linearLayout);
            }
            List<WordPair> wordPairs = new ArrayList<>(mPuzzleViewModel.getWordPairs());
            Collections.shuffle(wordPairs);

            if (buttons.length != wordPairs.size()) {
                return false;
            } else {
                for (int i = 0; i < wordPairs.size(); i++) {
                    buttons[i].setText(wordPairs.get(i).getLang2());
                    buttons[i].setOnClickListener(v -> {
                        PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                        Button button = (Button) v;
                        try {
                            puzzleFragment.enterWordInBoard(button.getText().toString());
                        } catch (IllegalWordPairException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalDimensionException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return true;
            }
        }
        return false;
    }
}