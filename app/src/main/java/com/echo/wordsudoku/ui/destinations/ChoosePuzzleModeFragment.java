package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.dialogs.ChoosePuzzleSizeFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class ChoosePuzzleModeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_choose_puzzle_mode, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button classicButton = view.findViewById(R.id.classic_puzzle_button);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.puzzleFragment);
            }
        });


        Button customButton = view.findViewById(R.id.custom_size_button);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ChoosePuzzleSizeFragment choosePuzzleSizeFragment = new ChoosePuzzleSizeFragment();
                choosePuzzleSizeFragment.show(getFragmentManager(), "ChoosePuzzleSizeFragment");
                PuzzleViewModel puzzleViewModel = new ViewModelProvider(getActivity()).get(PuzzleViewModel.class);
                puzzleViewModel.getPuzzleDimensions().observe(getViewLifecycleOwner(), puzzleDimensions -> {
                    if(puzzleViewModel.getPuzzleDimensions().getValue() != null)
                        System.out.println(puzzleViewModel.getPuzzleDimensions().getValue().getPuzzleDimension());
                });
            }
        });


        Button customWords = view.findViewById(R.id.custom_words_button);

        customWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
