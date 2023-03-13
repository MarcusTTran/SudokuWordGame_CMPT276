package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleSizeFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;


public class ChoosePuzzleModeFragment extends Fragment {


    private static final String TAG = "ChoosePuzzleModeFragment";

    private PuzzleViewModel mPuzzleViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_choose_puzzle_mode, container, false);
        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button classicButton = view.findViewById(R.id.classic_puzzle_button);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        classicButton.setOnClickListener(v -> {
            navController.navigate(R.id.startPuzzleModeAction);
        });


        Button customButton = view.findViewById(R.id.custom_size_button);

        customButton.setOnClickListener(v -> {
                    new ChoosePuzzleSizeFragment().show(getChildFragmentManager(), ChoosePuzzleSizeFragment.TAG);
        });


        Button customWords = view.findViewById(R.id.custom_words_button);

        customWords.setOnClickListener(v -> {
            // If user has not entered custom words, navigate to custom words fragment
            if(mPuzzleViewModel.getCustomWordPair()==null){
                navController.navigate(R.id.chooseCustomWordsFragment);
            } else {
                //  If user has entered custom words, navigate to start puzzle fragment
                ChoosePuzzleModeFragmentDirections.StartPuzzleModeAction action = ChoosePuzzleModeFragmentDirections.startPuzzleModeAction();
                action.setIsCustomGame(true);
                navController.navigate(action);
            }
        });

    }

}
