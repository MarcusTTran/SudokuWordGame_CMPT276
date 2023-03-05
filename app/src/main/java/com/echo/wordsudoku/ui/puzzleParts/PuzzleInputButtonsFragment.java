package com.echo.wordsudoku.ui.puzzleParts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.destinations.PuzzleFragment;

import java.util.List;

public class PuzzleInputButtonsFragment extends Fragment {

    private PuzzleViewModel mPuzzleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puzzle_input_buttons, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button[] buttons = {getView().findViewById(R.id.button1),
                getView().findViewById(R.id.button2),
                getView().findViewById(R.id.button3),
                getView().findViewById(R.id.button4),
                getView().findViewById(R.id.button5),
                getView().findViewById(R.id.button6),
                getView().findViewById(R.id.button7),
                getView().findViewById(R.id.button8),
                getView().findViewById(R.id.button9)};
        mPuzzleViewModel.getWordPairs().observe(getViewLifecycleOwner(), wordPairs -> {
            initializeButtons(buttons, wordPairs, BoardLanguage.getOtherLanguage(mPuzzleViewModel.getBoardLanguage().getValue()));
        });
    }

    // This method sets the labels of the buttons and adds on Click Listeners to them
    // @param buttons The array of buttons
    // @param language The language to be used for the button labels
    private void initializeButtons(Button[] buttons, List<WordPair> mWordPairs, int mPuzzleLanguage)
    {
        if (buttons.length != mWordPairs.size())
        {
            throw new IllegalArgumentException("The number of buttons must match the number of word pairs");
        } else {
            for (int i = 0; i < mWordPairs.size(); i++) {
                buttons[i].setText(mWordPairs.get(i).getEnglishOrFrench(mPuzzleLanguage));
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PuzzleFragment puzzleFragment = (PuzzleFragment) getParentFragment();
                        Button button = (Button) v;
                        puzzleFragment.enterWordInBoard(button.getText().toString());
                    }
                });
            }
        }
    }


}