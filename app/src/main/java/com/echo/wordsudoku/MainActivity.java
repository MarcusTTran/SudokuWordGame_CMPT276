package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    // UI references.
    private Button mNewGameButton;
    private Button mLoadGameButton;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(MainActivity.this, "New Game Button Clicked", Toast.LENGTH_SHORT).show();

                // Start the puzzle activity
                Intent intent = PuzzleActivity.newIntent(MainActivity.this, false);
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
                Toast.makeText(MainActivity.this, "Load Game Button Clicked", Toast.LENGTH_SHORT).show();

                // Start the puzzle activity
                Intent intent = PuzzleActivity.newIntent(MainActivity.this, true);
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
                Toast.makeText(MainActivity.this, "Exit Button Clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}