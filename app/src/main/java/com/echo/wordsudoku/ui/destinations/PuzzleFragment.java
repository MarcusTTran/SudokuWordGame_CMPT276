/*
* Description: This class is the fragment that contains the puzzle board and the puzzle buttons
* It is the main fragment that the user interacts with when playing the game
* It contains the puzzle board and the puzzle buttons
* It contains the logic for the puzzle buttons
* It contains the logic for the puzzle board
* It contains the logic for the puzzle timer
* */

package com.echo.wordsudoku.ui.destinations;

import static com.echo.wordsudoku.models.language.BoardLanguage.ENGLISH;
import static com.echo.wordsudoku.models.language.BoardLanguage.defaultLanguage;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.exceptions.IllegalDimensionException;
import com.echo.wordsudoku.exceptions.IllegalLanguageException;
import com.echo.wordsudoku.exceptions.IllegalWordPairException;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.sudoku.Cell;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.MainActivity;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;
import com.echo.wordsudoku.ui.dialogs.RulesFragment;

import java.util.List;
import java.util.Locale;

import com.echo.wordsudoku.ui.dialogs.SaveGameDialog;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleBoardFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class PuzzleFragment extends Fragment {

    private int dictionaryPopupLimit = 0;

    private PuzzleViewModel mPuzzleViewModel;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        settingsViewModel= new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_puzzle, container, false);
        return root;
    }

    //@method
    //This method resets the puzzle to its initial state
    public void resetGame() {
        mPuzzleViewModel.resetPuzzle(false);
    }

    //@method
    //Adds a word to the puzzle object
    //args: word - the word to be added
    //It automatically adds the word in the selected cell which we get from the puzzleViewFragment
    public void enterWordInBoard(String word) throws IllegalWordPairException, IllegalDimensionException {
        PuzzleBoardFragment puzzleViewFragment = (PuzzleBoardFragment) getChildFragmentManager().findFragmentById(R.id.board);
        Dimension currentCell = puzzleViewFragment.getSelectedCell();
        if(currentCell.getColumns() == -2 || currentCell.getRows() == -2) {
            Toast.makeText(requireActivity(), R.string.error_no_cell_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mPuzzleViewModel.isCellWritable(currentCell)) {
            Toast.makeText(requireActivity(), R.string.error_insert_in_initial_cell, Toast.LENGTH_SHORT).show();
        } else {
            mPuzzleViewModel.insertWord(currentCell, word);
        }
    }

    //When the user presses the rules button
    public void rulesButtonPressed() {
        //Create new instance of RulesFragment
        RulesFragment rulesFragment = new RulesFragment();
        rulesFragment.show(getActivity().getSupportFragmentManager(), RulesFragment.TAG);
    }

    //When the user presses the dictionary button to see a table of all the words in the puzzle and their translations
    public void dictionaryButtonPressed() {
        //Toast.makeText(this, "Dictionary Button pressed", Toast.LENGTH_LONG).show();
        List<WordPair> wordPairs = mPuzzleViewModel.getWordPairs();
        int size = wordPairs.size();
        String[] LanguageList1 = new String[size],LanguageList2 = new String[size];
        for (int i = 0; i < size; i++) {
            LanguageList1[i] = wordPairs.get(i).getEnglish();
        }
        for (int i = 0; i < size; i++) {
            LanguageList2[i] = wordPairs.get(i).getFrench();
        }

        //Create new instance of RulesFragment
        DictionaryFragment dictionaryFragment = DictionaryFragment.newInstance(LanguageList1, LanguageList2, dictionaryPopupLimit,settingsViewModel.getDifficulty());
        dictionaryFragment.show(getActivity().getSupportFragmentManager(), DictionaryFragment.TAG);
        //Increase the dictionary pop up limit (limit is twice per game)
        dictionaryPopupLimit++;

    }

    // This method is called when the finish button is pressed
    // It checks if the user has filled the board
    // If the user has filled the board, it checks if the user has solved the puzzle
    // @param view The view that was pressed (the button)
    // TODO: Add a dialog to ask the user if he wants to finish the puzzle
    public void finishButtonPressed() {
        if (!mPuzzleViewModel.isPuzzleComplete()) {
            Toast.makeText(getActivity(), getString(R.string.error_not_filled_puzzle), Toast.LENGTH_LONG).show();
            return;
        }
//        toSpeech.shutdown(); // Turn off the text to speech engine
        Navigation.findNavController(getView()).navigate(R.id.submitPuzzleAction);
    }

    // Sandwich menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.puzzle_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // When the user presses a button in the sandwich menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_finish_button:
                finishButtonPressed();
                return true;
            case R.id.options_rules_button:
                rulesButtonPressed();
                return true;
            case R.id.options_dictionary_help_button:
                dictionaryButtonPressed();
                return true;
            case R.id.options_save_game_button:
                MainActivity activity = (MainActivity) requireActivity();
                activity.savePuzzle();
                return true;
            case R.id.options_main_menu_button:
                //discardGame();
                if(!((MainActivity)requireActivity()).isGameSaved())
                    new SaveGameDialog().show(getChildFragmentManager(), SaveGameDialog.TAG);
                else
                    ((MainActivity)requireActivity()).mainMenu();
                return true;
            case R.id.options_exit_button:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // This method is called when the user presses the back button
    // It checks if the user has saved the game
    // If the user has not saved the game, it asks the user if he wants to save the game
    @Override
    public void onResume() {
        super.onResume();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(!((MainActivity)requireActivity()).isGameSaved()) {
                    new SaveGameDialog().show(getChildFragmentManager(), SaveGameDialog.TAG);
                } else {
                    ((MainActivity)requireActivity()).mainMenu();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

}
