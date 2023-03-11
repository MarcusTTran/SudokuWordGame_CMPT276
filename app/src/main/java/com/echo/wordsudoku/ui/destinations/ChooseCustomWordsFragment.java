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
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseCustomWordsFragment extends Fragment{

    private PuzzleViewModel mPuzzleViewModel;
    private int currentSize;

    private View root;

    private final String KEY_SAVE_BUTTON = "KEY_SAVE_BUTTON_FILL";
    private final String KEY_SAVE_BOARD = "KEY_SAVE_BOARD_FILL";
    private final String KEY_SAVE_CURRENTSIZE = "KEY_SAVE_CURRENTSIZE";


    private List<Integer> idBoardLanguageWords;
    private List<Integer> idButtonLanguageWords;


    private List<String> prefilledBoardLanguageWords;
    private List<String> prefilledButtonLanguageWords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MYTEST","onCreate called");

        super.onCreate(savedInstanceState);

        //From Bundle
        if (savedInstanceState != null) {
            Log.d("MYTEST", "Loading previously saved bundle" );

            int savedSize = savedInstanceState.getInt(KEY_SAVE_CURRENTSIZE, -1);
            this.currentSize = savedSize;
            Log.d("MYTEST", "Currentsize was: " + savedSize);

            List<String> buttonPrefilled = savedInstanceState.getStringArrayList(KEY_SAVE_BUTTON);
            this.prefilledButtonLanguageWords = buttonPrefilled;

            List<String> boardPrefilled = savedInstanceState.getStringArrayList(KEY_SAVE_BOARD);
            this.prefilledBoardLanguageWords = boardPrefilled;


        } else {
            //If no saved bundle is found fill with empty "" strings
            Log.d("MYTEST", "No bundle previously saved" );
            this.currentSize = 4;

            prefilledButtonLanguageWords = initializeBlankStringList();
            prefilledBoardLanguageWords = initializeBlankStringList();
        }

        idBoardLanguageWords = new ArrayList<>();
        idButtonLanguageWords = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d("MYTEST","onCreateView called");

        View root = inflater.inflate(R.layout.fragment_choose_custom_words, container, false);
        this.root = root;

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        // TODO: Get the values of the word pairs user entered and set them in the PuzzleViewModel
        // EditText editText = root.findViewById(R.id.editText);
        // mPuzzleViewModel.setWordPairs(editText.getText().toString());

        Spinner dropdown = root.findViewById(R.id.puzzleSizeCustomDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.puzzle_sizes_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        Log.d("MYTEST", "Prior to initializeEntryBoxes, currentSize is: " + this.currentSize);

        initializeEntryBoxes(root, this.currentSize);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //If first dropdown selected
                    Log.d("MYTEST", "Value: " + position);
                    fillLayoutsWithEditTexts(root, 4);
                    currentSize = 4;
                } else if (position == 1) {
                    //If second dropdown selected
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("MYTEST", "Nothing selected");
                //Nothing selected; Do nothing
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

                    Toast.makeText(getContext(), "Words for Custom Puzzle have been successfully set!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Please fill in all entry boxes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    //Used to initialize String List if Bundle empty in onCreate;
    private List<String> initializeBlankStringList() {
        return Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "");
    }

    //Remove certain amount of EditText
    private void removeEditTexts(View root, int numberToRemove) {
        Log.d("MYTEST", "Number to remove: " + numberToRemove);


        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
        Log.d("MYTEST", "entryBoxHolder1.getChildCounter(): " + entryBoxHolder1.getChildCount());

        for (int i = 0; i < numberToRemove; i++) {
            entryBoxHolder1.removeViewAt(entryBoxHolder1.getChildCount() - 1);
            idBoardLanguageWords.remove(idBoardLanguageWords.size() - 1);
        }

        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);
        Log.d("MYTEST", "entryBoxHolder2.getChildCounter(): " + entryBoxHolder2.getChildCount());

        for (int i = 0; i < numberToRemove; i++) {
            entryBoxHolder2.removeViewAt(entryBoxHolder2.getChildCount() - 1);
            idButtonLanguageWords.remove(idButtonLanguageWords.size() - 1);
        }
    }

    //Add certain amount of EditText
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
            addEditTexts(root, puzzleSize - currentSize);
        } else if (currentSize > puzzleSize) {
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


    //Loaded in first time or on rotation
    private void initializeEntryBoxes(View root, int puzzleSize) {
        //Fill in the Board Language table with EditTexts
        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);
        for (int i = 0; i < puzzleSize; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder1.getContext());
            someEntryBox.setText(prefilledBoardLanguageWords.get(i));
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
            someEntryBox.setText(prefilledButtonLanguageWords.get(i));

            Log.d("MYTEST", "Initializing Entry box as: " + prefilledButtonLanguageWords.get(i));


            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idButtonLanguageWords.add(someEntryBox.getId());
            entryBoxHolder2.addView(someEntryBox);
        }
    }

    //Save our currentSize selected and any text that has been entered in an EditText
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MYTEST", "onSaveInstanceState called");
        Log.d("MYTEST", "CurrentSize at the moment: " + currentSize);
        outState.putInt(KEY_SAVE_CURRENTSIZE, this.currentSize);
//        savePrefilledEditTexts(root, idButtonLanguageWords);
        outState.putStringArrayList(KEY_SAVE_BUTTON, savePrefilledEditTexts(root, idButtonLanguageWords));

        outState.putStringArrayList(KEY_SAVE_BOARD, savePrefilledEditTexts(root, idBoardLanguageWords));



    }

    public ArrayList<String> savePrefilledEditTexts(View root, List<Integer> idLanguageWords) {
        ArrayList<String> savedWordsButton = new ArrayList<>();
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idLanguageWords.get(i));
            savedWordsButton.add(someEntryBox.getText().toString());
        }
        return savedWordsButton;

    }
}
