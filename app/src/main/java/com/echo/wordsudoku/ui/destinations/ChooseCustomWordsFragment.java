package com.echo.wordsudoku.ui.destinations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseCustomWordsFragment extends Fragment{

    private PuzzleViewModel mPuzzleViewModel;
    private int currentSize;

    private List<Integer> idBoardLanguageWords;
    private List<Integer> idButtonLanguageWords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idBoardLanguageWords = new ArrayList<>();
        idButtonLanguageWords = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choose_custom_words, container, false);

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        // TODO: Get the values of the word pairs user entered and set them in the PuzzleViewModel
        // EditText editText = root.findViewById(R.id.editText);
        // mPuzzleViewModel.setWordPairs(editText.getText().toString());

        Spinner dropdown = root.findViewById(R.id.puzzleSizeCustomDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.puzzle_sizes_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);


        if (dropdown.getSelectedItemPosition() == 0) {
            currentSize = 4;
        } else if (dropdown.getSelectedItemPosition() == 1) {
            currentSize = 6;
        } else if (dropdown.getSelectedItemPosition() == 2) {
            currentSize = 9;
        } else {
            currentSize = 12;
        }
        initializeEntryBoxes(root, currentSize);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Log.d("MYTEST", "Value: " + position);
                    fillLayoutsWithEditTexts(root, 4);
                    currentSize = 4;
                } else if (position == 1) {
                    Log.d("MYTEST", "Value: " + position);
                    fillLayoutsWithEditTexts(root, 6);
                    currentSize = 6;
                } else if(position == 2) {
                    Log.d("MYTEST", "Value: " + position);
                    fillLayoutsWithEditTexts(root, 9);
                    currentSize = 9;
                } else {
                    Log.d("MYTEST", "Value: " + position);
                    fillLayoutsWithEditTexts(root, 12);
                    currentSize = 12;
                }
//                fillLayoutsWithEditTexts(root, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("MYTEST", "Nothing selected");
                //Do nothing
            }
        });

        Button confirmButton = root.findViewById(R.id.buttonConfirmCustomWords);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all EntryBoxes are full
                if (isEntryBoxesFull(root)) {
                    Log.d("MYTEST", "clicked on Confirm");
                    //Turn the EntryBoxes into a WordPair List
                    List<WordPair> userEnteredWordList = new ArrayList<>();
                    for (int i = 0; i < currentSize; i++) {
//                    userEnteredWordList.add()
                        EditText someEntryBox1 = root.findViewById(idBoardLanguageWords.get(i));
                        EditText someEntryBox2 = root.findViewById(idButtonLanguageWords.get(i));

                        Log.d("MYTEST", "Board Language Words[" + i +"]: " + someEntryBox1.getText().toString());
                        Log.d("MYTEST", "Button Language Words[" + i +"]: " + someEntryBox2.getText().toString());

                        //Add in each word to WordPair list
                        userEnteredWordList.add(new WordPair(someEntryBox1.getText().toString(), someEntryBox2.getText().toString()));
                    }
                    Log.d("MYTEST", "Size of WordPair list: " + userEnteredWordList.size());

                    //Set the PuzzleViewModel to store the WordPair list we made
                    mPuzzleViewModel.setWordPairs(userEnteredWordList);

                } else {
                    Toast.makeText(getContext(), "Please fill in all entry boxes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void removeEditTexts(View root, int numberToRemove) {
        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
        entryBoxHolder1.getChildCount();
        Log.d("MYTEST", "Number to remove: " + numberToRemove);
        Log.d("MYTEST", "entryBoxHolder1.getChildCounter(): " + entryBoxHolder1.getChildCount());

        for (int i = 0; i < numberToRemove; i++) {
            entryBoxHolder1.removeViewAt(entryBoxHolder1.getChildCount() - 1);
            idBoardLanguageWords.remove(idBoardLanguageWords.size() - 1);
        }
    }

    private void addEditTexts(View root, int numberToAdd) {
        //Add EditTexts to Board Language table
        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
        entryBoxHolder1.getChildCount();
        for (int i = 0; i < numberToAdd; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder1.getContext());
            someEntryBox.setText("");
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idBoardLanguageWords.add(someEntryBox.getId());
            entryBoxHolder1.addView(someEntryBox);
        }

        //Add EditTexts to Board Language table
        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);
        entryBoxHolder2.getChildCount();
        for (int i = 0; i < numberToAdd; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder2.getContext());
            someEntryBox.setText("");
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idButtonLanguageWords.add(someEntryBox.getId());
            entryBoxHolder2.addView(someEntryBox);
        }
    }



    private void fillLayoutsWithEditTexts(View root, int puzzleSize) {
        //if currentSize < puzzleSize, add this many EditTexts to the tables
        //if currentSize > puzzleSize, remove this many EditTexts from the tables
        if (currentSize < puzzleSize) {
//            LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
            addEditTexts(root, puzzleSize - currentSize);
        } else if (currentSize > puzzleSize) {
//            LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
            removeEditTexts(root, currentSize - puzzleSize);

        } else {
            //If same size selected, do nothing
        }
    }

    //Check if entry boxes are full
    private boolean isEntryBoxesFull(View root) {
        //Check that Board Language table is full
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idBoardLanguageWords.get(i));
            String someString = someEntryBox.getText().toString();
            if (someString.equals("") || someString == null) {
                return false;
            }
        }

        //Check that Button Language table is full
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idButtonLanguageWords.get(i));
            String someString = someEntryBox.getText().toString();
            if (someString.equals("") || someString == null) {
                return false;
            }
        }
        return true;
    }



    private void initializeEntryBoxes(View root, int puzzleSize) {
        //Fill in the Board Language table with EditTexts
        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
        for (int i = 0; i < puzzleSize; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder1.getContext());
            someEntryBox.setText("");
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idBoardLanguageWords.add(someEntryBox.getId());
            entryBoxHolder1.addView(someEntryBox);
        }

        //Fill in the Button Language table with EditTexts
        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);
        for (int i = 0; i < puzzleSize; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder2.getContext());
            someEntryBox.setText("");
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idButtonLanguageWords.add(someEntryBox.getId());
            entryBoxHolder2.addView(someEntryBox);
        }
    }



}
