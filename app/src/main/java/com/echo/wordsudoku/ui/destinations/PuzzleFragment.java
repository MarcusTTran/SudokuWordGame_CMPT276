package com.echo.wordsudoku.ui.destinations;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.Memory.JsonReader;
import com.echo.wordsudoku.models.Memory.JsonWriter;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.ui.MainActivity;
import com.echo.wordsudoku.ui.SettingsViewModel;
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;
import com.echo.wordsudoku.ui.dialogs.RulesFragment;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.echo.wordsudoku.ui.puzzleParts.PuzzleBoardFragment;
import com.echo.wordsudoku.ui.destinations.PuzzleFragmentDirections.SubmitPuzzleAction;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleInputButtonsFragment;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

public class PuzzleFragment extends Fragment{


    // CONSTANTS
    private final int numberOfInitialWords = 80;
    private final int puzzleDimension = 9;
    // END CONSTANTS

    private static final String TAG = "PuzzleFragment";

    private int dictionaryPopupLimit = 0;


    //Used to hold English and French words to pass to DictionaryFragment
    String[] LanguageList1;
    String[] LanguageList2;


    private PuzzleViewModel mPuzzleViewModel;

    private SettingsViewModel mSettingsViewModel;

    private JsonWriter mJsonWriter = null;

    private JsonReader mJsonReader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mPuzzleViewModel = new ViewModelProvider(requireActivity()).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_puzzle, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean isNewGame = PuzzleFragmentArgs.fromBundle(getArguments()).getIsNewGame();
        if (isNewGame) {
            newGame();
        } else {
            loadGame();
        }

        LanguageList1 = new String[puzzleDimension];
        LanguageList2 = new String[puzzleDimension];
    }

    private void loadGame() {
        // Load the puzzle from the file and update the class members
        mJsonReader = new JsonReader(requireActivity());
        new Thread(() -> {
            // Load the puzzle from the file and update the class members
            Puzzle puzzle;
            try {
                puzzle = mJsonReader.readPuzzle();
                // Update the PuzzleViewModel
                if (puzzle != null) {
                    mPuzzleViewModel.setPuzzle(puzzle);
                    requireActivity().runOnUiThread(() -> {
                        PuzzleBoardFragment puzzleFragment = (PuzzleBoardFragment) getChildFragmentManager().findFragmentById(R.id.board);
                        PuzzleInputButtonsFragment puzzleInputButtonsFragment = (PuzzleInputButtonsFragment) getChildFragmentManager().findFragmentById(R.id.puzzle_input_buttons);
                        puzzleFragment.initBoardWithPuzzleModel();
                        puzzleInputButtonsFragment.initButtonsFromPuzzleModel();
                    });
                }
            } catch (IOException | JSONException e) {
                // run from main thread
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireActivity(), R.string.load_game_error, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
                });
            }
        }).start();
    }

    private void newGame() {
        List<WordPair> wordPairs;
        try {
            wordPairs = mPuzzleViewModel.getWordPairReader().getRandomWords(puzzleDimension);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Get the language setting from the SettingsViewModel and update respectively
        int puzzleLanguage = mSettingsViewModel.getPuzzleLanguage().getValue();

        // Create a new board
        Puzzle puzzle = new Puzzle(wordPairs,puzzleDimension,puzzleLanguage,numberOfInitialWords);
        mPuzzleViewModel.setPuzzle(puzzle);
        PuzzleBoardFragment puzzleFragment = (PuzzleBoardFragment) getChildFragmentManager().findFragmentById(R.id.board);
        PuzzleInputButtonsFragment puzzleInputButtonsFragment = (PuzzleInputButtonsFragment) getChildFragmentManager().findFragmentById(R.id.puzzle_input_buttons);
        // Initialize the board with the puzzle model
        puzzleFragment.initBoardWithPuzzleModel();

        // Initialize the input buttons with the puzzle model
        puzzleInputButtonsFragment.initButtonsFromPuzzleModel();

    }

    public void enterWordInBoard(String word) {
        PuzzleBoardFragment puzzleFragment = (PuzzleBoardFragment) getChildFragmentManager().findFragmentById(R.id.board);
        Dimension currentCell = puzzleFragment.getSelectedCell();
        Puzzle puzzle = mPuzzleViewModel.getPuzzle();
        if (!puzzle.isWritableCell(currentCell)) {
            Toast.makeText(requireActivity(), R.string.error_insert_in_initial_cell, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            WordPair associatedWordPair = null;
            List<WordPair> mWordPairs = mPuzzleViewModel.getPuzzle().getWordPairs();
            for (WordPair wordPair : mWordPairs) {
                if (wordPair.doesContain(word)) {
                    associatedWordPair = wordPair;
                    break;
                }
            }
            if (associatedWordPair != null) {
                puzzle.setCell(currentCell.getRows(), currentCell.getColumns(), associatedWordPair);
                puzzleFragment.insertWordInBoardView(word);
                return;
            } else {
                throw new RuntimeException("Trying to insert " + word + ". Word not found in word pairs");
            }
        }
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
        List<WordPair> wordPairs = mPuzzleViewModel.getPuzzle().getWordPairs();
        for (int i = 0; i < wordPairs.size(); i++) {
            LanguageList1[i] = wordPairs.get(i).getEnglish();
        }
        for (int i = 0; i < wordPairs.size(); i++) {
            LanguageList2[i] = wordPairs.get(i).getFrench();
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
        Puzzle puzzle = mPuzzleViewModel.getPuzzle();
        if (!puzzle.isPuzzleFilled()) {
            Toast.makeText(getActivity(), "You have not filled the puzzle!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: show a dialog to ask the user if he wants to finish the puzzle

        GameResult gameResult = puzzle.getGameResult();
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
                MainActivity activity = (MainActivity) requireActivity();
                activity.saveGame();
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
