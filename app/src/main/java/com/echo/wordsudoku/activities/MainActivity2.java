package com.echo.wordsudoku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.ui.puzzle.PuzzleActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String LOAD_MAIN_MENU = "com.echo.wordsudoku.load_main_menu";

    // UI references.
    private Button mNewGameButton;
    private Button mLoadGameButton;
    private Button mExitButton;

    private Button mChangeLanguageButton;

    private int mPuzzleLanguage = BoardLanguage.ENGLISH;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        // A toast to make sure the activity is being created
        Toast.makeText(this, "Main Menu Activity", Toast.LENGTH_SHORT).show();

        // [NEW GAME BUTTON]
        // Set up the new game button
        // When the button is clicked, start the puzzle activity and pass in a boolean (false) to indicate that a new game should be started
        // the boolean is passed in as an extra in the intent (see PuzzleActivity.java)
        // The boolean is used in the PuzzleActivity.java to determine whether to load a previous game or start a new game

        mNewGameButton = findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "New Game Button Clicked", Toast.LENGTH_SHORT).show();

                // Start the puzzle activity
                Intent intent = PuzzleActivity.newIntent(MainActivity2.this, false);
                startActivity(intent);
            }
        });


        // [LOAD GAME BUTTON]
        // Set up the load game button
        // When the button is clicked, start the puzzle activity and pass in a boolean (true) to indicate that a previous game should be loaded
        // the boolean is passed in as an extra in the intent (see PuzzleActivity.java)
        // The boolean is used in the PuzzleActivity.java to determine whether to load a previous game or start a new game

        mLoadGameButton = findViewById(R.id.load_game_button);
        mLoadGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Load Game Button Clicked", Toast.LENGTH_SHORT).show();

                // Start the puzzle activity
                Intent intent = PuzzleActivity.newIntent(MainActivity2.this, true);
                startActivity(intent);
            }
        });


        // [EXIT BUTTON]
        // Set up the exit button
        // When the button is clicked, finish the activity

        mExitButton = findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Exit Button Clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        mChangeLanguageButton = findViewById(R.id.change_language_button);
        String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mPuzzleLanguage);
        mChangeLanguageButton.setText(changeLanguageButtonText);
        mChangeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPuzzleLanguage = BoardLanguage.getOtherLanguage(mPuzzleLanguage);
                    preferencesEditor.putInt(getString(R.string.puzzle_language_key), mPuzzleLanguage);
                    preferencesEditor.apply();
                    String changeLanguageButtonText = "Puzzle Language : " + BoardLanguage.getLanguageName(mPuzzleLanguage);
                    mChangeLanguageButton.setText(changeLanguageButtonText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity2.class);
        // This flag is used to clear the activity stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

}