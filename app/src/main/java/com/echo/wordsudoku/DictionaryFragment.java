package com.echo.wordsudoku;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageButton exitButton;

    // TODO: Rename and change types of parameters
    private String[] mParam1;
    private String[] mParam2;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DictionaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DictionaryFragment newInstance(String[] param1, String[] param2) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, param1);
        args.putStringArray(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArray(ARG_PARAM1);
            mParam2 = getArguments().getStringArray(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_dictionary, container, false);

        exitButton = view1.findViewById(R.id.dictionaryExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DictionaryFragment", "ImageButton was pressed");
                getActivity().getSupportFragmentManager().beginTransaction().remove(DictionaryFragment.this).commit();
            }
        });

        if (getArguments() != null) {
            mParam1 = getArguments().getStringArray(ARG_PARAM1);
            mParam2 = getArguments().getStringArray(ARG_PARAM2);
        }

        LinearLayout linearLayout1 = view1.findViewById(R.id.wordListLang1);
        for (int i = 0; i < mParam1.length; i++) {
            TextView word = new TextView(linearLayout1.getContext());
            word.setText(mParam1[i]);
            word.setPadding(0, 20, 0, 0);
            linearLayout1.addView(word);
        }

        LinearLayout linearLayout2 = view1.findViewById(R.id.wordListLang2);
        for (int i = 0; i < mParam2.length; i++) {
            TextView word = new TextView(linearLayout2.getContext());
            word.setText(mParam2[i]);
            word.setPadding(0, 20, 0, 0);
            linearLayout2.addView(word);
        }

        return view1;
    }
}