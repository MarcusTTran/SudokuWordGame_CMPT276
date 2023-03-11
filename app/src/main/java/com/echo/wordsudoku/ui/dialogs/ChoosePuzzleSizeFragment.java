package com.echo.wordsudoku.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.destinations.ChoosePuzzleModeFragmentDirections;

public class ChoosePuzzleSizeFragment extends DialogFragment {


    private int mPuzzleSize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_puzzle_size_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        RadioGroup radioGroup = view.findViewById(R.id.size_radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.choose_4x4_button:
                        mPuzzleSize = 4;
                        break;
                    case R.id.choose_6x6_button:
                        mPuzzleSize = 6;
                        break;
                    case R.id.choose_12x12_puzzle:
                        mPuzzleSize = 12;
                        break;
                    default:
                        mPuzzleSize = 9;
                        break;
                }
            }
        });


        Button doneButton = view.findViewById(R.id.done_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePuzzleModeFragmentDirections.ChoosePuzzleModeToStartPuzzleAction action = ChoosePuzzleModeFragmentDirections.choosePuzzleModeToStartPuzzleAction();
                action.setPuzzleSize(mPuzzleSize);
                NavHostFragment navHostFragment =
                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(action);
                dismiss();
            }
        });

    }
}
