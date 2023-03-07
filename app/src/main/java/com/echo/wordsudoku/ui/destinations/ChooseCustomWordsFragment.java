package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class ChooseCustomWordsFragment extends Fragment{

    private PuzzleViewModel mPuzzleViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_custom_words, container, false);

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        // TODO: Get the values of the word pairs user entered and set them in the PuzzleViewModel
        // EditText editText = root.findViewById(R.id.editText);
        // mPuzzleViewModel.setWordPairs(editText.getText().toString());

        return root;
    }
}
