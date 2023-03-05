package com.echo.wordsudoku.ui.destinations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;
import com.echo.wordsudoku.ui.dialogs.RulesFragment;

import org.json.JSONException;

import java.util.List;

import com.echo.wordsudoku.ui.puzzleParts.PuzzleBoardFragment;
import com.echo.wordsudoku.ui.destinations.PuzzleFragmentDirections.SubmitPuzzleAction;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class PuzzleFragment extends Fragment{


    private static final String TAG = "PuzzleFragment";

    private List<WordPair> mWordPairs;
    private Puzzle mPuzzle;


    private int dictionaryPopupLimit = 0;


    //Used to hold English and French words to pass to DictionaryFragment
    String[] LanguageList1;
    String[] LanguageList2;

    private int mPuzzleLanguage;

    private PuzzleViewModel mPuzzleViewModel;

    private SettingsViewModel mSettingsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // CONSTANTS
        final int numberOfInitialWords = 80;
        final int puzzleDimension = 9;
        // END CONSTANTS

        mPuzzleViewModel = new ViewModelProvider(getActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);

        try {
            mWordPairs = mPuzzleViewModel.getWordPairReader().getValue().getRandomWords(puzzleDimension);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Get the language setting from the SettingsViewModel and update respectively
        mPuzzleLanguage = mSettingsViewModel.getPuzzleLanguage().getValue();

        // Create a new board
        mPuzzle = new Puzzle(mWordPairs,puzzleDimension,mPuzzleLanguage,numberOfInitialWords);

        mPuzzleViewModel.setWordPairs(mWordPairs);
        mPuzzleViewModel.setBoardLanguage(mPuzzleLanguage);
        mPuzzleViewModel.setPuzzle(mPuzzle);
        LanguageList1 = new String[mWordPairs.size()];
        LanguageList2 = new String[mWordPairs.size()];
        View root = inflater.inflate(R.layout.fragment_puzzle, container, false);
        return root;
    }

    public void enterWordInBoard(String word) {
        String msg = "Word entered successfully!";
        WordPair associatedWordPair = null;
        for (WordPair wordPair : mWordPairs) {
            if (wordPair.getEnglishOrFrench(BoardLanguage.ENGLISH).equals(word) || wordPair.getEnglishOrFrench(BoardLanguage.FRENCH).equals(word)) {
                associatedWordPair = wordPair;
                break;
            }
        }
        if (associatedWordPair != null) {
            PuzzleBoardFragment puzzleFragment = (PuzzleBoardFragment) getChildFragmentManager().findFragmentById(R.id.board);
            Dimension currentCell =  puzzleFragment.getSelectedCell();
            if (currentCell.getRows()==-2 || currentCell.getColumns()==-2) {
                msg = "You must select a cell first";
            }
            else {
                try {
                    mPuzzle.setCell(currentCell.getRows(), currentCell.getColumns(), associatedWordPair);
                    mPuzzleViewModel.setPuzzle(mPuzzle);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = "You can not write in the puzzle initial cells";
                }
            }
        }
        Log.d(TAG, msg);
    }

    //When the user presses the rules button
    public void rulesButtonPressed() {
        //Toast.makeText(this, "Help Button pressed", Toast.LENGTH_LONG).show();
        //Create new instance of RulesFragment
        RulesFragment rulesFragment = new RulesFragment();
        rulesFragment.show(getActivity().getSupportFragmentManager(), "RulesFragment");
    }

    public void dictionaryButtonPressed() {
        //Toast.makeText(this, "Dictionary Button pressed", Toast.LENGTH_LONG).show();

        for (int i = 0; i < mWordPairs.size(); i++) {
            LanguageList1[i] = mWordPairs.get(i).getEnglish();
        }
        for (int i = 0; i < mWordPairs.size(); i++) {
            LanguageList2[i] = mWordPairs.get(i).getFrench();
        }

        //Create new instance of RulesFragment
        DictionaryFragment dictionaryFragment = DictionaryFragment.newInstance(LanguageList1, LanguageList2,dictionaryPopupLimit);
        dictionaryFragment.show(getActivity().getSupportFragmentManager(), "DictionaryFragment");

        //Increase the dictionary pop up limit (limit is twice per game)
        dictionaryPopupLimit++;

    }

    // This method is called when the finish button is pressed
    // It checks if the user has filled the board
    // If the user has filled the board, it checks if the user has solved the puzzle
    // @param view The view that was pressed (the button)
    // TODO: Add a dialog to ask the user if he wants to finish the puzzle
    public void finishButtonPressed() {
        if (!mPuzzle.isPuzzleFilled()) {
            Toast.makeText(getActivity(), "You have not filled the puzzle!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: show a dialog to ask the user if he wants to finish the puzzle

        GameResult gameResult = mPuzzle.getGameResult();
        SubmitPuzzleAction action = PuzzleFragmentDirections.submitPuzzleAction();
        action.setIsWin(gameResult.getResult());
        action.setMistakes(gameResult.getMistakes());
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.puzzle_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

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
                //saveGame();
                Toast.makeText(getActivity(), "Save Game Button pressed", Toast.LENGTH_LONG).show();
                return true;
            case R.id.options_main_menu_button:
                //discardGame();
                Navigation.findNavController(getView()).navigate(R.id.quitPuzzleToMainMenuAction);
                return true;
            case R.id.options_exit_button:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
