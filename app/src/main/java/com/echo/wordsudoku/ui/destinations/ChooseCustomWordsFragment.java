package com.echo.wordsudoku.ui.destinations;

import android.content.res.Configuration;
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
        Log.d("MYTEST", "onCreate triggered");
        super.onCreate(savedInstanceState);

        //Get the puzzle size
        if (getArguments() != null) {
            puzzleSize = getArguments().getInt("puzzleSize");
        }

        //Initialize our List of id for the EditText views
        entryBox1IDs = new ArrayList<>();
        entryBox2IDs = new ArrayList<>();

        //Initialize list to store prefilled entry boxes if device is rotated


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MYTEST", "onCreateView triggered");

        View view1 = super.onCreateView(inflater, container, savedInstanceState);

        int detectOrientations = getResources().getConfiguration().orientation;

        //If orientation is landscape fill layout with 4 columns, not 2
        if (detectOrientations == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("MYTEST", "Landscape detected");

            LinearLayout entryBoxHolder1 = view1.findViewById(R.id.entryBoxHolder1);
            entryBoxHolder1.setGravity(Gravity.CENTER);
            for (int i = 0; i < puzzleSize/2; i++) {
                EditText entryBox = new EditText(entryBoxHolder1.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
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
            for (int i = 0; i < puzzleSize/2; i++) {
                EditText entryBox = new EditText(entryBoxHolder2.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                entryBox.setId(View.generateViewId());
                entryBox2IDs.add(entryBox.getId());
                entryBoxHolder2.addView(entryBox);
            }

            //If the puzzle is 9x9, we must add extra entry box to the first 2 columns
            if (puzzleSize == 9) {
                EditText entryBox = new EditText(entryBoxHolder1.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                //Create a Id for every entry box
                entryBox.setId(View.generateViewId());
                //Save the Id in a List to access later
                entryBox1IDs.add(entryBox.getId());
                entryBoxHolder1.addView(entryBox);

                entryBox = new EditText(entryBoxHolder2.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                entryBox.setId(View.generateViewId());
                entryBox2IDs.add(entryBox.getId());
                entryBoxHolder2.addView(entryBox);
            }

            //Fill out the extra 3rd and 4th column
            LinearLayout entryBoxHolder3 = view1.findViewById(R.id.entryBoxHolder3);
            entryBoxHolder3.setGravity(Gravity.CENTER);
            for (int i = 0; i < puzzleSize/2; i++) {
                EditText entryBox = new EditText(entryBoxHolder3.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                //Create a Id for every entry box
                entryBox.setId(View.generateViewId());
                //Save the Id in a List to access later
                entryBox1IDs.add(entryBox.getId());
                entryBoxHolder3.addView(entryBox);
            }

            //Create left side LinearLayout and fill with EditText views (entry boxes)
            LinearLayout entryBoxHolder4 = view1.findViewById(R.id.entryBoxHolder4);
            entryBoxHolder4.setGravity(Gravity.CENTER);
            for (int i = 0; i < puzzleSize/2; i++) {
                EditText entryBox = new EditText(entryBoxHolder4.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                entryBox.setId(View.generateViewId());
                entryBox2IDs.add(entryBox.getId());
                entryBoxHolder4.addView(entryBox);
            }



        //Else fill up each column with regular amount
        } else {
            //Create right side LinearLayout and fill with EditText views (entry boxes)
            LinearLayout entryBoxHolder1 = view1.findViewById(R.id.entryBoxHolder1);
            entryBoxHolder1.setGravity(Gravity.CENTER);
            for (int i = 0; i < puzzleSize; i++) {
                EditText entryBox = new EditText(entryBoxHolder1.getContext());
                entryBox.setText("");
                entryBox.setEms(6);
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
                entryBox.setEms(6);
                entryBox.setTextSize(15);
                entryBox.setId(View.generateViewId());
                entryBox2IDs.add(entryBox.getId());
                entryBoxHolder2.addView(entryBox);
            }
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
                        Log.d("MYTEST", "Null value found, display toast warning");
                        return;
                    }
                }
                Log.d("MYTEST", "No nulls found, method continued");
                //Take the words entered in the EditTexts and pass to next fragment

            }
        });

        return view1;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
