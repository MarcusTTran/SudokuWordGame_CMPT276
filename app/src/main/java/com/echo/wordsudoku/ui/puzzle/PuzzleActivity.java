package com.echo.wordsudoku.ui.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.activities.ResultActivity;
import com.echo.wordsudoku.models.dimension.Dimension;
import com.echo.wordsudoku.ui.dialogs.DictionaryFragment;
import com.echo.wordsudoku.ui.dialogs.RulesFragment;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.views.SudokuBoard;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    // This is the key for the boolean extra in the intent
    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    private static final String TAG = "PuzzleActivity";

    private SudokuBoard mSudokuBoardView;

    // The word list for the spinner to choose from
    // This is just for testing purposes
    // TODO: Replace this with a list of words from the database

    // This member variable is used to store the input stream for the json file
    // If later on we want to use a remote database, we can just change this to a String and
    // use HttpHandler to get the json file from the database and store it in a String
    private InputStream jsonFile;


    private List<WordPair> mWordPairs;
    private Puzzle mPuzzle;

    // This is used for accessing the shared preferences associated with this app
    private SharedPreferences mPreferences;

    private int dictionaryPopupLimit = 0;


    //Used to hold English and French words to pass to DictionaryFragment
    String[] LanguageList1;
    String[] LanguageList2;

    private int mPuzzleLanguage;

    private PuzzleViewModel mPuzzleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);


        // CONSTANTS
        final int numberOfInitialWords = 17;
        final int puzzleDimension = 9;
        // END CONSTANTS

        try {
            jsonFile = getAssets().open("words.json");
            mWordPairs = getWords(jsonFile,9);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // Get the shared preferences
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // Get the puzzle language from the shared preferences
        mPuzzleLanguage = mPreferences.getInt(getString(R.string.puzzle_language_key), BoardLanguage.ENGLISH);

        // Create a new board
        mPuzzle = new Puzzle(mWordPairs,puzzleDimension,mPuzzleLanguage,numberOfInitialWords);

        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);
        mPuzzleViewModel.setWordPairs(mWordPairs);
        mPuzzleViewModel.setBoardLanguage(mPuzzleLanguage);
        mPuzzleViewModel.setPuzzle(mPuzzle);

        // We need to check if the user wants to load a previous game or start a new game
        // It is done by checking the boolean extra in the intent
        /*
        if(getIntent().getBooleanExtra(LOAD_PREVIOUS_GAME, false)){
            // A toast to make sure the activity is being created
            Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();
            */

        // For testing purposes only, to make sure the activity is being created
//        Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();




        //Used to hold English and French word that we pass to DictionaryFragment
        LanguageList1 = new String[mWordPairs.size()];
        LanguageList2 = new String[mWordPairs.size()];

    }


    // This method enters a word in the board
    // It returns true if the word was entered successfully
    // It returns false if the word was not entered successfully
    // @param word The word to be entered
    // @return true if the word was entered successfully, false otherwise
    // msg is the message to be displayed in the Logcat
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
            Dimension currentCell =  ((PuzzleBoardFragment) (getSupportFragmentManager().findFragmentById(R.id.board))).getSelectedCell();
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




    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "Puzzle Activity Started (Load previous game!)", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(this, "Puzzle Activity Stopped (Save game progress now!)", Toast.LENGTH_LONG).show();
    }

    public static Intent newIntent (Context packageContext, boolean loadPreviousGame) {
        Intent intent = new Intent(packageContext, PuzzleActivity.class);
        intent.putExtra(LOAD_PREVIOUS_GAME, loadPreviousGame);
        // Makes this current activity the root of the back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    //When the user presses the rules button
    public void rulesButtonPressed() {
        //Toast.makeText(this, "Help Button pressed", Toast.LENGTH_LONG).show();
        //Create new instance of RulesFragment
        RulesFragment rulesFragment = new RulesFragment();
        rulesFragment.show(getSupportFragmentManager(), "RulesFragment");
    }

    //When the user presses the dictionary button
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
        dictionaryFragment.show(getSupportFragmentManager(), "DictionaryFragment");

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
            Toast.makeText(this, "You have not filled the puzzle!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: show a dialog to ask the user if he wants to finish the puzzle

        GameResult gameResult = new GameResult();
        // Intent to start the ResultActivity
        Intent intent;
        if(mPuzzle.isPuzzleSolved())
        {
            // Default constructor of GameResult sets the result to true so nothing to do here
        }
        else
        {
            gameResult.setResult(false);
            gameResult.setMistakes(mPuzzle.getMistakes());
        }
        intent = ResultActivity.newIntent(PuzzleActivity.this,gameResult);
        startActivity(intent);
    }

    // gets a list of word pairs based on the number of dimension given
    // calls the csv reader
    private List<WordPair> getWords(InputStream is, int puzzleDimension) throws JSONException, IOException {
        WordPairReader reader = new WordPairReader(is,puzzleDimension);
        return reader.getWords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.puzzle_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish_button:
                finishButtonPressed();
                return true;
            case R.id.rules_button:
                rulesButtonPressed();
                return true;
            case R.id.dictionary_help_button:
                dictionaryButtonPressed();
                return true;
            case R.id.save_game_button:
                //saveGame();
                Toast.makeText(this, "Save Game Button pressed", Toast.LENGTH_LONG).show();
                return true;
            case R.id.discard_game_button:
                //discardGame();
                Toast.makeText(this, "Discard Game Button pressed", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}