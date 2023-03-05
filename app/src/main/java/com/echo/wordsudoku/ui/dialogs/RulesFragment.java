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
 * Use the {@link RulesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RulesFragment extends DialogFragment {


    private ImageButton exitButton;



    public RulesFragment() {
        // Required empty public constructor
    }

//     Use this factory method to create a new instance of
//     this fragment using the provided parameters.
//
//     returns a new instance of fragment RulesFragment.
    public static RulesFragment newInstance() {
        RulesFragment fragment = new RulesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_rules, container, false);

        //set Listener onto button
        exitButton = view1.findViewById(R.id.rulesExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("HelpFragment", "ImageButton was pressed");
                //Ends fragment when exit button tapped
                getActivity().getSupportFragmentManager().beginTransaction().remove(RulesFragment.this).commit();
            }
        });
        return view1;
    }


}