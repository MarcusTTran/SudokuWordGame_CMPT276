package com.echo.wordsudoku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private InputStream jsonFile;

    private PuzzleViewModel mPuzzleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);

        try {
            jsonFile = getAssets().open("words.json");
            mPuzzleViewModel.setWordPairReader(new WordPairReader(jsonFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.puzzleResultFragment,R.id.mainMenuFragment,R.id.puzzleFragment).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        // This flag is used to clear the activity stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

}