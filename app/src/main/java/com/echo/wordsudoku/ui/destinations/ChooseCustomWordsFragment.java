package com.echo.wordsudoku.ui.destinations;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.ChooseCustomWordsDialog;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseCustomWordsFragment extends Fragment{

    private PuzzleViewModel mPuzzleViewModel;

    private SettingsViewModel mSettingsViewModel;

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

    //Store our words from the view model
    private List<WordPair> CustomWordPairsFromViewModel;

    //Check if Stored Words exist
    private boolean isStoredCustomWords = false;

    //Track index of CustomWordPair list as we call the addEditText method appropriate number of times
    private int loadedEntryBox1Counter = 0;
    private int loadedEntryBox2Counter = 0;


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

        //Initialize list to store any saved words if they exist
        CustomWordPairsFromViewModel = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(CC_WORDS_DEBUG_KEY,"onCreateView called");

        View root = inflater.inflate(R.layout.fragment_choose_custom_words, container, false);

        this.root = root;

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        int[] dropDownIndexToSize = {4,6,9,12};


        //Create our dropdown and fill each selection with values from resource file
        Spinner dropdown = root.findViewById(R.id.puzzleSizeCustomDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.puzzle_sizes_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        int defaultPos = -1;
        //If custom word pairs previously exist
        if (mPuzzleViewModel.getCustomWordPairs() != null) {
            Log.d(CC_WORDS_DEBUG_KEY, "CUSTOM WORD PAIR WAS NOT NULL");
            //Get the custom word pairs
            List<WordPair> wordPairs = mPuzzleViewModel.getCustomWordPairs();
            Log.d(CC_WORDS_DEBUG_KEY, "CustomWordPair size is: " + wordPairs.size());
            //Get the size of the custom word pair list to choose which dropdown should be preselected
            for (int i = 0; i < dropDownIndexToSize.length; i++) {
                if (dropDownIndexToSize[i] == wordPairs.size()) {
                    defaultPos = i;
                    Log.d(CC_WORDS_DEBUG_KEY, "default selection pos is: " + defaultPos);
                }
            }
            dropdown.setSelection(defaultPos);
            this.CustomWordPairsFromViewModel = wordPairs;
            isStoredCustomWords = true;
        } else {
            Log.d(CC_WORDS_DEBUG_KEY, "CUSTOM WORD PAIR WAS NULL");
        }




        Log.d(CC_WORDS_DEBUG_KEY, "Prior to initializeEntryBoxes, currentSize is: " + this.currentSize);

        //Create the EditTexts
        addEditTexts(root, this.currentSize,true);


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

                        //Add in each word to WordPair list
                        userEnteredWordList.add(new WordPair(someEntryBox1.getText().toString(), someEntryBox2.getText().toString()));
                    }

                    //Set the PuzzleViewModel to store the WordPair list we made
                    mPuzzleViewModel.setCustomWordPairs(userEnteredWordList);
                    try {
                        mPuzzleViewModel.newCustomPuzzle(mSettingsViewModel.getPuzzleLanguage().getValue(), mSettingsViewModel.getDifficulty());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Navigation.findNavController(root).navigate(R.id.startCustomPuzzleAction);
                    //Toast.makeText(getContext(), "Words for Custom Puzzle have been successfully set.", Toast.LENGTH_LONG).show();

                } else {
                    ChooseCustomWordsDialog errorFragment = ChooseCustomWordsDialog.newInstance(getString(R.string.error_custom_words_msg), getString(R.string.error));
                    errorFragment.show(getActivity().getSupportFragmentManager(), "Error Dialog");
                }
            }
        });


        Button clearButton = root.findViewById(R.id.buttonClearCustomWords);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < currentSize; i++) {
                    //Entry Boxes under Board Language are first argument in WordPair
                    EditText someEntryBox1 = root.findViewById(idEnglishWords.get(i));
                    //Entry Boxes under Button Language are second argument in WordPair
                    EditText someEntryBox2 = root.findViewById(idFrenchWords.get(i));

                    //Make every EditText blank;
                    someEntryBox1.setText("");
                    someEntryBox2.setText("");
                }
                //Clear the CustomWordPair list
                mPuzzleViewModel.setCustomWordPairs(null);
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

            //If stored custom words exist and we have still have unloaded words, fill them in
            if (isStoredCustomWords && loadedEntryBox1Counter < CustomWordPairsFromViewModel.size()) {
//                Log.d(CC_WORDS_DEBUG_KEY, "loadedWordsEntryBox1: " + loadedEntryBox1Counter);
                someEntryBox.setText(CustomWordPairsFromViewModel.get(loadedEntryBox1Counter).getEnglish());
                loadedEntryBox1Counter++;
            } else if (!isInitialize) {
                someEntryBox.setText("");
            } else {
                someEntryBox.setText(prefilledEnglishWords.get(i));
            }

            someEntryBox.setEms(6);
            someEntryBox.setTextSize(13);
            someEntryBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});
            someEntryBox.setId(View.generateViewId());
            //Disable the keyboard from popping up when EditText is clicked (landscape mode)
            someEntryBox.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            idEnglishWords.add(someEntryBox.getId());
            entryBoxHolder1.addView(someEntryBox);
        }

        //Add EditTexts to Board Language table
        LinearLayout entryBoxHolder2 = root.findViewById(R.id.buttonLanguageEntries);

        for (int i = 0; i < numberToAdd; i++) {
            EditText someEntryBox = new EditText(entryBoxHolder2.getContext());

            //If stored custom words exist and we have still have unloaded words, fill them in
            if (isStoredCustomWords && loadedEntryBox2Counter < CustomWordPairsFromViewModel.size()) {
//                Log.d(CC_WORDS_DEBUG_KEY, "loadedWordsEntryBox2: " + loadedEntryBox2Counter);
                someEntryBox.setText(CustomWordPairsFromViewModel.get(loadedEntryBox2Counter).getFrench());
                loadedEntryBox2Counter++;
            } else if (!isInitialize) {
                someEntryBox.setText("");
            } else {
                someEntryBox.setText(prefilledFrenchWords.get(i));
            }

            someEntryBox.setEms(6);
            someEntryBox.setTextSize(13);
            someEntryBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});
            someEntryBox.setId(View.generateViewId());
            //Disable the keyboard from popping up when EditText is clicked (landscape mode)
            someEntryBox.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
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

            //Check that the entered word contains no non-characters
            char[] charactersInString = someString.toCharArray();
            for (char someCharacter : charactersInString) {
                if(!Character.isLetter(someCharacter)) {
                    return false;
                }
            }

            if (someString.equals("") || someString == null) {
                return false;
            }
        }



        //Check that Button Language table is full
        for (int i = 0; i < currentSize; i++) {
            EditText someEntryBox = root.findViewById(idFrenchWords.get(i));
            String someString = someEntryBox.getText().toString();

            //Check that the entered word contains no non-characters
            char[] charactersInString = someString.toCharArray();
            for (char someCharacter : charactersInString) {
                if(!Character.isLetter(someCharacter)) {
                    return false;
                }
            }

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
