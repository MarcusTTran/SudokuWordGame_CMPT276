/*
* Description: This class is the fragment that displays the rules of the word sudoku game to the user
*  */

package com.echo.wordsudoku.ui.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.echo.wordsudoku.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RulesFragment extends DialogFragment {


    public static final String TAG = "RulesFragment";
    private ImageButton exitButton;



    public RulesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_rules, container, false);

        //set Listener onto exit button
        exitButton = view1.findViewById(R.id.rulesExitButton);
        exitButton.setOnClickListener(v -> {
            dismiss();
        });
        return view1;
    }


}