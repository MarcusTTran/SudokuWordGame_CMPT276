package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.echo.wordsudoku.models.GameResult;

public class ResultActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private ImageView mResultImageView;


    private GameResult mGameResult;

    private final static String GAME_RESULT = "com.echo.wordsudoku.game_result";
    private final static String GAME_RESULT_MISTAKES = "com.echo.wordsudoku.game_result_mistakes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String message;
        int imageResource;
        getGameResult();
        if(mGameResult.getResult() == true) {
            message = "You won!";
            imageResource = R.drawable.success;
        }
        else {
            message = "You lost! You made " + mGameResult.getMistakes() + " mistakes.";
            imageResource = R.drawable.fail;
        }

        mResultTextView = findViewById(R.id.result_text_view);
        mResultImageView = findViewById(R.id.result_image_view);

        mResultTextView.setText(message);
        mResultImageView.setImageResource(imageResource);
    }

    public static Intent newIntent(Context packageContext, GameResult gameResult) {
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putExtra(GAME_RESULT,gameResult.getResult());
        if(gameResult.getResult() == false)
            intent.putExtra(GAME_RESULT_MISTAKES,gameResult.getMistakes());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        return intent;
    }

    private void getGameResult() {
        boolean game_result = getIntent().getBooleanExtra(GAME_RESULT,false);
        int mistakes = getIntent().getIntExtra(GAME_RESULT_MISTAKES,0);
        mGameResult = new GameResult(game_result,mistakes);
    }


    // When the user presses the back button, go back to the main menu
    // instead of going back to the puzzle activity with previous game
    public void onBackPressed() {
        Intent intent = MainActivity.newIntent(ResultActivity.this);
        startActivity(intent);
    }

    // When the user presses the main menu button, go back to the main menu
    // instead of going back to the puzzle activity with previous game (this fires when the user clicks the button on the result activity)
    public void mainMenuButtonPressed(View view) {
        Intent intent = MainActivity.newIntent(ResultActivity.this);
        startActivity(intent);
    }

    public void newGameButtonPressed(View view) {
        Intent intent = PuzzleActivity.newIntent(ResultActivity.this,false);
        startActivity(intent);
    }
}