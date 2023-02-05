package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Toast.makeText(this, "Puzzle Activity being Created (Check the memory and if there was a game in progress load it!)", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Puzzle Activity Started (Check the memory and if there was a game in progress load it!)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Puzzle Activity Stopped (Save game progress now!)", Toast.LENGTH_SHORT).show();
    }
}