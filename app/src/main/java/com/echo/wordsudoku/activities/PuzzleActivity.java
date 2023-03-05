package com.echo.wordsudoku.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.fragments.DictionaryFragment;
import com.echo.wordsudoku.fragments.RulesFragment;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.Memory.JsonReader;
import com.echo.wordsudoku.models.Memory.JsonWriter;
import com.echo.wordsudoku.models.sudoku.GameResult;
import com.echo.wordsudoku.models.sudoku.Puzzle;
import com.echo.wordsudoku.models.words.WordPair;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.views.SudokuBoard;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    // This is the key for the boolean extra in the intent
    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    private static final String TAG = "PuzzleActivity";

    private JsonWriter jsonWriter;

    private JsonReader jsonReader;

    private SudokuBoard mSudokuBoardView;

    // The word list for the spinner to choose from
    // This is just for testing purposes
    // TODO: Replace this with a list of words from the database

    // This member variable is used to store the input stream for the json file
    // If later on we want to use a remote database, we can just change this to a String and
    // use HttpHandler to get the json file from the database and store it in a String
    private InputStream jsonFile_Words;



    private List<WordPair> mWordPairs;
    private Puzzle mPuzzle;

    // This is used for accessing the shared preferences associated with this app
    private SharedPreferences mPreferences;

    private int dictionaryPopupLimit = 0;


    //Used to hold English and French words to pass to DictionaryFragment
    String[] LanguageList1;
    String[] LanguageList2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);


        // CONSTANTS
        final int numberOfInitialWords = 17;
        final int puzzleDimension = 9;
        // END CONSTANTS

        // Get the word pairs from the json file
        try {
            jsonFile_Words = getAssets().open("words.json");
            mWordPairs = getWords(jsonFile_Words,9);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // Get the shared preferences
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Get the puzzle language from the shared preferences
        int puzzleLanguage = mPreferences.getInt(getString(R.string.puzzle_language_key), BoardLanguage.ENGLISH);

        // Create a new board
        mPuzzle = new Puzzle(mWordPairs,puzzleDimension,puzzleLanguage,numberOfInitialWords);


        // read puzzle from json //TODO: DELETE FOR LATER USE (THIS IS ONLY FOR TESTING)
        // ONLY FOR TESTING
        try {

            jsonReader = new JsonReader(this);
            mPuzzle = jsonReader.readPuzzle();
            mWordPairs = mPuzzle.getWordPairs();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // ONLY FOR TESTING



        // We need to check if the user wants to load a previous game or start a new game
        // It is done by checking the boolean extra in the intent
        /*
            if(getIntent().getBooleanExtra(LOAD_PREVIOUS_GAME, false)){
                // A toast to make sure the activity is being created
                Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();
        */

        View[] buttons = {findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)};

        // Set button labels with the other language
        setButtonLabels(buttons, BoardLanguage.getOtherLanguage(puzzleLanguage));

        // For testing purposes only, to make sure the activity is being created
        //Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();


        // Get the SudokuBoardView reference from XML layout
        // this is the view that will display the Sudoku board
        mSudokuBoardView = findViewById(R.id.sudoku_board);
        // Setting the initial board to UI
        mSudokuBoardView.setBoard(mPuzzle.toStringArray());


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
    private boolean enterWord(String word) {
        String msg = "Word entered successfully!";
        boolean result = true;
        WordPair associatedWordPair = null;
        for (WordPair wordPair : mWordPairs) {
            if (wordPair.getEnglishOrFrench(BoardLanguage.ENGLISH).equals(word) || wordPair.getEnglishOrFrench(BoardLanguage.FRENCH).equals(word)) {
                associatedWordPair = wordPair;
                break;
            }
        }
        if (associatedWordPair != null) {
            try {
                mPuzzle.setCell(mSudokuBoardView.getCurrentCellRow() - 1, mSudokuBoardView.getCurrentCellColumn() - 1, associatedWordPair);
                if (!mSudokuBoardView.fillWord(word)) {
                    msg = "An error occurred! Make sure you have selected a cell";
                    result = false;
                }
            } catch (Exception e) {
                msg = "You can not write in the puzzle initial cells";
                result = false;
            } finally {
                Log.d(TAG, msg);
                return result;
            }
        }
        return false;
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


    // This method sets the labels of the buttons
    // @param buttons The array of buttons
    // @param language The language to be used for the button labels
    private void setButtonLabels(View[] buttons, int language)
    {
        for(int i = 0; i < mWordPairs.size(); i++)
        {
            ((Button)buttons[i]).setText(mWordPairs.get(i).getEnglishOrFrench(language));
        }
    }

    // This method is called when a button is pressed
    // It enters the word in the board
    // @param view The view that was pressed (the button)
    public void wordButtonPressed(View view) {
        String word = ((Button)view).getText().toString();
        enterWord(word);
    }

    //When the user presses the rules button
    public void rulesButtonPressed(View view) {
        //Toast.makeText(this, "Help Button pressed", Toast.LENGTH_LONG).show();
        //Create new instance of RulesFragment
        RulesFragment rulesFragment = new RulesFragment();
        rulesFragment.show(getSupportFragmentManager(), "RulesFragment");

    }

    //When the user presses the dictionary button
    public void dictionaryButtonPressed(View view) {
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
    public void finishButtonPressed(View view) {

        // for testing purposes
        if (!mPuzzle.isPuzzleFilled()) {
            //Toast.makeText(this, "You have not filled the puzzle!", Toast.LENGTH_LONG).show();

            try {
                jsonWriter = new JsonWriter(this);
                jsonWriter.open();
                jsonWriter.writePuzzle(mPuzzle);
                jsonWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
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
    // calls the WordPairReader
    private List<WordPair> getWords(InputStream is, int puzzleDimension) throws JSONException, IOException {
        WordPairReader reader = new WordPairReader(is,puzzleDimension);
        return reader.getWords();
    }

}