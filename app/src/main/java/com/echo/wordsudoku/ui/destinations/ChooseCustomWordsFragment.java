package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.echo.wordsudoku.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseCustomWordsFragment extends Fragment{



    private int puzzleSize;
    private List<Integer> entryBox1IDs;
    private List<Integer> entryBox2IDs;

    public ChooseCustomWordsFragment() {
        super(R.layout.fragment_choose_custom_words);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the puzzle size
        if (getArguments() != null) {
            puzzleSize = getArguments().getInt("puzzleSize");
        }
        //Initialize our List of id for the EditText views
        entryBox1IDs = new ArrayList<>();
        entryBox2IDs = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = super.onCreateView(inflater, container, savedInstanceState);

        //Create right side LinearLayout and fill with EditText views (entry boxes)
        LinearLayout entryBoxHolder1 = view1.findViewById(R.id.entryBoxHolder1);
        entryBoxHolder1.setGravity(Gravity.CENTER);
        for (int i = 0; i < puzzleSize; i++) {
            EditText entryBox = new EditText(entryBoxHolder1.getContext());
            entryBox.setText("");
            entryBox.setEms(8);
            entryBox.setTextSize(15);
            //Create a Id for every entry box
            entryBox.setId(View.generateViewId());
            //Save the Id in a List to access later
            entryBox1IDs.add(entryBox.getId());
            entryBoxHolder1.addView(entryBox);
        }

        //Create left side LinearLayout and fill with EditText views (entry boxes)
        LinearLayout entryBoxHolder2 = view1.findViewById(R.id.entryBoxHolder2);
        entryBoxHolder2.setGravity(Gravity.CENTER);
        for (int i = 0; i < puzzleSize; i++) {
            EditText entryBox = new EditText(entryBoxHolder2.getContext());
            entryBox.setText("");
            entryBox.setEms(8);
            entryBox.setTextSize(15);
            entryBox.setId(View.generateViewId());
            entryBox2IDs.add(entryBox.getId());
            entryBoxHolder2.addView(entryBox);
        }

        Button confirmButton = view1.findViewById(R.id.confirmCustomPuzzle);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that all entry boxes are full
                for (int i = 0; i < puzzleSize; i++) {
                    EditText entryBox1s = view1.findViewById(entryBox1IDs.get(i));
                    EditText entryBox2s = view1.findViewById(entryBox2IDs.get(i));

                    String k1 = entryBox1s.getText().toString();
                    String k2 = entryBox2s.getText().toString();

                    Log.d("MYTEST", "Value: " + k1);
                    Log.d("MYTEST", "Value: " + k2);

                    //If any of the entry boxes are empty
                    if (k1.equals("") || k1 == null || k2.equals("") || k2 == null) {
                        Toast.makeText(getContext(), "Please fill in all entry boxes", Toast.LENGTH_SHORT).show();
                        Log.d("MYTEST", "Null value found, ending method");
                        return;
                    }
                }
                Log.d("MYTEST", "No nulls found, method continued");
                //Take the words entered in the edittexts and pass to puzzlefragment

            }
        });




        return view1;
    }

}
