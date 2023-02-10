package com.echo.wordsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.echo.wordsudoku.models.Board;
import com.echo.wordsudoku.models.WordPair;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Toast.makeText(this, "Main Menu Activity", Toast.LENGTH_SHORT).show();


/*      ======== FOR TESTING THE BOARD CLASS! ========
         int K = 20, N = 9;
        WordPair[] wp= new WordPair[10];

        for (int i = 1; i <= N; i++) {
            wp[i-1] = new WordPair(("eng_" + i), ("fre_" + i), i);
            System.out.println(wp[i-1].getEnglish() +
            " = "+ wp[i-1].getFrench()+ ", " + wp[i-1].getId());
        }

        Board board = new Board(N, wp, "English", K);

*/



    }



}