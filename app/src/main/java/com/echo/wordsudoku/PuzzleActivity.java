package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PuzzleActivity extends AppCompatActivity {

    private static final String LOAD_PREVIOUS_GAME = "com.echo.wordsudoku.load_previous_game";

    // Action text view shows whether a new game is being started or a previous game is being loaded
    // This is just for testing purposes
    private TextView mActionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Toast.makeText(this, "Puzzle Activity being Created (Load previous game!)", Toast.LENGTH_LONG).show();

        // get the ui reference
        mActionTextView = findViewById(R.id.action_text_view);

        // set the text based on the boolean passed in as an extra in the intent
        if (getIntent().getBooleanExtra(LOAD_PREVIOUS_GAME, false)) {
            mActionTextView.setText("Loading previous game...");
        } else {
            mActionTextView.setText("Starting new game...");
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
}