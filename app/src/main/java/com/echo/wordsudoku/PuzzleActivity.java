package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.wordsudoku.models.Board;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.CSVReader;
import com.echo.wordsudoku.models.WordPair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {

    // This is the key for the boolean extra in the intent
    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    private static final String TAG = "PuzzleActivity";

    private Spinner mWordListSpinner;
    private Button mEnterWordButton;
    private SudokuBoard mSudokuBoardView;

    // The word list for the spinner to choose from
    // This is just for testing purposes
    // TODO: Replace this with a list of words from the database
    private String[] mWordList = new String[9];

//    private WordPair[] mWordPairs = {new WordPair("Apple","pomme"),
//            new WordPair("Banana","banane"),
//            new WordPair("Grape","raisin"),
//            new WordPair("Orange","orange"),
//            new WordPair("Peach","pêche"),
//            new WordPair("Pear","poire"),
//            new WordPair("Strawberry","fraise"),
//            new WordPair("Watermelon","pastèque"),
//            new WordPair("Coconut","coco")};

    private WordPair[] mWordPairs;

    private Board mBoard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        int numberOfInitialWords = 17;
        int puzzleDimension = 9;

        mWordPairs = getWords(puzzleDimension);


        mBoard = new Board(puzzleDimension,mWordPairs,BoardLanguage.ENGLISH,puzzleDimension*puzzleDimension - numberOfInitialWords);

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
        fillWordList();
        setButtonLabels(buttons);

        // For testing purposes only, to make sure the activity is being created
        // TODO: Remove this
        Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();


        // Get the SudokuBoardView reference from XML layout
        // this is the view that will display the Sudoku board
        mSudokuBoardView = findViewById(R.id.sudoku_board);
        // Setting the initial board to UI
        mSudokuBoardView.setBoard(mBoard.getUnSolvedBoard());

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
        try {
            mBoard.insertWord(mSudokuBoardView.getCurrentCellRow() - 1, mSudokuBoardView.getCurrentCellColumn() - 1, word);
            if (!mSudokuBoardView.fillWord(word)) {
                msg = "An error occurred! Make sure you have selected a cell";
                result = false;
            }
        }
        catch (Exception e) {
            msg = "You can not write in the puzzle initial cells";
            result = false;
        }
        finally {
            Log.d(TAG, msg);
            return result;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Puzzle Activity Started (Load previous game!)", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Puzzle Activity Stopped (Save game progress now!)", Toast.LENGTH_LONG).show();
    }

    public static Intent newIntent (Context packageContext, boolean loadPreviousGame) {
        Intent intent = new Intent(packageContext, PuzzleActivity.class);
        intent.putExtra(LOAD_PREVIOUS_GAME, loadPreviousGame);
        return intent;
    }

    private void fillWordList()
    {
        for(int i = 0; i < mWordPairs.length; i++)
        {
            mWordList[i] = mWordPairs[i].getEnglishOrFrench(BoardLanguage.FRENCH);
        }
    }

    private void setButtonLabels(View[] buttons)
    {
        for(int i = 0; i < mWordPairs.length; i++)
        {
            ((Button)buttons[i]).setText(mWordPairs[i].getEnglishOrFrench(BoardLanguage.FRENCH));
        }
    }



    public void wordButtonPressed(View view) {
        enterWord(((Button)view).getText().toString());
    }


    // gets a list of word pairs based on the number of dimension given
    // calls the csv reader
    private WordPair[] getWords(int puzzleDimension) {
        InputStream is = getResources().openRawResource(R.raw.dictionary);
        CSVReader csvReader = new CSVReader(is, puzzleDimension);
        csvReader.collectWords();
        return csvReader.getWords();
    }



}