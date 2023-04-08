/*
* Description: This fragment is used to display a dictionary of word pairs to the user when they tap the dictionary button in the puzzleFragment
* The number of times the dictionary is displayed is limited by the difficulty of the puzzle
*  */

package com.echo.wordsudoku.ui.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echo.wordsudoku.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends DialogFragment {

    public static final String TAG = "DictionaryFragment";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";

    private static final String ARG_PARAM4 = "param4";

    private int popupLimitCounter;
    private int popupLimit;

    private ImageButton exitButton;

    //Used to store word pairs passed for PuzzleActivity for dictionary pop up
    private String[] englishWords;
    private String[] frenchWords;

    public DictionaryFragment() {
        // Required empty public constructor
    }


//     Use this factory method to create a new instance of
//     this fragment using the provided parameters.
//
//     returns a new instance of fragment DictionaryFragment.
    public static DictionaryFragment newInstance(String[] englishWords, String[] frenchWords, int popupLimitCounter, int popupLimit) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, englishWords);
        args.putStringArray(ARG_PARAM2, frenchWords);
        args.putInt(ARG_PARAM3, popupLimitCounter);
        args.putInt(ARG_PARAM4, popupLimit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            englishWords = getArguments().getStringArray(ARG_PARAM1);
            frenchWords = getArguments().getStringArray(ARG_PARAM2);
            popupLimitCounter = getArguments().getInt(ARG_PARAM3);
            popupLimit = getArguments().getInt(ARG_PARAM4);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            englishWords = getArguments().getStringArray(ARG_PARAM1);
            frenchWords = getArguments().getStringArray(ARG_PARAM2);
            popupLimitCounter = getArguments().getInt(ARG_PARAM3);
            popupLimit = getArguments().getInt(ARG_PARAM4);
        }

        View view1;
        if (popupLimitCounter < popupLimit) {
            view1 = inflater.inflate(R.layout.fragment_dictionary, container, false);


            //Fills a linearlayout full of TextViews objects, which we set to display English words in word pair list
            LinearLayout linearLayout1 = view1.findViewById(R.id.wordListLang1);
            for (int i = 0; i < englishWords.length; i++) {
                TextView word = new TextView(linearLayout1.getContext());
                word.setText(englishWords[i]);
                word.setPadding(0, 20, 0, 0);
                linearLayout1.addView(word);
            }

            //Same thing but fill linearlayout full of TextView displaying French words
            LinearLayout linearLayout2 = view1.findViewById(R.id.wordListLang2);
            for (int i = 0; i < frenchWords.length; i++) {
                TextView word = new TextView(linearLayout2.getContext());
                word.setText(frenchWords[i]);
                word.setPadding(0, 20, 0, 0);
                linearLayout2.addView(word);
            }

            TextView peeksRemaining = view1.findViewById(R.id.peeksRemaining);
            peeksRemaining.setText(R.string.peeks_Remaining);
            peeksRemaining.append(" " + Integer.toString((popupLimit - popupLimitCounter-1)));

        } else {
            view1 = inflater.inflate(R.layout.fragment_dictionary_limit, container, false);
        }

        // Inflate the layout for this fragment

        exitButton = view1.findViewById(R.id.dictionaryExitButton);
        //Set listener to exit button to end fragment when tapped
        exitButton.setOnClickListener(v -> {
            //Log.d("DictionaryFragment", "ImageButton was pressed");
            //Ends fragment when exit button tapped
            getActivity().getSupportFragmentManager().beginTransaction().remove(DictionaryFragment.this).commit();
        });


        return view1;
    }
}