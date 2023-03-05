package com.echo.wordsudoku.ui.puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.echo.wordsudoku.R;

public class PuzzleTopMenuBarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puzzle_top_menu_bar, container, false);
        Button doneButton = view.findViewById(R.id.finish_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleActivity activity = (PuzzleActivity) PuzzleTopMenuBarFragment.this.getActivity();
                activity.finishButtonPressed();
            }
        });
        ImageButton helpButton = view.findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleActivity activity = (PuzzleActivity) PuzzleTopMenuBarFragment.this.getActivity();
                activity.rulesButtonPressed();
            }
        });
        ImageButton dictionaryButton = view.findViewById(R.id.dictionary_help_button);
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleActivity activity = (PuzzleActivity) PuzzleTopMenuBarFragment.this.getActivity();
                activity.dictionaryButtonPressed();
            }
        });
        return view;
    }


}
