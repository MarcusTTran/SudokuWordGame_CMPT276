package com.echo.wordsudoku;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RulesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */// implements View.OnClickListener
public class RulesFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageButton exitButton;

    // TODO: Rename and change types of parameters


    public RulesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        exitButton = view1.findViewById(R.id.rulesExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HelpFragment", "ImageButton was pressed");
                getActivity().getSupportFragmentManager().beginTransaction().remove(RulesFragment.this).commit();
            }
        });
        return view1;
    }


}