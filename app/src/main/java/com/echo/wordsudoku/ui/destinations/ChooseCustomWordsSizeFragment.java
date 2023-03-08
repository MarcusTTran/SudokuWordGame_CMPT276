package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCustomWordsSizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCustomWordsSizeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseCustomWordsSizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCustomWordsSizeFragment newInstance(String param1, String param2) {
        ChooseCustomWordsSizeFragment fragment = new ChooseCustomWordsSizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_choose_custom_words_size, container, false);

        Button btn4x4 = view1.findViewById(R.id.btn4x4);
        Button btn6x6 = view1.findViewById(R.id.btn6x6);
        Button btn9x9 = view1.findViewById(R.id.btn9x9);
        Button btn12x12 = view1.findViewById(R.id.btn12x12);

        btn4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puzzleSize = 4;
                Bundle tempBundle = new Bundle();
                tempBundle.putInt("puzzleSize", puzzleSize);
                Navigation.findNavController(v).navigate(R.id.chooseCustomWordsFragment, tempBundle);
            }
        });

        btn6x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puzzleSize = 6;
                Bundle tempBundle = new Bundle();
                tempBundle.putInt("puzzleSize", puzzleSize);
                Navigation.findNavController(v).navigate(R.id.action_chooseCustomWordsSizeFragment_to_chooseCustomWordsFragment2, tempBundle);
            }
        });

        btn9x9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puzzleSize = 9;
                Bundle tempBundle = new Bundle();
                tempBundle.putInt("puzzleSize", puzzleSize);
                Navigation.findNavController(v).navigate(R.id.action_chooseCustomWordsSizeFragment_to_chooseCustomWordsFragment2, tempBundle);
            }
        });

        btn12x12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int puzzleSize = 12;
                Bundle tempBundle = new Bundle();
                tempBundle.putInt("puzzleSize", puzzleSize);
                Navigation.findNavController(v).navigate(R.id.action_chooseCustomWordsSizeFragment_to_chooseCustomWordsFragment2, tempBundle);
            }
        });


        return view1;
    }
}