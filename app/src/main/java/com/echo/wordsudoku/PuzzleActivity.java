package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.wordsudoku.models.Board;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.WordPair;

import java.util.Arrays;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    // This is the key for the boolean extra in the intent
    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    private Spinner mWordListSpinner;
    private Button mEnterWordButton;
    private SudokuBoard mSudokuBoardView;

    // The word list for the spinner to choose from
    // This is just for testing purposes
    // TODO: Replace this with a list of words from the database
    private String[] mWordList = new String[9];

    private WordPair[] mWordPairs = {new WordPair("Apple","pomme"), new WordPair("Banana","banane"), new WordPair("Grape","raisin"), new WordPair("Orange","orange"), new WordPair("Peach","pêche"), new WordPair("Pear","poire"), new WordPair("Strawberry","fraise"), new WordPair("Watermelon","pastèque"), new WordPair("Coconut","noix de coco")};

    private Board mBoard;


    private int selected_word = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        int numberOfInitialWords = 17;
        int puzzleDimension = 9;

        mBoard = new Board(puzzleDimension,mWordPairs,BoardLanguage.ENGLISH,puzzleDimension*puzzleDimension - numberOfInitialWords);

        // We need to check if the user wants to load a previous game or start a new game
        // It is done by checking the boolean extra in the intent
        /*
        if(getIntent().getBooleanExtra(LOAD_PREVIOUS_GAME, false)){
            // A toast to make sure the activity is being created
            Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();
            */


        // For testing purposes only, to make sure the activity is being created
        // TODO: Remove this
        Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();


        // Get the SudokuBoardView reference from XML layout
        // this is the view that will display the Sudoku board
        mSudokuBoardView = findViewById(R.id.sudoku_board);
        // Setting the initial board to UI
        mSudokuBoardView.setBoard(mBoard.getUnSolvedBoard());




        // Get the Spinner reference from XML layout
        // And populate the spinner with the word list
        mWordListSpinner = findViewById(R.id.word_list_spinner);
        // fill the spinner words
        fillWordList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mWordList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWordListSpinner.setAdapter(adapter);

        // Set the listener for the spinner
        // When the user selects an item, the selected_word member variable will be set to the position of the item
        // This keeps track of which word the user has selected
        mWordListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the user selects an item, change the selection member variable to the position of the item
                selected_word = position;
            }

            // This is required for the interface
            // We don't need to do anything when the user selects nothing
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // When the user selects nothing, do nothing
            }
        });


        // Get the Enter button reference from XML layout
        mEnterWordButton = findViewById(R.id.enter_word_button);

        // Set the listener for the enter button
        // When the user clicks the button, we will call the fillWord method in the SudokuBoardView
        // with the selected word from the spinner
        mEnterWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the user clicks the enter button, get the selected word from the spinner
                // and display it in a toast
                String result = "";
                String selectedWord = mWordList[selected_word];
                try {
                    mBoard.insertWord(mSudokuBoardView.getCurrentCellRow() - 1, mSudokuBoardView.getCurrentCellColumn() - 1, selectedWord);
                    if (!mSudokuBoardView.fillWord(selectedWord)) {
                       result = "An error occurred! Make sure you have selected a cell";
                    }
                }
                catch (Exception e) {
                    result = "You can not write in the puzzle initial cells";
                }
                Toast.makeText(PuzzleActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

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
}