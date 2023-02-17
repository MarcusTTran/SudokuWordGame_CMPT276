package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.echo.wordsudoku.models.GameResult;
import com.echo.wordsudoku.models.WordPair;

import java.util.Arrays;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    // This is the key for the boolean extra in the intent
    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    private static final String TAG = "PuzzleActivity";

    private SudokuBoard mSudokuBoardView;

    // The word list for the spinner to choose from
    // This is just for testing purposes
    // TODO: Replace this with a list of words from the database
    private String[] mWordList = new String[9];

    private WordPair[] mWordPairs = {new WordPair("Apple","pomme"), new WordPair("Banana","banane"), new WordPair("Grape","raisin"), new WordPair("Orange","orange"), new WordPair("Peach","pêche"), new WordPair("Pear","poire"), new WordPair("Strawberry","fraise"), new WordPair("Watermelon","pastèque"), new WordPair("Coconut","coco")};

    private Board mBoard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);


        // CONSTANTS

        final int numberOfInitialWords = 80;
        final int puzzleDimension = 9;

        // END CONSTANTS

        mBoard = new Board(puzzleDimension,mWordPairs,BoardLanguage.ENGLISH,puzzleDimension*puzzleDimension - numberOfInitialWords);

        // We need to check if the user wants to load a previous game or start a new game
        // It is done by checking the boolean extra in the intent
        /*
        if(getIntent().getBooleanExtra(LOAD_PREVIOUS_GAME, false)){
            // A toast to make sure the activity is being created
            Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();
            */
        View[] buttons = {findViewById(R.id.button1),findViewById(R.id.button2),findViewById(R.id.button3),findViewById(R.id.button4),findViewById(R.id.button5),findViewById(R.id.button6),findViewById(R.id.button7),findViewById(R.id.button8),findViewById(R.id.button9)};
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
        // Makes this current activity the root of the back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    // This method fills the word list
    // It is used to label the buttons
    private void fillWordList()
    {
        for(int i = 0; i < mWordPairs.length; i++)
        {
            mWordList[i] = mWordPairs[i].getEnglishOrFrench(BoardLanguage.FRENCH);
        }
    }

    // This method sets the labels of the buttons
    // @param buttons The array of buttons
    private void setButtonLabels(View[] buttons)
    {
        for(int i = 0; i < mWordPairs.length; i++)
        {
            ((Button)buttons[i]).setText(mWordPairs[i].getEnglishOrFrench(BoardLanguage.FRENCH));
        }
    }

    // This method is called when a button is pressed
    // It enters the word in the board
    // @param view The view that was pressed (the button)
    public void wordButtonPressed(View view) {
        enterWord(((Button)view).getText().toString());
    }

    // This method is called when the finish button is pressed
    // It checks if the user has filled the board
    // If the user has filled the board, it checks if the user has solved the puzzle
    // @param view The view that was pressed (the button)
    // TODO: Add a dialog to ask the user if he wants to finish the puzzle
    public void finishButtonPressed(View view) {
        if (!mBoard.hasUserFilled()) {
            Toast.makeText(this, "You have not filled the puzzle!", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO: show a dialog to ask the user if he wants to finish the puzzle

        GameResult gameResult = new GameResult();
        // Intent to start the ResultActivity
        Intent intent;
        if(mBoard.checkWin())
        {
            // Default constructor of GameResult sets the result to true so nothing to do here
        }
        else
        {
            gameResult.setResult(false);
            gameResult.setMistakes(mBoard.getMistakes());
        }
        intent = ResultActivity.newIntent(PuzzleActivity.this,gameResult);
        startActivity(intent);
    }

}