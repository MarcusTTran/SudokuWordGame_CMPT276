package com.echo.wordsudoku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.echo.wordsudoku.R;
import com.echo.wordsudoku.models.BoardLanguage;
import com.echo.wordsudoku.models.words.WordPairReader;
import com.echo.wordsudoku.ui.puzzleParts.PuzzleViewModel;
import com.google.android.material.navigation.NavigationView;

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

    private AppBarConfiguration appBarConfiguration;

    private DrawerLayout mDrawerLayout;

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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Hooking the navigation with the drawer and the action bar

        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.mainMenuFragment,R.id.settingsFragment,R.id.chooseCustomWordsFragment).setDrawerLayout(mDrawerLayout).build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}