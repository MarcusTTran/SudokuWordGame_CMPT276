package com.echo.wordsudoku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private InputStream jsonFile;


    private PuzzleViewModel mPuzzleViewModel;

    // This is used for accessing the shared preferences associated with this app
    private SharedPreferences mPreferences;

    private SettingsViewModel mSettingsViewModel;

    private int mSettingsPuzzleLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPuzzleViewModel = new ViewModelProvider(this).get(PuzzleViewModel.class);
        mSettingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        mPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        try {
            jsonFile = getAssets().open("words.json");
            mPuzzleViewModel.setWordPairReader(new WordPairReader(jsonFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Setting up the game settings saved in the shared preferences
        // Get the puzzle language from the shared preferences
        mSettingsPuzzleLanguage = mPreferences.getInt(getString(R.string.puzzle_language_key), BoardLanguage.ENGLISH);
        mSettingsViewModel.setPuzzleLanguage(mSettingsPuzzleLanguage);

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

    @Override
    protected void onStop() {
        super.onStop();
        // Save the puzzle language to the shared preferences
        SharedPreferences.Editor editor = mPreferences.edit();
        mSettingsPuzzleLanguage = mSettingsViewModel.getPuzzleLanguage().getValue();
        editor.putInt(getString(R.string.puzzle_language_key), mSettingsPuzzleLanguage);
        editor.apply();
    }


}