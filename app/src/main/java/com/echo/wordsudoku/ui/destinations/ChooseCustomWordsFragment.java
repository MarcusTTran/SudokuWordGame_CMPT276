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
import java.util.Arrays;
import java.util.List;

public class ChooseCustomWordsFragment extends Fragment{

    private PuzzleViewModel mPuzzleViewModel;

    private final String CC_WORDS_DEBUG_KEY = "CCUSTOM_WORDS_DEBUG_KEY";

    //Store the currentSize selected
    private int currentSize;

    private View root;

    private final String KEY_SAVE_BUTTON = "KEY_SAVE_BUTTON_FILL";
    private final String KEY_SAVE_BOARD = "KEY_SAVE_BOARD_FILL";
    private final String KEY_SAVE_CURRENTSIZE = "KEY_SAVE_CURRENTSIZE";


    //Store the ID of each EditText we dynamically create
    private List<Integer> idEnglishWords;
    private List<Integer> idFrenchWords;

    //Store any filled EditTexts when user rotates the screen
    private List<String> prefilledEnglishWords;
    private List<String> prefilledFrenchWords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(CC_WORDS_DEBUG_KEY,"onCreate called");

        super.onCreate(savedInstanceState);

        //If Bundle not empty; Load any of the previously filled EditTexts into the current existing ones
        if (savedInstanceState != null) {
            Log.d(CC_WORDS_DEBUG_KEY, "Loading previously saved bundle" );

            int savedSize = savedInstanceState.getInt(KEY_SAVE_CURRENTSIZE, -1);
            this.currentSize = savedSize;
            Log.d(CC_WORDS_DEBUG_KEY, "Currentsize was: " + savedSize);

            List<String> buttonPrefilled = savedInstanceState.getStringArrayList(KEY_SAVE_BUTTON);
            this.prefilledFrenchWords = buttonPrefilled;

            List<String> boardPrefilled = savedInstanceState.getStringArrayList(KEY_SAVE_BOARD);
            this.prefilledEnglishWords = boardPrefilled;


        } else {
            //If no saved bundle is found fill with empty "" strings
            Log.d(CC_WORDS_DEBUG_KEY, "No bundle previously saved" );
            this.currentSize = 4;

            prefilledFrenchWords = initializeBlankStringList();
            prefilledEnglishWords = initializeBlankStringList();
        }

        idEnglishWords = new ArrayList<>();
        idFrenchWords = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(CC_WORDS_DEBUG_KEY,"onCreateView called");

        View root = inflater.inflate(R.layout.fragment_choose_custom_words, container, false);
        this.root = root;

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);

        //Create our dropdown and fill each selection with values from resource file
        Spinner dropdown = root.findViewById(R.id.puzzleSizeCustomDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.puzzle_sizes_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        Log.d(CC_WORDS_DEBUG_KEY, "Prior to initializeEntryBoxes, currentSize is: " + this.currentSize);

        //Create the EditTexts
        addEditTexts(root, this.currentSize,true);

        int[] dropDownIndexToSize = {4,6,9,12};

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillLayoutsWithEditTexts(root, dropDownIndexToSize[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(CC_WORDS_DEBUG_KEY, "Nothing selected");
                //Nothing selected; Do nothing
            }
        });

        Button confirmButton = root.findViewById(R.id.buttonConfirmCustomWords);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            //On Confirm button click
            @Override
            public void onClick(View v) {

                //Check if all EntryBoxes are full
                if (isEntryBoxesFull(root)) {
                    Log.d(CC_WORDS_DEBUG_KEY, "clicked on Confirm");
                    //Take all words entered in the EntryBoxes and use them to make a WordPair List
                    List<WordPair> userEnteredWordList = new ArrayList<>();
                    for (int i = 0; i < currentSize; i++) {
                        //Entry Boxes under Board Language are first argument in WordPair
                        EditText someEntryBox1 = root.findViewById(idEnglishWords.get(i));
                        //Entry Boxes under Button Language are second argument in WordPair
                        EditText someEntryBox2 = root.findViewById(idFrenchWords.get(i));

                        //Validate Words are being read with Log.d
//                        Log.d(CC_WORDS_DEBUG_KEY, "Board Language Words[" + i +"]: " + someEntryBox1.getText().toString());
//                        Log.d(CC_WORDS_DEBUG_KEY, "Button Language Words[" + i +"]: " + someEntryBox2.getText().toString());

                        //Add in each word to WordPair list
                        userEnteredWordList.add(new WordPair(someEntryBox1.getText().toString(), someEntryBox2.getText().toString()));
                    }
                    Log.d(CC_WORDS_DEBUG_KEY, "Size of WordPair list: " + userEnteredWordList.size());

                    //Set the PuzzleViewModel to store the WordPair list we made
                    mPuzzleViewModel.setCustomWordPair(userEnteredWordList);

                    Toast.makeText(getContext(), "Words for Custom Puzzle have been successfully set.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Please fill in all entry boxes!", Toast.LENGTH_SHORT).show();
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
        Log.d(CC_WORDS_DEBUG_KEY, "Number to of EditTexts to remove: " + numberToRemove);

        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);

        //Double check number of view children in the holder of EditTexts
        Log.d(CC_WORDS_DEBUG_KEY, "Value of entryBoxHolder1.getChildCounter(): " + entryBoxHolder1.getChildCount());

        for (int i = 0; i < numberToRemove; i++) {
            entryBoxHolder1.removeViewAt(entryBoxHolder1.getChildCount() - 1);
            idEnglishWords.remove(idEnglishWords.size() - 1);
        }

        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);
        Log.d(CC_WORDS_DEBUG_KEY, "Value of entryBoxHolder2.getChildCounter(): " + entryBoxHolder2.getChildCount());

        for (int i = 0; i < numberToRemove; i++) {
            entryBoxHolder2.removeViewAt(entryBoxHolder2.getChildCount() - 1);
            idFrenchWords.remove(idFrenchWords.size() - 1);
        }
    }

    //Add certain amount of EditText
    private void addEditTexts(View root, int numberToAdd,boolean isInitialize) {
        //Add EditTexts to Board Language table
        LinearLayout entryBoxHolder1 = root.findViewById(R.id.boardLanguageEntries);

        for (int i = 0; i < numberToAdd; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder1.getContext());
            if (!isInitialize) {
                someEntryBox.setText("");
            } else {
                someEntryBox.setText(prefilledEnglishWords.get(i));
            }
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idEnglishWords.add(someEntryBox.getId());
            entryBoxHolder1.addView(someEntryBox);
        }

        //Add EditTexts to Board Language table
        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);

        for (int i = 0; i < numberToAdd; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder2.getContext());
            if (!isInitialize) {
                someEntryBox.setText("");
            } else {
                someEntryBox.setText(prefilledFrenchWords.get(i));
            }
            someEntryBox.setEms(6);
            someEntryBox.setTextSize(15);
            someEntryBox.setId(View.generateViewId());
            idFrenchWords.add(someEntryBox.getId());
            entryBoxHolder2.addView(someEntryBox);
        }
    }

    private void fillLayoutsWithEditTexts(View root, int puzzleSize) {
        //if currentSize < puzzleSize, add this many EditTexts to the tables
        //if currentSize > puzzleSize, remove this many EditTexts from the tables
        if (currentSize < puzzleSize) {
            addEditTexts(root, puzzleSize - currentSize,false);
        } else if (currentSize > puzzleSize) {
            removeEditTexts(root, currentSize - puzzleSize);
        } else {
            //If same size selected, do nothing
        }
        currentSize = puzzleSize;
    }

    //Check if entry boxes are full
    private boolean isEntryBoxesFull(View root) {
        //Check that Board Language table is full
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idEnglishWords.get(i));
            String someString = someEntryBox.getText().toString();
            if (someString.equals("") || someString == null) {
                return false;
            }
        }

        //Check that Button Language table is full
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idFrenchWords.get(i));
            String someString = someEntryBox.getText().toString();
            if (someString.equals("") || someString == null) {
                return false;
            }
        }
        return true;
    }

    //Save our currentSize selected and any text that has been entered in an EditText
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(CC_WORDS_DEBUG_KEY, "onSaveInstanceState called");
        Log.d(CC_WORDS_DEBUG_KEY, "CurrentSize at the moment: " + currentSize);
        outState.putInt(KEY_SAVE_CURRENTSIZE, this.currentSize);
//        savePrefilledEditTexts(root, idButtonLanguageWords);
        outState.putStringArrayList(KEY_SAVE_BUTTON, savePrefilledEditTexts(root, idFrenchWords));

        outState.putStringArrayList(KEY_SAVE_BOARD, savePrefilledEditTexts(root, idEnglishWords));
    }

    //Helper method to save any filled EditTexts
    public ArrayList<String> savePrefilledEditTexts(View root, List<Integer> idLanguageWords) {
        ArrayList<String> savedWordsButton = new ArrayList<>();
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idLanguageWords.get(i));
            savedWordsButton.add(someEntryBox.getText().toString());
        }
        return savedWordsButton;

    }
}
